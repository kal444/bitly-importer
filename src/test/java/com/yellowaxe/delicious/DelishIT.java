package com.yellowaxe.delicious;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DelishIT {

    @Inject
    private Delish target;

    @Test
    public void testInjection() throws Exception {
        assertNotNull(target);
    }

}
