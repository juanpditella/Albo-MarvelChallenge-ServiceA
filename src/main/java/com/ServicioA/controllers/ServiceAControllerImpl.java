package com.ServicioA.controllers;


import com.ServicioA.entities.response_entities.CollaborationResponse;
import com.ServicioA.exceptions.HeroNotFoundException;
import com.ServicioA.exceptions.LastTimeNotFoundException;
import com.ServicioA.services.ServiceAService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/marvel/colaborators")
public class ServiceAControllerImpl implements ServiceAController {
    private ServiceAService serviceAService;
    @Value("${heroes.endpoints}")
    private String heroesEndpoints;
    public ServiceAControllerImpl (ServiceAService serviceAService){
        this.serviceAService=serviceAService;
    }

    @GetMapping("/{character}")
    public ResponseEntity<CollaborationResponse> getCollaborators(@PathVariable String character) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        String realName = this.getRealNameForEndpoint(character);

        try {
            collaborationResponse = this.serviceAService.getCollaborators(realName);
            collaborationResponse.setMessage("El servicio fue ejecutado correctamente.");
        } catch (HeroNotFoundException hnf) {
            collaborationResponse.setMessage("El valor de la ruta definida no corresponde con ningun heroe cargado en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(collaborationResponse);
        } catch (LastTimeNotFoundException ltnf) {
            collaborationResponse.setMessage("No existe una sincronizacion de datos valida en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(collaborationResponse);
        }
        return ResponseEntity.ok(collaborationResponse);
    }

    private String getRealNameForEndpoint(String endpoint) {
        for (String endpointInfo : heroesEndpoints.split(",")) {
            String[] parts = endpointInfo.split("=");
            if (parts.length == 2 && parts[0].equals(endpoint)) {
                return parts[1];
            }
        }
        return "Unknown Hero";
    }
}

