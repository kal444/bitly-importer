package com.yellowaxe.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConstants;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author kal
 * 
 *         factory bean to create the oauth service using scribe
 * 
 *         this is just plumbing for Spring
 */
public class OAuthServiceFactoryBean implements FactoryBean<OAuthService> {

    private Class<? extends Api> clazz;

    private String apiKey;

    private String apiSecret;

    private String callbackUrl;

    public OAuthServiceFactoryBean(Class<? extends Api> clazz,
            String apiKey, String apiSecret, String callbackUrl) {
        super();
        this.clazz = clazz;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.callbackUrl = callbackUrl;
    }

    public OAuthServiceFactoryBean(Class<? extends Api> clazz,
            String apiKey, String apiSecret) {
        this(clazz, apiKey, apiSecret, OAuthConstants.OUT_OF_BAND);
    }

    public OAuthService getObject() throws Exception {
        return new ServiceBuilder().provider(clazz)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(callbackUrl)
                .build();
    }

    public Class<?> getObjectType() {
        return OAuthService.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
