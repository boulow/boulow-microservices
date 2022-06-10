package com.boulow.document.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.boulow.document.config.BoulowProperties;
import com.boulow.document.model.AppUser;
import com.boulow.document.security.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;


@Service
public class SecurityService {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CookieUtils cookieUtils;

    @Autowired
    private BoulowProperties boulowProperties;

    public AppUser getUser() {
    	AppUser userPrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof AppUser) {
            userPrincipal = ((AppUser) principal);
        }
        return userPrincipal;
    }

    public Credentials getCredentials() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (Credentials) securityContext.getAuthentication().getCredentials();
    }

    public boolean isPublic() {
        return boulowProperties.getSecurity().getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
    
}
