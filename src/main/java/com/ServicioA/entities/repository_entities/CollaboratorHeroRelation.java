package com.ServicioA.entities.repository_entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "CollaboratorHeroRelation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelation {
    @EmbeddedId
    private CollaboratorHeroRelationId id;

    @ManyToOne
    @JoinColumn(name = "superHeroId", insertable = false, updatable = false)
    private SuperHero superHero;

    @ManyToOne
    @JoinColumn(name = "idCreator", insertable = false, updatable = false)
    private Creator creator;

}
