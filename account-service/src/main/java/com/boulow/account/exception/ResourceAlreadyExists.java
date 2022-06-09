package com.boulow.account.exception;

public class ResourceAlreadyExists extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9211233610817185036L;

	public ResourceAlreadyExists(String message){
        super(message);
    }
}
