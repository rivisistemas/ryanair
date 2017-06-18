/**
 * 
 */
package com.ryanair.flights.exception;

/**
 * Excepcion de aplicacion que encapsula y devuelve codigo de error para response
 * @author antonio.delrio
 *
 */
public class FlightsException extends Exception {

	
	private static final long serialVersionUID = -1331432426618939966L;

	private Integer errorCode;
	
	public enum FLIGHTS_CODE {
		OK(200),ERROR(500),NOT_FOUND(404);
		private final Integer errorCode;
		
		public Integer getErrorCode() {
			return errorCode;
		}

		FLIGHTS_CODE(Integer errorCode) {
			this.errorCode = errorCode;
		}
	}
	
	public enum FLIGHTS_ERROR_MSG {
		DATES_INTERVAL_EXECUTION_ERROR,DATE_FORMAT_ERROR
	}
	
	public FlightsException(Integer errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}
	
	public FlightsException(FLIGHTS_CODE errorCode, String message) {
		
		super(message);
		this.setErrorCode(errorCode.getErrorCode());
	}
	/**
	 * @param message
	 */
	public FlightsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FlightsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FlightsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FlightsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
