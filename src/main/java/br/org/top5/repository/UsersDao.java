package br.org.top5.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.model.User;

@Component
public class UsersDao implements Users {

	private EntityManager entityManager;

	public UsersDao(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public boolean add(User user) {
		entityManager.persist(user);
		return true;
	}

	@Override
	public User findBy(String login, String email) {
		Query query = entityManager
				.createQuery("Select u from br.org.top5.model.User u where u.email=:email and u.login=:login");
		query.setParameter("email", email);
		query.setParameter("login", login);
		return (User) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list() {
		return entityManager.createQuery(
				"Select u from br.org.top5.model.User u").getResultList();
	}

}
