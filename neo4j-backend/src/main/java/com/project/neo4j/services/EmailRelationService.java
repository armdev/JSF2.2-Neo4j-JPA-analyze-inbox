package com.project.neo4j.services;

import com.project.neo4j.domain.Email;
import com.project.neo4j.domain.User;
import com.project.neo4j.domain.UserFinalData;
import com.project.neo4j.domain.UserRelationEntity;
import com.project.neo4j.repository.EmailRelationRepository;
import com.project.neo4j.repository.EmailRepository;
import com.project.neo4j.util.ConversionsUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("emailRelationService")
@Component
@Transactional
public class EmailRelationService {

    @Autowired
    private Neo4jTemplate template;
    @Autowired
    private EmailRelationRepository emailRelationRepository;
    @Autowired
    private EmailRepository emailRepository;
    private Relationship relationship;

    public EmailRelationService() {
    }

    public List<UserFinalData> getAllUsersData() {
        List<UserFinalData> finalData = new ArrayList<UserFinalData>();
        try {         

            List<User> mainList = this.getAllUsers();
          
            for (User user : mainList) {
                List<User> userClients = new ArrayList<User>();
                List<User> sharedUsers = new ArrayList<User>();
                
                if (user != null && user.getSharedUserList() != null) {
                    sharedUsers.addAll(user.getSharedUserList());
                }
                if (user != null && user.getSentUserList() != null) {
                    userClients.addAll(user.getSentUserList());
                }

                UserFinalData dataList = null;
                for (User client : userClients) {
                    int count = 0;
                    dataList = new UserFinalData();
                    dataList.setUsername(client.getUsername());
                    dataList.setFromUsername(user.getUsername());
                    System.out.println("getRelationshipsBetween " + user + " -  " + client);
                    Iterable<UserRelationEntity> rel = template.getRelationshipsBetween(user, client, UserRelationEntity.class,
                            RelTypes.TO.name());

                    for (UserRelationEntity it : rel) {
                        count++;
                        dataList.setConnectionCount(count);
                    }
                    finalData.add(dataList);
                    //    System.out.println("FinalData size/count  " + finalData.size());
                }
            }
            Collections.sort(finalData);
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        return finalData;
    }

    public List<Email> getUserEmailData(Long userId, Date startDate, Date endDate) {
        List<Email> list = new ArrayList<Email>();
        if (startDate == null || endDate == null || userId == null) {
            return null;
        }

        long first = ConversionsUtils.convertDateToLong(startDate);
        long second = ConversionsUtils.convertDateToLong(endDate);

        // System.out.println("newStartDate " + first);
        //System.out.println("newEndDate " + second);
        try {
            list = emailRepository.searchUserData(userId, first, second);
            // System.out.println("List size " +list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<UserFinalData> getUserData(String username) {
        System.out.println("username " + username);
        List<UserFinalData> finalData = new ArrayList<UserFinalData>();
        try {
            // System.out.println("User id from backend " + username);
            User user = this.getUserByNodeName(username);
            // System.out.println("Found user with id  " + user);

            List<User> userClients = new ArrayList<User>();
            List<User> sharedUsers = new ArrayList<User>();
            if (user != null && user.getSharedUserList() != null) {
                sharedUsers.addAll(user.getSharedUserList());
            }
            if (user != null && user.getSentUserList() != null) {
                userClients.addAll(user.getSentUserList());
            }
            UserFinalData dataList = null;
            for (User client : userClients) {
                int count = 0;
                dataList = new UserFinalData();
                dataList.setUsername(client.getUsername());
                System.out.println("getRelationshipsBetween " + user + " -  " + client);
                Iterable<UserRelationEntity> rel = template.getRelationshipsBetween(user, client, UserRelationEntity.class,
                        RelTypes.TO.name());

                for (UserRelationEntity it : rel) {
                    count++;
                    dataList.setConnectionCount(count);
                }
                finalData.add(dataList);
                //    System.out.println("FinalData size/count  " + finalData.size());
            }
            Collections.sort(finalData);
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        return finalData;
    }

    public int getUserRelationCount(User userOne, User userTwo) {
        User user = this.getUserByUserId(userOne.getUserId());
        User secondUser = this.getUserByUserId(userTwo.getUserId());
        //UserRelationEntity one = template.getRelationshipBetween(user, secondUser, UserRelationEntity.class, RelTypes.CLIENT.name());
        Iterable<UserRelationEntity> rel = template.getRelationshipsBetween(user, secondUser, UserRelationEntity.class, RelTypes.CLIENT.name());
        int count = 0;
        for (UserRelationEntity it : rel) {
            count++;
            //System.out.println("Count " + count);
        }
        return count;
    }

    public int getRelCount(Long firstUser, Long secondUser) {
        User user = this.getUserByUserId(firstUser);
        User toUser = this.getUserByUserId(secondUser);
        Iterable<UserRelationEntity> rel = template.getRelationshipsBetween(user, toUser, UserRelationEntity.class,
                RelTypes.TO.name());

        List<UserRelationEntity> relSumm = new ArrayList<UserRelationEntity>();
        int total = 0;

        for (UserRelationEntity it : rel) {
            total++;

        }

        int count = total;
        //  System.out.println("Total rel count beetween " + user.getUsername() + " and " + toUser.getUsername() + " = " + total);
        //   System.out.println("Count is " + count);
        //  System.out.println("List size is  " + relSumm.size());
        return count;
    }

    public int getUserRelationCountByUsername(String emailFirst, String emailSecond) {
        User user = this.getUserByNodeName(emailFirst);
        User secondUser = this.getUserByNodeName(emailSecond);
        //UserRelationEntity one = template.getRelationshipBetween(user, secondUser, UserRelationEntity.class, RelTypes.CLIENT.name());
        Iterable<UserRelationEntity> rel = template.getRelationshipsBetween(user, secondUser, UserRelationEntity.class, RelTypes.TO.name());
        int count = 0;
        for (UserRelationEntity it : rel) {
            count++;
            //System.out.println("Count " + count);
        }
        return count;
    }

    public List<User> getEmailRelations(Long id) {
        List<User> relationList = new ArrayList<User>();
        Node node = template.getNode(id);

        for (Relationship rel : node.getRelationships()) {
            User u = (User) rel.getEndNode();
            relationList.add(u);
            // System.out.println("Rels start node " + rel.getStartNode() + " end node " + rel.getEndNode());
        }
        return relationList;
    }

    public Email getEmailByDate(Date date_sent) {
        return emailRepository.findByPropertyValue("datesent", date_sent);
    }

    public Email getEmailById(String emailId) {
        Email email = new Email();
        try {
            Long id = Long.parseLong(emailId);
            email = emailRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    public User getUserByName(String username) {
        return emailRelationRepository.findByPropertyValue("username", username);
    }

    public void truncateDatabase() {
        emailRelationRepository.deleteAll();
        emailRepository.deleteAll();
    }

    public List<User> getAllUsers() {
        List<User> allusers = new ArrayList<User>();
        Iterable<User> all = emailRelationRepository.findAll();
        for (User u : all) {
            allusers.add(u);
        }
        return allusers;
    }

    public List<User> getAllNodes() {
        List<User> allList = new ArrayList<User>();
        Iterable<User> users = emailRelationRepository.findAll();
        if (users != null) {
            for (User u : users) {
                System.out.println("_____________Start node_________");
                System.out.println("Node Id  " + u.getId());
                System.out.println("User name " + u.getUsername());
                System.out.println("User Id " + u.getUserId());
                System.out.println("_____________Finish Node_________");
                allList.add(u);
            }
        }
        return allList;
    }

    public List<User> getMutualClients(Long firstNodeId, Long secondNodeId) {
        List<User> muturalClients = new ArrayList<User>();
        try {
            Node firstNode = template.getNode(firstNodeId);
            Node secondNode = template.getNode(secondNodeId);
            PathFinder<Path> finder = GraphAlgoFactory.allPaths(
                    Traversal.expanderForTypes(RelTypes.SENT), 2);

            Iterable<Path> paths = finder.findAllPaths(firstNode, secondNode);
            for (Path path : paths) {
                for (Relationship rel : path.relationships()) {
                    for (Node node : rel.getNodes()) {
                        // System.out.println("Nodes " +node);
                        if (node.getId() != firstNodeId.longValue() && node.getId() != secondNodeId.longValue()) {
                            User user = emailRelationRepository.findOne(node.getId());
                            if (!muturalClients.contains(user)) {
                                muturalClients.add(user);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return muturalClients;
    }

    public User getUserByUserId(Long userId) {
        User user = null;
        try {
            user = emailRelationRepository.findUserByUserId(userId);
            //  System.out.println("Found user " + user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public User getUserByNodeName(String username) {
        User user = new User();
        try {
            System.out.println("getUserByNodeName username " + username);
            user = emailRelationRepository.findUserByUsername(username);
            System.out.println("getUserByNodeName user " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserById(Long id) {
        User user = new User();
        try {
            user = emailRelationRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByDataId(String id) {
        User user = new User();
        try {
            Long uid = Long.parseLong(id);
            user = emailRelationRepository.findOne(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static enum RelTypes implements RelationshipType {

        SENT,
        CLIENT,
        TO,
        RECEIVED,
        CC
    }

    public long getNodesCount() {
        return emailRelationRepository.count();
    }

    public long getEmailCount() {
        return emailRepository.count();
    }
}
