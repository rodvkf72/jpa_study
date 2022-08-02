package com.rodvkf72.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rodvkf72.web.start.Member;
import com.rodvkf72.web.start.Team;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class JpaStudyApplicationTests {

	//엔티티 매니저 팩토리 생성
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
			
	//엔티티 매니저 생성
	EntityManager em = emf.createEntityManager();
	
	EntityTransaction tx = em.getTransaction();
	
	@Test
	@Disabled
	public void 멤버_팀_ManyToOne_테스트() {
		tx.begin();
		//팀1 저장
		Team team1 = new Team("team1", "팀1");
		em.persist(team1);
		
		//회원1 저장
		Member member1 = new Member("member1", "회원1");
		member1.setTeam(team1);	//연관관계 설정
		em.persist(member1);
		
		//회원2 저장
		Member member2 = new Member("member2", "회원2");
		member2.setTeam(team1);	//연관관계 설정
		em.persist(member2);
		
		tx.commit();
	}
	
	@Test
	@Disabled
	public void JPQL_조인검색() {
		String jpql = "select m from Member m join m.team t where t.name=:teamName";
		
		List<Member> resultList = em.createQuery(jpql, Member.class)
				.setParameter("teamName", "팀1")
				.getResultList();
		
		for (Member member : resultList) {
			System.out.println("[query] member.username=" + member.getUsername());
		}
	}
	
	@Test
	@Disabled
	public void 연관관계_수정() {
		//새로운 팀2
		Team team2 = new Team("team2", "팀2");
		em.persist(team2);
		
		//회원1에 새로운 팀2 설정
		Member member = em.find(Member.class, "member1");
		member.setTeam(team2);
		
		System.out.println("@@@ : " + member.getTeam().getId() + ", " + member.getTeam().getName());
	}

	@Test
	@Disabled
	public void 연관관계_제거() {
		tx.begin();
		Member member1 = em.find(Member.class, "member1");
		member1.setTeam(new Team("team1", "팀1"));
		
		if (member1.getTeam() == null) {
			System.out.println("null 입니다.");
		} else {
			System.out.println("@@@ : " + member1.getTeam().getId() + ", " + member1.getTeam().getName());
		}
		tx.commit();
	}
	
	@Test
	public void 양방향_연관관계_주의점() {
		tx.begin();
		
		//회원1 저장
		Member member3 = new Member("member3", "회원3");
		em.persist(member3);
		
		//회원2 저장
		Member member4 = new Member("member4", "회원4");
		em.persist(member4);
		
		Team team3 = new Team("team3", "팀3");
		team3.getMembers().add(member3);
		team3.getMembers().add(member4);
		
		em.persist(team3);
		tx.commit();
	}
	
	@Test
	public void 양방향_리팩토링() {
		Team team4 = new Team("team4", "팀4");
		em.persist(team4);
		
		Member member5 = new Member("member5", "회원5");
		member5.setTeam(team4);
		em.persist(member5);
		
		Member member6 = new Member("member6", "회원6");
		member6.setTeam(team4);
		em.persist(member6);
	}
}
