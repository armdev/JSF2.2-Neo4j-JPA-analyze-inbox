package com.project.neo4j.repository;

import com.project.neo4j.domain.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface EmailRelationRepository extends GraphRepository<User>, RelationshipOperationsRepository<User> {    
    User findUserByUserId(Long userId);    
    User findUserByUsername(String username);    
}
