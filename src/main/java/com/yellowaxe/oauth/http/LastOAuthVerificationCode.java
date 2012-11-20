package com.yellowaxe.oauth.http;

import org.springframework.stereotype.Component;

/**
 * @author kal
 * 
 *         synchronization pojo for storing the last verification code received
 */
@Component
public class LastOAuthVerificationCode {

    private String lastVerificationCode;

    public String getLastVerificationCode() {
        return lastVerificationCode;
    }

    synchronized public void setLastVerificationCode(String lastVerificationCode) {
        this.lastVerificationCode = lastVerificationCode;
    }

}
