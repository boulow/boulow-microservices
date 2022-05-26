package com.boulow.user.exception;

public class NoSuchElementFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9211233610817185036L;

	public NoSuchElementFoundException(String message){
        super(message);
    }
}
