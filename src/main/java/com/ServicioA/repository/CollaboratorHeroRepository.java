package ar.com.tdm.mock.repository;

import ar.com.tdm.mock.entities.repository_entities.CollaboratorHeroRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorHeroRepository extends JpaRepository<CollaboratorHeroRelation, Long> {
}
