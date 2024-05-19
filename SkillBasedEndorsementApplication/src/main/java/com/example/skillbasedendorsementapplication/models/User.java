package com.example.skillbasedendorsementapplication.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("User")
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int yearsOfExperience;

    @Relationship(type = "HAS_SKILL", direction = Relationship.Direction.OUTGOING)
    private List<Skill> skills;

    @Relationship(type = "ENDORSED", direction = Relationship.Direction.OUTGOING)
    private List<Endorsement> endorsements;
}


