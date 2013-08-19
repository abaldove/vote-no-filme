package br.org.top5.service;

import java.util.List;

import br.org.top5.model.Movie;
import br.org.top5.model.User;
import br.org.top5.model.VoteVo;

public interface VoteService {

	public void addVote(User user, Movie movie);

	public boolean addVotes(List<VoteVo> votes, String login, String email);

}
