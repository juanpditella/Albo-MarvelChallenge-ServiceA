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
        } catch (HeroNotFoundException hnf) {
            return ResponseEntity.notFound().build();  // Respuesta 404
        } catch (LastTimeNotFoundException ltnf) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Respuesta 500
        }
        return ResponseEntity.ok(collaborationResponse);
    }
}