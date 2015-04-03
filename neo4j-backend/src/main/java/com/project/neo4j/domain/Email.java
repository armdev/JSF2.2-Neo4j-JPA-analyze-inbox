package com.project.neo4j.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author armen
 */

@NodeEntity(useShortNames = true)
@TypeAlias("Email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String CC = "CC";
    public static final String TO = "TO";
    
    @GraphId
    private Long id;
    
    @GraphProperty
    private Long senderId;
    
    @GraphProperty
    private String subject;    
    
    @Indexed 
   // @GraphProperty(propertyType = java.util.Date.class)
    private String dateSent;
    
    @Indexed    
    private long searchDate;
    
    @GraphProperty
    private String emailTxt;
    @GraphProperty
    private String emailHtml;
    @GraphProperty
    private String emailId;
    //mail to
    @Fetch
    @RelatedTo(elementClass = User.class, type = TO, direction = Direction.OUTGOING)
    private Set<User> intoUsers;
    //mail shared
    @Fetch
    @RelatedTo(elementClass = User.class, type = CC, direction = Direction.OUTGOING)
    private Set<User> sharedUsers;

    public long getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(long searchDate) {
        this.searchDate = searchDate;
    }

    public Email() {
    }

    public Email(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Set<User> getIntoUsers() {
        return intoUsers;
    }

    public void setIntoUsers(Set<User> intoUsers) {
        this.intoUsers = intoUsers;
    }

    public Set<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(Set<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getEmailTxt() {
        return emailTxt;
    }

    public void setEmailTxt(String emailTxt) {
        this.emailTxt = emailTxt;
    }

    public String getEmailHtml() {
        return emailHtml;
    }

    public void setEmailHtml(String emailHtml) {
        this.emailHtml = emailHtml;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
