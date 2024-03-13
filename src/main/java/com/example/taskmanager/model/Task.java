package com.example.taskmanager.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String description;
	private Date dueDate;
	private String status;
	@ManyToOne
    @JoinColumn(name = "owner_id")
    private TmUser owner;
	
	@ManyToMany
    @JoinTable(
        name = "task_collaborators",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TmUser> collaborators;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<TmUser> getCollaborators() {
		return collaborators;
	}
	public void setCollaborators(List<TmUser> collaborators) {
		this.collaborators = collaborators;
	}
	public TmUser getOwner() {
		return owner;
	}
	public void setOwner(TmUser owner) {
		this.owner = owner;
	}
	
	
	
	
}
