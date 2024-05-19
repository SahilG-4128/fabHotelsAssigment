package com.example.skillbasedendorsementapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableNeo4jRepositories
public class SkillBasedEndorsementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillBasedEndorsementApplication.class, args);
    }
}
