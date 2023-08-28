package com.ServicioA.services;


import com.ServicioA.entities.response_entities.CollaborationResponse;
import com.ServicioA.exceptions.HeroNotFoundException;
import com.ServicioA.exceptions.LastTimeNotFoundException;

public interface ServiceAService {

    public CollaborationResponse getCollaborators(String superHeroName) throws HeroNotFoundException, LastTimeNotFoundException;
}
