package br.org.top5.repository;

import java.util.List;

import br.org.top5.model.User;


public interface Users {

	public boolean add(User user);

	public User findBy(String login, String email);

	public List<User> list();


}
