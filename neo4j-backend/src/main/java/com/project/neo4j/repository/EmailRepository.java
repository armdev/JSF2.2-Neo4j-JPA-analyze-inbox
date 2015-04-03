package com.project.neo4j.repository;


import com.project.neo4j.domain.Email;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface EmailRepository extends GraphRepository<Email>, RelationshipOperationsRepository<Email> {

    
            
//    @Query("START email=node:__types__("className"=com.backend.core.neo.entities.Email") WHERE email.senderId={0}"
//            + " AND email.searchDate > {1} AND email.searchDate < {2}"
//            + " RETURN email")
            
            // start person=node:__types__("className"="com...Person") 
// where person.age = {0} and person.married = {1}
// return person
    
    @Query(
            "START  email=node:__types__(className='com.backend.core.neo.entities.Email') "            
            + "WHERE  email.senderId={0} and (email.searchDate > {1} and email.searchDate < {2})"
            + "RETURN email")
    List<Email> searchUserData(Long userId, long startDate, long endDate);

    List<Email> findBySenderId(Long userId);
//    @Query("START root=node:Email(senderId = userId) MATCH root-[:To]->friends RETURN Email")
//    List<Email> getUserDatalist(Long userId, Date startDate, Date endDate);
}
