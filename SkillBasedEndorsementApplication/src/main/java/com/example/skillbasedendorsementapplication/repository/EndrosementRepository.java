package com.example.skillbasedendorsementapplication.repository;

import com.example.skillbasedendorsementapplication.models.Endorsement;
import org.apache.coyote.Response;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EndrosementRepository extends Neo4jRepository<Endorsement,Long> {
    public List<Endorsement> findByEndorseeId(Long id);
}
