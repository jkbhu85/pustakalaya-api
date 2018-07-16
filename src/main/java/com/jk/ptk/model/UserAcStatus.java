package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="UserAccountStatus")
public class UserAcStatus {
	@Autowired
	EntityManager em;
	
	public static final UserAcStatus ACTIVE;
	public static final UserAcStatus LOCKED;
	public static final UserAcStatus CLOSED;
	public static final UserAcStatus REVOKED;
	public static final UserAcStatus INCOMPLETE;
	
	private static class UserAcStatusInner {
		@Autowired
		EntityManager em;
	}
	
	
	static {
		UserAcStatusInner uac = new UserAcStatusInner(); // to get entity manager
		
		TypedQuery<UserAcStatus> q = uac.em.createQuery("select acs from UserAcStatus u where u.name=:name", UserAcStatus.class);
		
		q.setParameter("name", "ACTIVE");
		ACTIVE = q.getSingleResult();
		
		q.setParameter("name", "LOCKED");
		LOCKED = q.getSingleResult();
		
		q.setParameter("name", "CLOSED");
		CLOSED = q.getSingleResult();
		
		q.setParameter("name", "REVOKED");
		REVOKED = q.getSingleResult();

		q.setParameter("name", "REVOKED");
		INCOMPLETE = q.getSingleResult();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;


	public UserAcStatus() {}


	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Account status: " + name;
	}

}
