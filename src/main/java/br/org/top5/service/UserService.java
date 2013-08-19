package br.org.top5.service;

import java.util.Map;

import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.model.User;

public interface UserService {

	public User addUser(String login, String email)
			throws InvalidEmailException, InvalidLoginException;

	public Map<String,Integer> userRanking(String login, String email);

	public Map<String, Integer> generalUserRanking();

	public User findBy(String login, String email);

}
