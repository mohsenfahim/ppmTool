package com.mohsenfahim.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    //ManytoOne with backlog
    //@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH) this cascade reloads the managed object from DB which will prevent project task to get delete.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectSequence(String projectSequence) {
        this.projectSequence = projectSequence;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
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
