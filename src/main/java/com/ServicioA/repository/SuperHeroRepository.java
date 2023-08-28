package ar.com.tdm.mock.repository;

import ar.com.tdm.mock.entities.repository_entities.Creator;
import ar.com.tdm.mock.entities.repository_entities.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    SuperHero findByName(String superHeroName);

    @Query("SELECT c FROM CollaboratorHeroRelation chr " +
            "JOIN Creator c ON c.id = chr.id.idCreator " +
            "WHERE chr.id.superHeroId = :heroId")
    List<Creator> findCollaboratorsByHeroId(@Param("heroId") Long heroId);
}
