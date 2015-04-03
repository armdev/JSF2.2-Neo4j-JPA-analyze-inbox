package com.project.neo4j.domain;
/**
 *
 * @author armen
 */
public class UserFinalData implements Comparable<UserFinalData> {

    private String fromUsername;
    private String username;//to
    private Integer connectionCount;

    public UserFinalData() {
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    public int compareTo(UserFinalData compare) {
        int compareQuantity = ((UserFinalData) compare).getConnectionCount();
        //ascending order
       // return this.connectionCount - compareQuantity;

        //descending order
        return compareQuantity - this.getConnectionCount();
    }
}
