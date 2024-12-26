package com.deep.ims.entity;

import jakarta.persistence.*;

@Entity
//@EntityListeners(CustomizedIdGenerator.class)
public class Incident {

    @Id
    private String id;
    private String type;
//    private Long userId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User createdBy;

    private String priority;
    private String status;
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedByUser(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
