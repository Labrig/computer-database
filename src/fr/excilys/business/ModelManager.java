package fr.excilys.business;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class ModelManager {

	protected EntityManager entityManager;
	
	public ModelManager() {
		this.entityManager = Persistence.createEntityManagerFactory("computer-database").createEntityManager();
	}
}
