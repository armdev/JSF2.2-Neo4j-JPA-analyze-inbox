package com.project.neo4j.data;

import java.io.Serializable;
import java.util.Date;

public class DataDTO implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private Long id;
  
    private String emailFrom;
 
    private String emailTo;
   
    private String emailCc;
  
    private String emailTxt;
   
   
  
    private String subject;
   
    private Date created_date;

    public DataDTO() {
    }

    public DataDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getEmailTxt() {
        return emailTxt;
    }

    public void setEmailTxt(String emailTxt) {
        this.emailTxt = emailTxt;
    }

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 73 * hash + (this.emailFrom != null ? this.emailFrom.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataDTO other = (DataDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.emailFrom == null) ? (other.emailFrom != null) : !this.emailFrom.equals(other.emailFrom)) {
            return false;
        }
        
        return true;
    }
   

 
    @Override
    public String toString() {
        return "com.backend.core.domain.UserData[ id=" + id + " ]";
    }
    
}
