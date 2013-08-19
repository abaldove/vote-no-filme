package br.org.top5.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class EntityManagerCreator implements ComponentFactory<EntityManager> {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Override
	public EntityManager getInstance() {
		if (entityManager == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("top5unit");
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

}
