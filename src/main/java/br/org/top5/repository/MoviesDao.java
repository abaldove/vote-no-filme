package br.org.top5.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.model.Movie;

@Component
public class MoviesDao implements Movies {

	private EntityManager entityManager;

	public MoviesDao(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	public List<Movie> movies() {
		Query query = entityManager.createQuery("Select m from Movie m");
		List<Movie> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Movie findBy(long movieId) {
		Query query = entityManager
				.createQuery("Select m from Movie m where m.id=:movieId");
		query.setParameter("movieId", movieId);
		return (Movie) query.getSingleResult();
	}

}
