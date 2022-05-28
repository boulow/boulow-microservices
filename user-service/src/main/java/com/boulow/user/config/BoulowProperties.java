package com.boulow.user.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Data;

@Configuration
@ConfigurationProperties(
	    prefix = "boulow",
	    ignoreUnknownFields = false
	)
@Data
public class BoulowProperties {

    private final CorsConfiguration cors = new CorsConfiguration();
    private final BoulowProperties.Security security = new BoulowProperties.Security();
    private final BoulowProperties.Firebase firebaseProps = new BoulowProperties.Firebase();
    
    @Data
    public static class Firebase {
    	private int sessionExpiryInDays;
    	private String databaseUrl;
    	private boolean enableStrictServerSession;
    	private boolean enableCheckSessionRevoked;
    	private boolean enableLogoutEverywhere;

        public Firebase() {
        }

    }
    
    @Data
    public static class Security {
    	private BoulowProperties.Security.CookieProperties cookieProps = new BoulowProperties.Security.CookieProperties();
    	private List<String> allowedPublicApis;

    	public Security() {
        }
    	
    	@Data
        public static class CookieProperties {
        	private String domain;
        	private String path;
        	private boolean httpOnly;
        	private boolean secure;
        	private int maxAgeInMinutes;

            public CookieProperties() {
            }

        }
    }
  
}
