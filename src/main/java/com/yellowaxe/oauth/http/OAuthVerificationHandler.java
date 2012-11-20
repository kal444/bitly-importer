package com.yellowaxe.oauth.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * @author kal
 * 
 *         handles the saving of the verification code from an oauth provider
 */
public class OAuthVerificationHandler extends AbstractHandler {

    private String parameterName;

    private LastOAuthVerificationCode lastOAuthVerificationCode;

    public OAuthVerificationHandler(String parameterName,
            LastOAuthVerificationCode lastOAuthVerificationCode) {
        this.parameterName = parameterName;
        this.lastOAuthVerificationCode = lastOAuthVerificationCode;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        PrintWriter responseWriter = response.getWriter();

        String accessTokenCode = request.getParameter(parameterName);
        if (accessTokenCode != null) {
            lastOAuthVerificationCode.setLastVerificationCode(accessTokenCode);
            responseWriter.println("verified");
        } else {
            responseWriter.println("done");
        }

    }

}
