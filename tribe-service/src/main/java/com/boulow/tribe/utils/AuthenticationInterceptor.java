package com.boulow.tribe.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class AuthenticationInterceptor implements RequestInterceptor {
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public void apply(RequestTemplate requestTemplate) {
		logger.info("Apply method of RequestInterceptor");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String language = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        if (language == null) {
            return;
        }
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if(authorization.startsWith(BEARER_TOKEN_TYPE))
        	requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, authorization.substring(7)));
        
        	requestTemplate.header(ACCEPT_LANGUAGE_HEADER, language);
		
	}

}
