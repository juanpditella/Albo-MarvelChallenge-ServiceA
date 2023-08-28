package com.ServicioA.entities.repository_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelationId implements Serializable {
    @Column(name = "superHeroId")
    private Long superHeroId;

    @Column(name = "idCreator")
    private Long idCreator;
}
