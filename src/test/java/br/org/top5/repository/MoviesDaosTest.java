package br.org.top5.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
import org.unitils.reflectionassert.ReflectionAssert;

import br.org.top5.model.Movie;

@DataSet("Movie.xml")
@Transactional(TransactionMode.DISABLED)
@JpaEntityManagerFactory(persistenceUnit = "testPersistenceUnit")
public class MoviesDaosTest extends UnitilsJUnit4 {

	private Movies moviesDao;
	private static EntityManagerFactory emf;
	private EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() {
		emf = Persistence.createEntityManagerFactory("testPersistenceUnit");
	}

	@Before
	public void setUp() {
		entityManager = emf.createEntityManager();
		moviesDao = new MoviesDao(entityManager);

	}

	@Test
	public void testMoviesFull() {
		List<Movie> moviesList = moviesDao.movies();
		assertNotNull(moviesList);
		ReflectionAssert.assertPropertyLenientEquals("name",
				Arrays.asList("Super Man", "Wolverine"), moviesList);
		ReflectionAssert.assertPropertyLenientEquals("id",
				Arrays.asList(1l, 2l), moviesList);
	}

	@Test
	public void testFindById() {
		long movieId = 1l;
		Movie movie = moviesDao.findBy(movieId);
		Assert.assertNotNull(movie);
		Assert.assertEquals(1l, movie.getId());
	}

	@Test(expected = NoResultException.class)
	public void testWontFindById() {
		long movieId = 4l;
		moviesDao.findBy(movieId);
	}

}
