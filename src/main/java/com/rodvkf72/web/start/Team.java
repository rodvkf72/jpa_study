package com.rodvkf72.web.start;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {
	@Id
	@Column(name="TEAM_ID")
	private String id;
	private String name;

	public Team() {
		
	}
	public Team(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	
	//추가
	@OneToMany(mappedBy="team")	//mappedBy는 @ManyToOne을 지정한 곳에서의 변수명과 동일하게 지정
	private List<Member> members = new ArrayList<Member>();
	
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public List<Member> getMembers() {
		return this.members;
	}
}
