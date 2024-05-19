package com.example.skillbasedendorsementapplication.repository;

import com.example.skillbasedendorsementapplication.models.Skill;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends Neo4jRepository<Skill,Long> {
    public Skill findByName(String skillName);
}
