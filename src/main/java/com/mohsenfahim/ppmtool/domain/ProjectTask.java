package com.mohsenfahim.ppmtool.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    private Date dueDate;

    //ManytoOne with backlog

    @Column(updatable = false)
    private String projectIdentifier;

    private Date create_At;
    private Date update_At;


    public ProjectTask() {
    }

    public Long getId() {
        return id;
    }

    public String getProjectSequence() {
        return projectSequence;
    }

    public String getSummary() {
        return summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    @PrePersist
    protected void OnCreate(){
        this.create_At = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "id=" + id +
                ", projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", create_At=" + create_At +
                ", update_At=" + update_At +
                '}';
    }
}
