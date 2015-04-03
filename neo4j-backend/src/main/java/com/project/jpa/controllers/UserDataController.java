package com.project.jpa.controllers;


import com.project.jpa.entities.Inbox;
import com.project.neo4j.domain.DataRelationEntity;
import com.project.neo4j.domain.Email;
import com.project.neo4j.domain.EmailUserRelationEntity;
import com.project.neo4j.domain.User;
import com.project.neo4j.domain.UserRelationEntity;
import com.project.neo4j.repository.EmailRelationRepository;
import com.project.neo4j.repository.EmailRepository;
import com.project.neo4j.services.EmailRelationService;
import com.project.neo4j.util.ConversionsUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Controller for handling user actions.
 */
@Component
@Transactional
@Service("userDataController")
public class UserDataController {

    @Autowired
    private InboxJpaController inboxJpaController;
    @Autowired
    private EmailRelationService emailRelationService;
    @Autowired
    private EmailRelationRepository emailRelationRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private Neo4jTemplate template;

    public List<Inbox> getAllDataList() {
        return inboxJpaController.findInboxEntities();
    }

    public static void main(String args[]) {
        UserDataController data = new UserDataController();
        data.makingNodes();
    }

    public void makingNodes() {
        try {
            List<Inbox> list = inboxJpaController.findInboxEntities();
            User firstUser = null;
            List<User> secondUser = null;
            List<User> sharedUser = null;
            Email email = null;
            System.out.println("List count " + list.size());
            for (Inbox data : list) {
                System.out.println("data " + data);
                firstUser = new User();
                secondUser = new ArrayList<User>();
                sharedUser = new ArrayList<User>();

                email = new Email();

                if (data.getCreatedDate() != null) {
                    String newEndDate  = ConversionsUtils.formatDate(data.getCreatedDate());
                    long searchDate  = ConversionsUtils.convertDateToLong(data.getCreatedDate());
                    email.setDateSent(newEndDate);                  
                    email.setSearchDate(searchDate);
                }
                email.setSubject(data.getTitle());

                if (data.getContent()!= null) {
                    email.setEmailTxt(data.getContent());
                }

                firstUser.setUsername(data.getEmailFrom());        
            

                String[] result = new String[data.getEmailTo().length()];

                if (data.getEmailTo().contains(",")) {
                    result = data.getEmailTo().split(",");
                } else {
                    result[0] = data.getEmailTo();
                }
                List<String> intoList = Arrays.asList(result);
                for (String into : intoList) {
                    User intoUser = new User();
                    if (into != null && into.length() > 0) {
                        intoUser.setUsername(into);
                       // intoUser = emailRelationRepository.save(intoUser);
                        secondUser.add(intoUser);
                    }
                }

                if (data.getEmailCc() != null && data.getEmailCc().contains(",")) {
                    String[] resultCC = new String[data.getEmailCc().length()];
                    resultCC = data.getEmailCc().split(",");
                    List<String> ccList = Arrays.asList(resultCC);
                    for (String cc : ccList) {
                        User ccUser = new User();
                        if (cc != null && cc.length() > 0) {
                            ccUser.setUsername(cc);
                          //  ccUser = emailRelationRepository.save(ccUser);
                            sharedUser.add(ccUser);
                        }
                    }
                } else if (data.getEmailCc() != null && !data.getEmailCc().contains(",")) {
                    User ccUser = new User();
                    ccUser.setUsername(data.getEmailCc());
                    sharedUser.add(ccUser);
                }

               System.out.println("---------START-------------- ");
               System.out.println("Sender " + firstUser.getUsername());

                User saveOwner = emailRelationService.getUserByNodeName(firstUser.getUsername());

                if (saveOwner == null) {
                   System.out.println("First user id " + firstUser.getId());
                    saveOwner = emailRelationRepository.save(firstUser);
                   System.out.println("Saved owner " + saveOwner.getId());
                }
//                } else if (saveOwner != null) {
//                    saveOwner.setUserId(firstUser.getUserId());
//                    saveOwner = emailRelationRepository.save(saveOwner);
//                }

                email.setSenderId(saveOwner.getId());
                Email savedEmail = emailRepository.save(email);
                  System.out.println("Email sender id " + savedEmail.getSenderId());
                 System.out.println("Email date sent " + savedEmail.getDateSent());

                DataRelationEntity sent = template.createRelationshipBetween(
                        saveOwner,
                        savedEmail,
                        DataRelationEntity.class,
                        RelTypes.SENT.name(), true);
                
               System.out.println("secondUser size " + secondUser.size());
        
                for (User a : secondUser) {
                   System.out.println("To " + a.getUsername());
                    if (a.getUsername() != null) {
                        User toUser = emailRelationService.getUserByNodeName(a.getUsername());
                        if (toUser == null) {
                         System.out.println("MAKING REALATION " + a.getUsername());
                            toUser = emailRelationRepository.save(a);
                            UserRelationEntity userClients = template.createRelationshipBetween(
                                    saveOwner,
                                    toUser,
                                    UserRelationEntity.class,
                                    RelTypes.TO.name(), true);

                          System.out.println("Saved First " + saveOwner.getUsername() + " _ "  + toUser.getUsername());
                            template.save(userClients);

                            EmailUserRelationEntity emailTo = template.createRelationshipBetween(
                                    savedEmail,
                                    toUser,
                                    EmailUserRelationEntity.class,
                                    RelTypes.TO.name(), true);

                            template.save(emailTo);

                        } else if (toUser != null) {                           
                            UserRelationEntity userClients = template.createRelationshipBetween(
                                    saveOwner,
                                    toUser,
                                    UserRelationEntity.class,
                                    RelTypes.TO.name(), true);

                            System.out.println("Saved Second " + saveOwner.getUsername() + " _ "  + toUser.getUsername());                            
                            template.save(userClients);

                            EmailUserRelationEntity emailTo = template.createRelationshipBetween(
                                    savedEmail,
                                    toUser,
                                    EmailUserRelationEntity.class,
                                    RelTypes.TO.name(), true);

                            template.save(emailTo);
                        }
                    }
                }

                for (User ab : sharedUser) {
                 System.out.println("CC " + ab.getUsername());
                    if (ab.getUsername() != null) {
                        User shared = emailRelationService.getUserByNodeName(ab.getUsername());
                        if (shared == null) {
                            shared = emailRelationRepository.save(ab);
                           System.out.println("Make node first time CC " + shared.getUsername());
                            UserRelationEntity userClients = template.createRelationshipBetween(
                                    saveOwner,
                                    shared,
                                    UserRelationEntity.class,
                                    RelTypes.CC.name(), true);
                            template.save(userClients);
                            EmailUserRelationEntity emailTo = template.createRelationshipBetween(
                                    savedEmail,
                                    shared,
                                    EmailUserRelationEntity.class,
                                    RelTypes.CC.name(), true);
                            template.save(emailTo);

                           } else if (shared != null) {
                          System.out.println("Make node CC Second : " + shared.getUsername());
                            UserRelationEntity userClients = template.createRelationshipBetween(
                                    saveOwner,
                                    shared,
                                    UserRelationEntity.class,
                                    RelTypes.CC.name(), true);
                            template.save(userClients);
                            
                            EmailUserRelationEntity emailTo = template.createRelationshipBetween(
                                    savedEmail,
                                    shared,
                                    EmailUserRelationEntity.class,
                                    RelTypes.CC.name(), true);
                        }
                    }
                }
            }
          System.out.println("--------FINISH data insert, now show all nodes -------------- ");
           System.out.println("//////////////////////////////////////////////////////////////// ");

           emailRelationService.getAllNodes();
           // emailRelationService.createNodes(firstUser, secondUser, sharedUser, email);            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in data");
            return;
        }
      System.out.println("Nodes count " + emailRelationService.getNodesCount());
    }

    public static enum RelTypes implements RelationshipType {

        SENT,
        CLIENT,
        TO,
        RECEIVED,
        CC
    }
}
