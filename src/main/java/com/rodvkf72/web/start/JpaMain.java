package com.rodvkf72.web.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
	public static void main(String[] args) {
		//엔티티 매니저 팩토리 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		//엔티티 매니저 생성
		EntityManager em = emf.createEntityManager();
		
		//트랜잭션
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			logic(em);
			tx.commit();
		} catch (Exception e) {
			//트랜잭션 롤백
			tx.rollback();
		} finally {
			//엔티티 매니저 종료
			em.close();
		}
		//엔티티 매니저 팩토리 종료
		emf.close();
	}
	
	private static void logic(EntityManager em) {
		Member member1 = new Member();
		Member member2 = new Member();
		
		member1.setId("1");
		member2.setId("2");
		member1.setUsername("광호");
		member2.setUsername("광혁");
		member1.setAge(28);
		member2.setAge(28);
		
		em.persist(member1);
		em.persist(member2);
		
		//목록 조회
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		System.out.println("members.size=" + members.size());
	}
}
