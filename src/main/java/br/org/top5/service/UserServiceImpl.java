package br.org.top5.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;
import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.repository.Movies;
import br.org.top5.repository.Users;
import br.org.top5.util.Validator;

@Component
@Log4j
public class UserServiceImpl implements UserService {

	private Users users;

	private Movies movies;

	public UserServiceImpl(Users users, Movies movies) {
		super();
		this.users = users;
		this.movies = movies;
	}

	@Override
	public User addUser(String login, String email)
			throws InvalidEmailException, InvalidLoginException {
		Validator.getInstance().validateEmail(email).validateLogin(login);
		User user = new User();
		user.setLogin(login);
		user.setEmail(email);
		users.add(user);
		return user;
	}

	@Override
	public Map<String, Integer> userRanking(String login, String email) {
		User user = this.findBy(login, email);
		Map<String, Integer> userRanking = user.getUserRanking();
		fillRankingWithAllMovies(userRanking);
		return userRanking;
	}

	private void fillRankingWithAllMovies(Map<String, Integer> ranking) {
		for (Movie movie : movies.movies()) {
			Integer votes = ranking.get(movie.getName());
			if (votes == null) {
				ranking.put(movie.getName(), 0);
			}
		}
	}

	public User findBy(String login, String email) {
		User user = new User();
		try {
			user = users.findBy(login, email);
		} catch (Exception e) {
			log.info("Usuario não encontrado, retornando uma instancia vazia");
		}
		return user;
	}

	@Override
	public Map<String, Integer> generalUserRanking() {
		Map<String, Integer> generalRanking = new HashMap<String, Integer>();
		List<User> usersList = users.list();
		for (User user : usersList) {
			Map<String, Integer> userRanking = user.getUserRanking();
			for (String key : userRanking.keySet()) {
				Integer generalVotes = generalRanking.get(key);
				if (null != generalVotes) {
					Integer userVotes = userRanking.get(key);
					int incrementedVotes = generalVotes + userVotes;
					generalRanking.put(key, incrementedVotes);
				} else {
					Integer userVotes = userRanking.get(key);
					generalRanking.put(key, userVotes);
				}
			}
		}
		fillRankingWithAllMovies(generalRanking);
		return generalRanking;
	}

}
