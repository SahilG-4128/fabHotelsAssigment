package com.example.skillbasedendorsementapplication.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Skill")
@Data
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
