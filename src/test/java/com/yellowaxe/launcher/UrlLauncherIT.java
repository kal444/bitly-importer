package com.yellowaxe.launcher;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UrlLauncherIT {

    @Inject
    private UrlLauncher urlLauncher;

    @Ignore
    @Test
    public void testLaunch() {
        assertNotNull(urlLauncher);
        urlLauncher.launch("http://www.google.com");
    }

}
