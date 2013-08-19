package br.org.top5.exception;

public class InvalidEmailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 516372507766442274L;

	private String mistake;

	public InvalidEmailException(String mistake) {
		super();
		this.mistake = mistake;
	}

	@Override
	public String getMessage() {
		return mistake;
	}

}
