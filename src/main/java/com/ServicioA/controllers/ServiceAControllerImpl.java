package ar.com.tdm.mock.controllers;

import ar.com.tdm.mock.entities.response_entities.CollaborationResponse;
import ar.com.tdm.mock.exceptions.HeroNotFoundException;
import ar.com.tdm.mock.exceptions.LastTimeNotFoundException;
import ar.com.tdm.mock.services.ServiceAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/marvel/colaborators")
public class ServiceAControllerImpl implements ServiceAController {
    private ServiceAService serviceAService;

    public ServiceAControllerImpl (ServiceAService serviceAService){
        this.serviceAService=serviceAService;
    }

    @GetMapping("/{character}")
    public ResponseEntity<CollaborationResponse> getCollaborators(@PathVariable String character) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        try {
            collaborationResponse = this.serviceAService.getCollaborators(character);
            collaborationResponse.setMessage("El servicio fue ejecutado correctamente.");
        } catch (HeroNotFoundException hnf) {
            collaborationResponse.setMessage("El heroe "+character+ " no es un heroe presente en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(collaborationResponse);
        } catch (LastTimeNotFoundException ltnf) {
            collaborationResponse.setMessage("No existe una sincronizacion de datos valida en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(collaborationResponse);
        }
        return ResponseEntity.ok(collaborationResponse);
    }
}

