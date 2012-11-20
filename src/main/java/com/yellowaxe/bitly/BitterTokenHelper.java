package com.yellowaxe.bitly;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;
import com.google.common.io.Files;

/**
 * @author kal
 * 
 *         simple class to manage the access token cache file
 */
@Component
public class BitterTokenHelper {
    
    private static final Logger LOG = Logger.getLogger(BitterTokenHelper.class);

    private String tokenFilename;

    @Inject
    public BitterTokenHelper(
            @Value("#{bitlyProperties['bitly.tokenFilename']}") String tokenFilename) {
        this.tokenFilename = tokenFilename;
    }

    public String loadAccessToken() {
        if (hasAccessToken()) {
            return readAccessTokenFromFile();
        }
        return null;
    }

    String readAccessTokenFromFile() {
        try {
            List<String> lines = Files.readLines(getTokenFilePath(), Charset.defaultCharset());
            return lines.get(0);
        } catch (IOException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    public void saveAccessToken(String accessToken) {
        try {
            Files.write(accessToken, getTokenFilePath(), Charset.defaultCharset());
        } catch (IOException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
        }
    }

    boolean hasAccessToken() {
        File tokenFile = getTokenFilePath();
        if (tokenFile.isFile() && tokenFile.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    String getParentPath() {
        String path = BitterTokenHelper.class.getClassLoader()
                .getResource(".")
                .getPath();
        String parentPath = new File(path).getParent();
        return parentPath;
    }

    File getTokenFilePath() {
        File tokenFile = new File(getParentPath(), tokenFilename);
        LOG.debug(String.format("token file location: %s", tokenFile.getPath()));
        return tokenFile;
    }

}
