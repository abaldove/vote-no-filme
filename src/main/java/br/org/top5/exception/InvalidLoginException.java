package br.org.top5.exception;

public class InvalidLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5156203966822931901L;

	private String mistake;

	public InvalidLoginException(String mistake) {
		super();
		this.mistake = mistake;
	}

	@Override
	public String getMessage() {
		return mistake;
	}

}
