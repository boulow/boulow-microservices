package com.boulow.user.exception;

public class InvalidArgumentException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9211233610817185036L;

	public InvalidArgumentException(String message){
        super(message);
    }
}
