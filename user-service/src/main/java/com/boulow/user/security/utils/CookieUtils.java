package com.boulow.user.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.boulow.user.config.BoulowProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class CookieUtils {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Autowired
    BoulowProperties boulowProperties;

	public Cookie getCookie(String name) {
		return WebUtils.getCookie(httpServletRequest, name);
	}

	public void setCookie(String name, String value, int expiryInMinutes) {
		int expiresInSeconds = expiryInMinutes * 60 * 60;
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(boulowProperties.getSecurity().getCookieProps().isSecure());
		cookie.setPath(boulowProperties.getSecurity().getCookieProps().getPath());
		cookie.setDomain(boulowProperties.getSecurity().getCookieProps().getDomain());
		cookie.setMaxAge(expiresInSeconds);
		httpServletResponse.addCookie(cookie);
	}

	public void setSecureCookie(String name, String value, int expiryInMinutes) {
		int expiresInSeconds = expiryInMinutes * 60 * 60;
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(boulowProperties.getSecurity().getCookieProps().isHttpOnly());
		cookie.setSecure(boulowProperties.getSecurity().getCookieProps().isSecure());
		cookie.setPath(boulowProperties.getSecurity().getCookieProps().getPath());
		cookie.setDomain(boulowProperties.getSecurity().getCookieProps().getDomain());
		cookie.setMaxAge(expiresInSeconds);
		httpServletResponse.addCookie(cookie);
	}

	public void setSecureCookie(String name, String value) {
		int expiresInMinutes = boulowProperties.getSecurity().getCookieProps().getMaxAgeInMinutes();
		setSecureCookie(name, value, expiresInMinutes);
	}

	public void deleteSecureCookie(String name) {
		int expiresInSeconds = 0;
		Cookie cookie = new Cookie(name, null);
		cookie.setHttpOnly(boulowProperties.getSecurity().getCookieProps().isHttpOnly());
		cookie.setSecure(boulowProperties.getSecurity().getCookieProps().isSecure());
		cookie.setPath(boulowProperties.getSecurity().getCookieProps().getPath());
		cookie.setDomain(boulowProperties.getSecurity().getCookieProps().getDomain());
		cookie.setMaxAge(expiresInSeconds);
		httpServletResponse.addCookie(cookie);
	}
	
	public void deleteCookie(String name) {
		int expiresInSeconds = 0;
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(boulowProperties.getSecurity().getCookieProps().getPath());
		cookie.setDomain(boulowProperties.getSecurity().getCookieProps().getDomain());
		cookie.setMaxAge(expiresInSeconds);
		httpServletResponse.addCookie(cookie);
	}

}