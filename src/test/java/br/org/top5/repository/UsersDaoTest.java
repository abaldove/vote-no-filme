package br.org.top5.repository;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
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

import br.org.top5.model.User;

@Transactional(TransactionMode.DISABLED)
@JpaEntityManagerFactory(persistenceUnit = "testPersistenceUnit")
public class UsersDaoTest extends UnitilsJUnit4 {

	private UsersDao usersDao;
	private static EntityManagerFactory emf;
	private EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() {
		emf = Persistence.createEntityManagerFactory("testPersistenceUnit");
	}

	@Before
	public void setUp() {
		entityManager = emf.createEntityManager();
		usersDao = new UsersDao(entityManager);

	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setLogin("usuario");
		user.setEmail("usuario@gmail.com");
		usersDao.add(user);
		User userPersisted = entityManager.find(User.class, 1l);
		assertTrue(userPersisted.equals(user));
	}

	@Test
	@DataSet("User_complete.xml")
	public void testFindByLoginandEmail() {
		String login = "usuario1";
		String email = "usuario1@gmail.com";
		User user = usersDao.findBy(login, email);
		Assert.assertEquals(user.getId(), 1l);

	}

	@Test(expected = NoResultException.class)
	@DataSet("User_complete.xml")
	public void testWontFindByLoginandEmail() {
		String login = "usuario3";
		String email = "usuario3@gmail.com";
		usersDao.findBy(login, email);
	}

	@Test
	@DataSet("User_complete.xml")
	public void testUserRanking() {
		String login = "usuario1";
		String email = "usuario1@gmail.com";
		User user = usersDao.findBy(login, email);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getId(), 1l);
		Assert.assertEquals(user.getUserRanking(), getRankingTest());
	}

	@Test
	@DataSet("User_complete.xml")
	public void testUsersList() {
		List<User> list = usersDao.list();
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}

	@Test
	@DataSet("User_complete.xml")
	public void testUsersListGeneralRanking() {
		List<User> list = usersDao.list();
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}


	private Object getRankingTest() {
		HashMap<String, Integer> ranking = new HashMap<String, Integer>();
		ranking.put("Wolverine", 2);
		ranking.put("Super Man", 1);
		return ranking;
	}

}
