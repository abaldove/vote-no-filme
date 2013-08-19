package br.org.top5.repository;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.Vote;

@Transactional(TransactionMode.DISABLED)
@JpaEntityManagerFactory(persistenceUnit = "testPersistenceUnit")
public class VotesDaoTest extends UnitilsJUnit4 {

	private Votes votesDao;
	private static EntityManagerFactory emf;
	private EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() {
		emf = Persistence.createEntityManagerFactory("testPersistenceUnit");
	}

	@Before
	public void setUp() {
		entityManager = emf.createEntityManager();
		votesDao = new VotesDao(entityManager);

	}

	@Test
	@DataSet("Votes_basic.xml")
	public void testAdd() {
		Movie movie = entityManager.find(Movie.class, 1l);
		User user = entityManager.find(User.class, 1l);
		Assert.assertNotNull(user);
		Assert.assertNotNull(movie);

		Vote vote = new Vote();
		vote.setMovie(movie);
		vote.setUser(user);
		vote.setVoteDate(new Date());
		votesDao.add(vote);

		Vote votePersisted = entityManager.find(Vote.class, 1l);
		Assert.assertTrue(vote.equals(votePersisted));

	}

}
