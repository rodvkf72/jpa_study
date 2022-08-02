package com.rodvkf72.web.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Member {
	@Id
	@Column(name = "MEMBER_ID")
	private String id;
	
	private String username;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return this.username;
	}
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID")
	private Team team;
	
	public void setTeam(Team team) {
		if (this.team != null) {
			this.team.getMembers().remove(this);
		}
		this.team = team;
		team.getMembers().add(this);
	}
	public Team getTeam() {
		return this.team;
	}
	
	public Member() {
		
	}
	public Member(String id, String username) {
		this.id = id;
		this.username = username;
	}
	
}
