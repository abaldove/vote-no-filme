package br.org.top5.service;

import java.util.List;

import lombok.extern.log4j.Log4j;

import br.com.caelum.vraptor.ioc.Component;
import br.org.top5.model.Movie;
import br.org.top5.repository.Movies;
import br.org.top5.util.ImdbRating;

@Component
@Log4j
public class MoviesServiceImpl implements MoviesService {

	private Movies movies;

	public MoviesServiceImpl(Movies movies) {
		super();
		this.movies = movies;
	}

	public List<Movie> moviesList() {
		List<Movie> moviesList = movies.movies();
		for (Movie movie : moviesList) {
			ImdbRating.getInstance().imdbRating(movie);
			movie.setFormatedReleaseDate();
		}
		return moviesList;
	}

	@Override
	public Movie findBy(long movieId) {
		Movie movie = new Movie();
		try {
			movie = movies.findBy(movieId);
		} catch (Exception exception) {
			log.error("Filme com id:" + movieId + " não encontrado.");
		}

		return movie;
	}

}
