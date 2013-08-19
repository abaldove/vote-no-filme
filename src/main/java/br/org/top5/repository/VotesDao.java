package br.org.top5.repository;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.model.Vote;

@Component
public class VotesDao implements Votes {

	private EntityManager entityManager;

	public VotesDao(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public boolean add(Vote vote) {
		entityManager.persist(vote);
		return true;

	}

}
