package br.org.top5.service;

import java.util.List;

import br.org.top5.model.Movie;

public interface MoviesService {

	public List<Movie> moviesList();
	
	public Movie findBy(long movieId);
}
