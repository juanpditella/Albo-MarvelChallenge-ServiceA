package com.ServicioA.services;


import com.ServicioA.entities.repository_entities.Creator;
import com.ServicioA.entities.repository_entities.LastSyncInfo;
import com.ServicioA.entities.repository_entities.SuperHero;
import com.ServicioA.entities.response_entities.CollaborationResponse;
import com.ServicioA.exceptions.HeroNotFoundException;
import com.ServicioA.exceptions.LastTimeNotFoundException;
import com.ServicioA.repository.LastSyncInfoRepository;
import com.ServicioA.repository.SuperHeroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                LocalDateTime lastSyncDateTime = this.getLastSyncDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDateTime = lastSyncDateTime.format(formatter);

                String message = String.format("Fecha de la última sincronización: %s", formattedDateTime);
                response.setLastSync(message);
            }
            catch (LastTimeNotFoundException ltnf){
                throw new LastTimeNotFoundException(ltnf.getMessage());
            }
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
