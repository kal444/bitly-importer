package com.yellowaxe.bitly;

import static java.lang.String.format;

import java.util.Date;

import javax.inject.Inject;

import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.UserLinkEditResponse;
import net.swisstech.bitly.model.v3.UserLinkSaveResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;
import com.yellowaxe.launcher.UrlLauncher;
import com.yellowaxe.oauth.http.LastOAuthVerificationCode;

/**
 * @author kal
 * 
 *         My wrapper around bitly webservice
 */
@Component
public class Bitter {

    private static final Logger LOG = Logger.getLogger(Bitter.class);

    private OAuthService bitlyService;

    private BitterTokenHelper bitterTokenHelper;

    private UrlLauncher urlLauncher;

    private LastOAuthVerificationCode lastOAuthVerificationCode;

    @Inject
    public Bitter(OAuthService bitlyService,
            BitterTokenHelper bitterTokenHelper,
            UrlLauncher urlLauncher,
            LastOAuthVerificationCode lastOAuthVerificationCode) {
        super();
        this.bitlyService = bitlyService;
        this.bitterTokenHelper = bitterTokenHelper;
        this.urlLauncher = urlLauncher;
        this.lastOAuthVerificationCode = lastOAuthVerificationCode;
    }

    public OAuthService getBitlyService() {
        return bitlyService;
    }

    public void saveBitmark(String longUrl, String title, String note,
            Date timestamp, boolean privat) {

        BitlyClient client = new BitlyClient(getAccessToken());

        try {
            Response<UserLinkSaveResponse> saveResp = client.userLinkSave()
                    .setLongUrl(longUrl)
                    .setTitle(title)
                    .setNote(note)
                    .setPrivate(privat)
                    .setUserTs(new DateTime(timestamp))
                    .call();

            if (saveResp.status_code == 304) {
                LOG.info("existing, link, overwriting");
                Response<UserLinkEditResponse> editResp = client.userLinkEdit()
                        .setLink(saveResp.data.link_save.link)
                        .setTitle(title)
                        .setNote(note)
                        .setPrivate(privat)
                        .setUserTs(new DateTime(timestamp))
                        .call();
                logResponse(editResp, longUrl);
            } else {
                logResponse(saveResp, longUrl);
            }
        } catch (Throwable e) {
            LOG.error(format("Unable to save: %s", longUrl));
            LOG.error(Throwables.getStackTraceAsString(e));
        }
    }

    private void logResponse(Response<?> response, String longUrl) {
        LOG.info(String.format("Received %d - %s for %s", response.status_code, response.status_txt, longUrl));
        if (response.status_code != 200) {
            LOG.warn(response.data);
        }
    }

    public String getAccessToken() {
        String currentAccessToken = bitterTokenHelper.loadAccessToken();
        if (currentAccessToken != null) {
            return currentAccessToken;
        } else {
            refreshAccessToken();
            return bitterTokenHelper.loadAccessToken();
        }
    }

    private void refreshAccessToken() {
        String authorizationUrl = bitlyService.getAuthorizationUrl(null);
        LOG.debug(format("authorizationUrl: %s", authorizationUrl));

        // clear out the last verification code
        lastOAuthVerificationCode.setLastVerificationCode(null);

        // show webpage for user to verify
        urlLauncher.launch(authorizationUrl);
        LOG.info("waiting for user to verify request");

        try {
            while (lastOAuthVerificationCode.getLastVerificationCode() == null) {
                Thread.sleep(5 * 1000);
            }
        } catch (InterruptedException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
        }

        Verifier verifier = new Verifier(lastOAuthVerificationCode.getLastVerificationCode());
        Token accessToken = bitlyService.getAccessToken(null, verifier);
        LOG.debug(format("accessToken: %s", accessToken.getToken()));

        bitterTokenHelper.saveAccessToken(accessToken.getToken());
    }

}
