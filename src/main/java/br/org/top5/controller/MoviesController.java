package br.org.top5.controller;

import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.org.top5.annotation.Transactional;
import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.model.User;
import br.org.top5.model.VoteVo;
import br.org.top5.service.MoviesService;
import br.org.top5.service.UserService;
import br.org.top5.service.VoteService;
import br.org.top5.util.SortMapValue;

@Resource
@Log4j
public class MoviesController {

	private MoviesService moviesService;

	private UserService userService;

	private VoteService voteService;

	private Result result;

	public MoviesController(MoviesService moviesService,
			UserService userService, VoteService voteService, Result result) {
		super();
		this.moviesService = moviesService;
		this.userService = userService;
		this.result = result;
		this.voteService = voteService;

	}

	@Get
	@Path("/movies")
	public void getMovies() {
		result.use(Results.json()).from(moviesService.moviesList(), "movie").exclude("releaseDate")
				.serialize();
	}

	@Get
	public void userRanking(String login, String email) {
		Map<String, Integer> userRanking = userService
				.userRanking(login, email);

		result.use(Results.json())
				.from(SortMapValue.descendingOrder(userRanking), "userRating")
				.serialize();
	}

	@Get
	public void generalRanking() {
		Map<String, Integer> generalUserRanking = userService
				.generalUserRanking();
		result.use(Results.json()).from(generalUserRanking, "generalRanking")
				.serialize();
	}

	@Post
	@Transactional
	public void addUser(String login, String email) {
		User user = userService.findBy(login, email);
		if (user.getId() == 0) {
			try {
				userService.addUser(login, email);
			} catch (InvalidEmailException e) {
				log.error("Email invalido");
			} catch (InvalidLoginException e) {
				log.error("Login invalido");
			}
		}
	}

	@Post
	@Transactional
	public boolean addVotes(List<VoteVo> votes, String login, String email) {
		return voteService.addVotes(votes, login, email);
	}

}
