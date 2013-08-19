package br.org.top5.util;

import java.io.IOException;
import java.util.Properties;

import lombok.extern.log4j.Log4j;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import br.org.top5.model.Movie;

@Log4j
public class ImdbRating {

	private static final String RETY_ENABLED = "rety.disabled";
	private static ImdbRating instance;
	private static final String RETY_URL_KEY = "rety.url";
	private static final String N_A = "N/A";
	private static final String RATING = "rating";
	private Properties properties;
	private String retyUrl;
	private boolean retyDisabled;
	{
		properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(
					"top5.properties"));
		} catch (IOException e) {
			log.error("Erro carregando o arquivo top.properties", e);
		}
		retyUrl = properties.getProperty(RETY_URL_KEY);
		retyDisabled = Boolean.parseBoolean(properties
				.getProperty(RETY_ENABLED));
	}

	private ImdbRating() {

		super();
	}

	public static ImdbRating getInstance() {
		if (instance == null) {
			instance = new ImdbRating();
		}

		return instance;
	}

	public void imdbRating(Movie movie) {
		Resty r = new Resty();
		String rating = N_A;
		if (retyDisabled) {
			movie.setImdbRating(rating);
			return;
		}
		try {
			log.info("Consultando IMDB com o id:" + movie.getImdbId());
			JSONResource resource = r.json(retyUrl + movie.getImdbId());
			rating = (String) resource.get(RATING);
		} catch (Exception e) {
			log.error(
					"Erro no serviço http://deanclatworthy.com para consultar o IMDB rating",
					e);
		}
		movie.setImdbRating(rating);

	}

}
