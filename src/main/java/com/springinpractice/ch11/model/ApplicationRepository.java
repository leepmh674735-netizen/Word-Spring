package com.springinpractice.ch11.model;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends Neo4jRepository<Application, Long> {
    
    Application findByName(String name);
}