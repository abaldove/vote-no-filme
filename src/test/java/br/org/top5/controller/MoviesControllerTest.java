package br.org.top5.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockResult;
import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.VoteVo;
import br.org.top5.service.MoviesService;
import br.org.top5.service.UserService;
import br.org.top5.service.VoteService;

public class MoviesControllerTest {

	@Mock
	private MoviesService moviesService;

	@Mock
	private UserService userService;

	@Mock
	private VoteService voteService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMovies() {
		MockResult result = new MockResult();
		Mockito.when(moviesService.moviesList()).thenReturn(
				new ArrayList<Movie>());
		MoviesController moviesController = new MoviesController(moviesService,
				userService, voteService, result);
		moviesController.getMovies();
		Mockito.verify(moviesService, Mockito.atLeastOnce()).moviesList();
	}

	@Test
	public void testUserRanking() {
		MockResult result = new MockResult();
		Mockito.when(moviesService.moviesList()).thenReturn(
				new ArrayList<Movie>());
		MoviesController moviesController = new MoviesController(moviesService,
				userService, voteService, result);
		String login = "usuario";
		String email = "usuario@gmail.com";
		moviesController.userRanking(login, email);
		Mockito.verify(userService, Mockito.atLeastOnce()).userRanking(login,
				email);
	}

	@Test
	public void testGeneralRanking() {
		MockResult result = new MockResult();
		Mockito.when(moviesService.moviesList()).thenReturn(
				new ArrayList<Movie>());
		MoviesController moviesController = new MoviesController(moviesService,
				userService, voteService, result);
		moviesController.generalRanking();
		Mockito.verify(userService, Mockito.atLeastOnce()).generalUserRanking();
	}

	@Test
	public void testSaveVotes() {
		MockResult result = new MockResult();
		MoviesController moviesController = new MoviesController(moviesService,
				userService, voteService, result);
		String login = null;
		String email = null;
		List<VoteVo> votes = new ArrayList<VoteVo>();
		moviesController.addVotes(votes, login, email);
		Mockito.verify(voteService, Mockito.atLeastOnce()).addVotes(votes,
				login, email);
	}

	@Test
	public void testAddUser() throws InvalidEmailException,
			InvalidLoginException {
		MockResult result = new MockResult();
		String login = "user";
		String email = "user@gmail.com";
		Mockito.when(userService.findBy(login, email)).thenReturn(new User());
		MoviesController moviesController = new MoviesController(moviesService,
				userService, voteService, result);
		moviesController.addUser(login, email);
		Mockito.verify(userService, Mockito.atLeastOnce())
				.addUser(login, email);
	}

}
