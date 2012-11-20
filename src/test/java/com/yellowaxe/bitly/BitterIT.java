package com.yellowaxe.bitly;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scribe.oauth.OAuthService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BitterIT {

    @Inject
    private Bitter target;

    @Test
    public void testGetBitlyService() throws Exception {
        assertNotNull(target);
        OAuthService bitlyService = target.getBitlyService();
        assertNotNull(bitlyService);
    }

    @Ignore
    @Test
    public void testGetAccessToken() throws Exception {
        String accessToken = target.getAccessToken();
        System.out.println(accessToken);
    }

}
