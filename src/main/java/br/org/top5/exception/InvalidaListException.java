package br.org.top5.exception;

public class InvalidaListException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 32822937992309205L;
	private String mistake;

	public InvalidaListException(String mistake) {
		super();
		this.mistake = mistake;
	}

	@Override
	public String getMessage() {
		return mistake;
	}

}
