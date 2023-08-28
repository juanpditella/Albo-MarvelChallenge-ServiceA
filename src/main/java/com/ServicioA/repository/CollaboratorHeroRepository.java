package com.ServicioA.repository;

import com.ServicioA.entities.repository_entities.CollaboratorHeroRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorHeroRepository extends JpaRepository<CollaboratorHeroRelation, Long> {
}
