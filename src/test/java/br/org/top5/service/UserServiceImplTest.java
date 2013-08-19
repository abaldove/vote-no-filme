package br.org.top5.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.Vote;
import br.org.top5.repository.Movies;
import br.org.top5.repository.Users;

public class UserServiceImplTest {

	@Mock
	private Users users;

	@Mock
	private Movies movies;

	private UserService userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userService = new UserServiceImpl(users, movies);
	}

	@Test
	public void testAddUser() throws InvalidEmailException,
			InvalidLoginException {
		Mockito.when(users.add(Mockito.any(User.class))).thenReturn(true);
		String login = "usuario";
		String email = "email@gmail.com";
		userService.addUser(login, email);
		Mockito.verify(users, Mockito.atLeastOnce()).add(
				Mockito.any(User.class));
	}

	@Test(expected = InvalidEmailException.class)
	public void testAddUserInvalidEmail() throws InvalidEmailException,
			InvalidLoginException {
		String login = "usuario";
		String email = "emailgmail.com";
		userService.addUser(login, email);
	}

	@Test(expected = InvalidEmailException.class)
	public void testAddUserEmptyEmail() throws InvalidEmailException,
			InvalidLoginException {
		String login = "usuario";
		String email = "";
		userService.addUser(login, email);
	}

	@Test(expected = InvalidLoginException.class)
	public void testAddUserEmptyLogin() throws InvalidEmailException,
			InvalidLoginException {
		String login = "";
		String email = "email@gmail.com";
		userService.addUser(login, email);
	}

	@Test
	public void testGetUserRanking() {
		String login = "usuario";
		String email = "usuario@gmail.com";
		Mockito.when(users.findBy(login, email)).thenReturn(getUserTest());
		Mockito.when(movies.movies()).thenReturn(getMoviesTest());
		Map<String, Integer> userRanking = userService
				.userRanking(login, email);
		Assert.assertEquals(userRanking.get("Super Man").intValue(), 2);
		Assert.assertEquals(userRanking.get("Wolverine").intValue(), 1);
		Assert.assertEquals(userRanking.get("Akira").intValue(), 0);
		Assert.assertEquals(userRanking.get("RED 2").intValue(), 0);

	}

	@Test
	public void testGetGeneralUserRanking() {
		Mockito.when(users.list()).thenReturn(getUsersTest());
		Mockito.when(movies.movies()).thenReturn(getMoviesTest());
		Map<String, Integer> generalUserRanking = userService
				.generalUserRanking();
		Assert.assertNotNull(generalUserRanking);
		Assert.assertEquals(getGeneralRankingTest(), generalUserRanking);
	}

	private Object getGeneralRankingTest() {
		HashMap<String, Integer> ranking = new HashMap<String, Integer>();
		ranking.put("Wolverine", 3);
		ranking.put("Super Man", 4);
		ranking.put("Akira", 0);
		ranking.put("RED 2", 0);
		return ranking;
	}

	private List<User> getUsersTest() {
		List<User> usersList = new ArrayList<User>();
		User user = new User();
		user.setLogin("usuario");
		user.setEmail("usuario@gmail.com");
		Movie movie = new Movie();
		movie.setName("Super Man");
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Super Man");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Wolverine");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		user.setVotes(votes);
		usersList.add(user);
		user = new User();
		user.setLogin("usuario");
		user.setEmail("usuario@gmail.com");
		movie = new Movie();
		movie.setName("Super Man");
		votes = new ArrayList<Vote>();
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Super Man");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Wolverine");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		user.setVotes(votes);
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		user.setVotes(votes);
		usersList.add(user);
		return usersList;
	}

	private List<Movie> getMoviesTest() {
		List<Movie> movies = new ArrayList<Movie>();
		Movie movie = new Movie();
		movie.setName("RED 2");
		movies.add(movie);
		movie = new Movie();
		movie.setName("Akira");
		movies.add(movie);
		return movies;
	}

	private User getUserTest() {
		User user = new User();
		user.setLogin("usuario");
		user.setEmail("usuario@gmail.com");
		Movie movie = new Movie();
		movie.setName("Super Man");
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Super Man");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		movie = new Movie();
		movie.setName("Wolverine");
		vote = new Vote();
		vote.setVoteDate(new Date());
		vote.setMovie(movie);
		votes.add(vote);
		user.setVotes(votes);
		return user;
	}

}
