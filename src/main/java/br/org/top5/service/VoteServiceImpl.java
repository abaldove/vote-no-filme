package br.org.top5.service;

import java.util.Date;
import java.util.List;

import lombok.extern.log4j.Log4j;
import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.Vote;
import br.org.top5.model.VoteVo;
import br.org.top5.repository.Votes;
import br.org.top5.util.Validator;

@Component
@Log4j
public class VoteServiceImpl implements VoteService {

	private static final long INVALID_ID = 0;

	private MoviesService moviesService;

	private UserService userService;

	private Votes votes;

	public VoteServiceImpl(MoviesService moviesService,
			UserService userService, Votes votes) {
		super();
		this.moviesService = moviesService;
		this.userService = userService;
		this.votes = votes;
	}

	@Override
	public void addVote(User user, Movie movie) {

		Vote vote = new Vote();
		vote.setMovie(movie);
		vote.setUser(user);
		vote.setVoteDate(new Date());
		votes.add(vote);
		log.info("Voto adicionado para o filme:" + movie.getName()
				+ " pelo usuario:" + user.getLogin());
	}

	@Override
	public boolean addVotes(List<VoteVo> votes, String login, String email) {
		try {
			Validator.getInstance().validateEmail(email).validateLogin(login)
					.validateList(votes);
			log.info("Buscando usuario com login:" + login + " email:" + email);
			User user = userService.findBy(login, email);
			if (user.getId() == INVALID_ID) {
				log.info("Usuario não existe, criando um");
				user = userService.addUser(login, email);
			}
			for (VoteVo voteVo : votes) {
				Movie movie = moviesService.findBy(voteVo.getMovieId());
				this.addVote(user, movie);
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
