package br.org.top5.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.Vote;
import br.org.top5.model.VoteVo;
import br.org.top5.repository.Votes;

public class VotesServiceImplTest {

	private VoteService votesService;

	@Mock
	private UserService userService;

	@Mock
	private MoviesService moviesService;

	@Mock
	private Votes votes;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		votesService = new VoteServiceImpl(moviesService, userService, votes);
	}

	@Test
	public void testAddVote() {
		Mockito.when(votes.add(Mockito.any(Vote.class))).thenReturn(true);
		votesService.addVote(getTestUser(), getTestMovie());
		Mockito.verify(votes, Mockito.atLeastOnce()).add(
				Mockito.any(Vote.class));

	}

	@Test
	public void testAddVotes() {
		String login = "usuario";
		String email = "usuario1@gmail.com";
		List<VoteVo> votesList = new ArrayList<VoteVo>();
		votesService.addVotes(votesList, login, email);
	}

	@Test
	public void testAddVotesNullList() {
		String login = "usuario";
		String email = "usuario1@gmail.com";
		List<VoteVo> votesList = null;
		boolean result = votesService.addVotes(votesList, login, email);
		Assert.assertFalse(result);
	}

	@Test
	public void testAddVotesEmptyList() {
		String login = "usuario";
		String email = "usuario1@gmail.com";
		List<VoteVo> votesList = new ArrayList<VoteVo>();
		boolean result = votesService.addVotes(votesList, login, email);
		Assert.assertFalse(result);
	}

	@Test
	public void testAddVotesEmptyLogin() {
		String login = "";
		String email = "usuario1@gmail.com";
		List<VoteVo> votesList = new ArrayList<VoteVo>();
		VoteVo voteVo = new VoteVo();
		voteVo.setMovieId(1l);
		voteVo.setVotesQnty(10);
		votesList.add(voteVo);
		boolean result = votesService.addVotes(votesList, login, email);
		Assert.assertFalse(result);
	}

	private User getTestUser() {
		User user = new User();
		user.setId(1l);
		user.setEmail("usuario@gmail.com");
		return user;
	}

	private Movie getTestMovie() {
		Movie movie = new Movie();
		movie.setId(1l);
		movie.setName("The Wolverine");
		return movie;
	}

}
