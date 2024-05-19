package com.example.skillbasedendorsementapplication.repository;

import com.example.skillbasedendorsementapplication.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User,Long> {
}
