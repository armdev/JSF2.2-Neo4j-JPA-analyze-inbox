package com.project.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class UserRelationEntity {

    @GraphId
    private Long id;  
    
    @StartNode
    private User fromUser;  
    
    @EndNode
    private User toUser;      
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }     

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

 

   
}
