package com.project.neo4j.domain;

import java.io.Serializable;
import java.util.Set;
import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 *
 * @author armen
 */
@NodeEntity(useShortNames = true)
@TypeAlias("User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SENT = "SENT";
    public static final String TO = "TO";
    public static final String CC = "CC";
    @GraphId
    private Long id;    
    @Indexed(unique = false)
    private String username;  
    //@GraphProperty
    private long userId;
    @Fetch
    @RelatedTo(elementClass = Email.class, type = SENT, direction = Direction.OUTGOING)
    private Set<Email> userEmailList;
    @Fetch
    @RelatedTo(elementClass = User.class, type = TO, direction = Direction.OUTGOING)
    private Set<User> sentUserList;
    @Fetch
    @RelatedTo(elementClass = User.class, type = CC, direction = Direction.OUTGOING)
    private Set<User> sharedUserList;    
    @Fetch
    @RelatedToVia(type = "TO")
    private Set<UserRelationEntity> userRelation;
    @Fetch
    @RelatedToVia(type = "CC")
    private Set<UserRelationEntity> userRelationShared;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Set<UserRelationEntity> getUserRelationShared() {
        return userRelationShared;
    }

    public void setUserRelationShared(Set<UserRelationEntity> userRelationShared) {
        this.userRelationShared = userRelationShared;
    }

    public Set<UserRelationEntity> getUserRelation() {
        return userRelation;
    }

    public void setUserRelation(Set<UserRelationEntity> userRelation) {
        this.userRelation = userRelation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Email> getUserEmailList() {
        return userEmailList;
    }

    public void setUserEmailList(Set<Email> userEmailList) {
        this.userEmailList = userEmailList;
    }

    public Set<User> getSentUserList() {
        return sentUserList;
    }

    public void setSentUserList(Set<User> sentUserList) {
        this.sentUserList = sentUserList;
    }

    public Set<User> getSharedUserList() {
        return sharedUserList;
    }

    public void setSharedUserList(Set<User> sharedUserList) {
        this.sharedUserList = sharedUserList;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
