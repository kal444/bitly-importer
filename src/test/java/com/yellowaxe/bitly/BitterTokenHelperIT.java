package com.yellowaxe.bitly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.Files;

@ContextConfiguration("classpath:/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BitterTokenHelperIT {

    private static final String DUMMY_ACCESS_TOKEN_VALUE = "0123269c9d12312fb8f52dbfe23252196c5a88";

    @Inject
    private BitterTokenHelper target;

    @Value("#{bitlyProperties['bitly.tokenFilename']}")
    private String testTokenFileName;

    private File tokenFilePath;

    private File dummyTokenFilePath;

    @Before
    public void setup() {
        tokenFilePath = new File(target.getParentPath(), testTokenFileName);
        dummyTokenFilePath = new File(BitterTokenHelperIT.class.getClassLoader()
                .getResource("./goodAccessToken.dummy")
                .getPath());
    }

    @Test
    public void testInjection() {
        assertNotNull(target);
    }

    @Test
    public void testHasAccessToken() throws Exception {
        tokenFilePath.delete();
        assertFalse(target.hasAccessToken());

        Files.copy(dummyTokenFilePath, tokenFilePath);
        assertTrue(target.hasAccessToken());
    }

    @Test
    public void testLoadAccessToken() throws Exception {
        tokenFilePath.delete();
        assertNull(target.loadAccessToken());

        Files.copy(dummyTokenFilePath, tokenFilePath);
        assertNotNull(target.loadAccessToken());
    }

    @Test
    public void testReadAccessTokenFromFile() throws Exception {
        Files.copy(dummyTokenFilePath, tokenFilePath);
        String accessTokenFromFile = target.readAccessTokenFromFile();

        // should match with what's in the file
        assertEquals(DUMMY_ACCESS_TOKEN_VALUE, accessTokenFromFile);
    }

    @Test
    public void testSaveAccessToken() throws Exception {
        tokenFilePath.delete();
        target.saveAccessToken(DUMMY_ACCESS_TOKEN_VALUE);
        
        String dummyValue = Files.toString(dummyTokenFilePath, Charset.defaultCharset());
        String savedValue = Files.toString(tokenFilePath, Charset.defaultCharset());
        
        assertEquals(dummyValue, savedValue);
    }

}
