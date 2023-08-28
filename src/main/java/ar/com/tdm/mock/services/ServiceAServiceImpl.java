package ar.com.tdm.mock.services;

import ar.com.tdm.mock.controllers.ServiceAControllerImpl;
import ar.com.tdm.mock.entities.repository_entities.Creator;
import ar.com.tdm.mock.entities.repository_entities.LastSyncInfo;
import ar.com.tdm.mock.entities.repository_entities.SuperHero;
import ar.com.tdm.mock.entities.response_entities.CollaborationResponse;
import ar.com.tdm.mock.exceptions.HeroNotFoundException;
import ar.com.tdm.mock.exceptions.LastTimeNotFoundException;
import ar.com.tdm.mock.repository.LastSyncInfoRepository;
import ar.com.tdm.mock.repository.SuperHeroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceAServiceImpl implements ServiceAService {

    private SuperHeroRepository superHeroRepository;
    private LastSyncInfoRepository lastSyncInfoRepository;

    public ServiceAServiceImpl(SuperHeroRepository superHeroRepository, LastSyncInfoRepository lastSyncInfoRepository){
        this.superHeroRepository=superHeroRepository;
        this.lastSyncInfoRepository=lastSyncInfoRepository;
    }
    @Override
    public CollaborationResponse getCollaborators(String superHeroName) throws HeroNotFoundException, LastTimeNotFoundException {
        SuperHero superHero=this.superHeroRepository.findByName(superHeroName);
        if (superHero==null){
            throw new HeroNotFoundException("El heroe "+superHeroName+ " no se encuenta en la base de datos.");
        }
        else {
            CollaborationResponse response = new CollaborationResponse();
            List<Creator> collaborators=this.superHeroRepository.findCollaboratorsByHeroId(superHero.getId());
            for (Creator collaborator : collaborators) {
                String role = collaborator.getRole();
                String name = collaborator.getName();
                switch (role) {
                    case "editor":
                        response.addEditor(name);
                        break;
                    case "writer":
                        response.addWriter(name);
                        break;
                    case "colorist":
                        response.addColorist(name);
                        break;
                }
            }
            try{
                String lastSync = this.getLastSyncDateTime().toString();
                response.setLastSync(lastSync);
            }
            catch (LastTimeNotFoundException ltnf){
                throw new LastTimeNotFoundException(ltnf.getMessage());
            }
            System.out.println("Aaaaaaaaaaaaaa "+ response.toString());
            return response;
        }
    }


    public LocalDateTime getLastSyncDateTime() throws LastTimeNotFoundException {
        LastSyncInfo lastSyncInfo = lastSyncInfoRepository.findTopByOrderByLastSyncDateTimeDesc();

        if (lastSyncInfo != null) {
            return lastSyncInfo.getLastSyncDateTime();
        }

        throw new LastTimeNotFoundException("No existen datos de sincronizacion en la base.");
    }
}
