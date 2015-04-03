package com.project.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class EmailUserRelationEntity {

    @GraphId
    private Long id;        
    @StartNode
    private Email userData;      
    @EndNode
    private User toUser;                

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }     

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }  

    public Email getUserData() {
        return userData;
    }

    public void setUserData(Email userData) {
        this.userData = userData;
    }

   
}
