package com.example.skillbasedendorsementapplication.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Endorsement")
@Data
public class Endorsement {

    @Id
    @GeneratedValue()
    private long id;
    @NotNull
    private int score;
    private double adjustedScore;
    private String reason;

    @Relationship(type = "ENDORSED_BY", direction = Relationship.Direction.INCOMING)
    private User endorser;

    @Relationship(type = "ENDORSED", direction = Relationship.Direction.OUTGOING)
    private User endorsee;

    @Relationship(type = "ENDORSED_SKILL")
    private Skill skill;
}
