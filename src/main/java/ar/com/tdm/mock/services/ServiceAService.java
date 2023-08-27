package ar.com.tdm.mock.services;

import ar.com.tdm.mock.entities.response_entities.CollaborationResponse;
import ar.com.tdm.mock.exceptions.HeroNotFoundException;
import ar.com.tdm.mock.exceptions.LastTimeNotFoundException;

public interface ServiceAService {

    public CollaborationResponse getCollaborators(String superHeroName) throws HeroNotFoundException, LastTimeNotFoundException;
}
