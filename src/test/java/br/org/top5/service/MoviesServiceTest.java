package br.org.top5.service;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import br.org.top5.model.Movie;
import br.org.top5.repository.Movies;

public class MoviesServiceTest {

	@Mock
	private Movies movies;

	private List<Movie> moviesResult;

	private List<Movie> moviesInput;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		initMoviesResult();
	}

	@Test
	public void testMoviesList() {

		Mockito.when(movies.movies()).thenReturn(moviesInput);
		MoviesService moviesService = new MoviesServiceImpl(movies);
		List<Movie> moviesList = moviesService.moviesList();
		Mockito.verify(movies, Mockito.atLeastOnce()).movies();
		assertNotNull(moviesList);
		Assert.assertEquals(moviesList.get(0).getImdbRating(), moviesResult
				.get(0).getImdbRating());
	}

	@Test
	public void testFindById() {
		long movieId = 1l;
		Mockito.when(movies.findBy(movieId)).thenReturn(moviesInput.get(0));
		MoviesService moviesService = new MoviesServiceImpl(movies);
		Movie movie = moviesService.findBy(movieId);
		Assert.assertNotNull(movie);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWontFindById() {
		long movieId = 1l;
		Mockito.when(movies.findBy(movieId)).thenThrow(NoResultException.class);
		MoviesService moviesService = new MoviesServiceImpl(movies);
		Movie movie = moviesService.findBy(movieId);
		Assert.assertNotNull(movie);
		Assert.assertEquals(0, movie.getId());
	}

	@Test
	public void restyTest() {
		Resty r = new Resty();
		try {
			JSONResource resource = r
					.json("http://deanclatworthy.com/imdb/?id=tt1430132");
			Object name = resource.get("title");
			Object rating = resource.get("rating");
			Assert.assertEquals(name, "The Wolverine");
			Assert.assertEquals(rating, "7.1");
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}

	@Test(expected = JSONException.class)
	public void restyTestNotFound() throws Exception {
		Resty r = new Resty();
		try {
			JSONResource resource = r
					.json("http://deanclatworthy.com/imdb/?id=tt");
			Object name = resource.get("title");
			Object rating = resource.get("rating");
			Assert.assertEquals(name, "The Wolverine");
			Assert.assertEquals(rating, "7.1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initMoviesResult() {
		Movie movie = new Movie();
		movie.setName("The Wolwerine");
		movie.setImdbId("tt1430132");
		movie.setReleaseDate(new Date());
		moviesInput = new ArrayList<Movie>();
		moviesInput.add(movie);
		movie = new Movie();
		movie.setName("The Wolwerine");
		movie.setImdbRating("7.1");
		movie.setReleaseDate(new Date());
		moviesResult = new ArrayList<Movie>();
		moviesResult.add(movie);

	}

}
