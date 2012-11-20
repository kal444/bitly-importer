package com.yellowaxe.launcher;

import static java.awt.Desktop.isDesktopSupported;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.stereotype.Component;

/**
 * @author kal
 * 
 *         java 6 desktop action to launcher a browser so that the user can
 *         allow the oauth authentication to happen
 */
@Component
public class UrlLauncher {

    public void launch(String url) {
        if (!isDesktopSupported()) {
            throw new UnsupportedOperationException("java.awt.Desktop isn't supported by your JRE");
        }

        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            throw new UnsupportedOperationException("BROWSE Desktop Action isn't supported by your JRE");
        }

        try {
            URI uri = new URI(url);
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
