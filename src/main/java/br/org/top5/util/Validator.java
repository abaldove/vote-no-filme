package br.org.top5.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j;
import br.org.top5.exception.InvalidEmailException;
import br.org.top5.exception.InvalidLoginException;
import br.org.top5.exception.InvalidaListException;

@Log4j
public class Validator {

	private static Validator instance;
	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Validator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public static Validator getInstance() {
		if (instance == null) {
			instance = new Validator();
		}

		return instance;
	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 * @throws InvalidEmailException
	 */
	public Validator validateEmail(final String hex)
			throws InvalidEmailException {

		matcher = pattern.matcher(hex);
		if (!matcher.matches()) {
			throw new InvalidEmailException("Email informado é invalido");
		}

		return this;

	}

	/**
	 * 
	 * @param login
	 * @throws InvalidLoginException
	 */
	public Validator validateLogin(final String login)
			throws InvalidLoginException {
		if (login == null || login.equals("")) {
			InvalidLoginException exception = new InvalidLoginException(
					"O login esta vazio.");
			log.error("O login esta vazio ou nulo.", exception);
			throw exception;
		}
		return this;
	}

	/**
	 * 
	 * @param list
	 * @return
	 * @throws InvalidaListException
	 */
	public Validator validateList(List<?> list) throws InvalidaListException {
		if (list == null || list.isEmpty()) {
			InvalidaListException invalidaListException = new InvalidaListException(
					"Lista vazia ou nula");
			log.error("Lista vazia ou nula", invalidaListException);
			throw invalidaListException;
		}
		return this;
	}

}
