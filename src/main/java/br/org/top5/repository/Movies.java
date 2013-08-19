package br.org.top5.repository;

import java.util.List;

import br.org.top5.model.Movie;

public interface Movies {

	public List<Movie> movies();

	public Movie findBy(long movieId);

}
