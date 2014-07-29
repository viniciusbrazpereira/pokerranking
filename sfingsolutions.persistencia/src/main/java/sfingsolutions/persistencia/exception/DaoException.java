package sfingsolutions.persistencia.exception;

public class DaoException extends Exception {
	public DaoException(String cause) {
		super(cause);
	}
	
	public DaoException(Exception cause) {
		super(cause);
	}

}
