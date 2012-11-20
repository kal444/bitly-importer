package com.yellowaxe.googlereader.json;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.yellowaxe.googlereader.json.GoogleReaderJsonData.Item;

@ContextConfiguration("classpath:/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GoogleReaderJsonParserIT {

    @Inject
    private GoogleReaderJsonParser target;

    @Test
    public void testInjection() throws Exception {
        assertNotNull(target);

        InputStream jsonStream =
                GoogleReaderJsonParserIT.class.getClassLoader()
                        .getResourceAsStream(
                                format("all4kh-googlereader-json-files/%s",
                                        GoogleReaderJsonFiles.STARRED.filename()));

        GoogleReaderJsonData data = target.parse(jsonStream);

        for (Item item : data.getItems()) {
            System.out.println(item.getAuthor());
            System.out.println(item.getTitle());
            System.out.println(item.getAlternate());
            System.out.println(item.getCanonical());
            System.out.println(Iterables.filter(item.getCategories(),
                    Predicates.not(Predicates.contains(Pattern.compile("state.com.google")))));
            System.out.println(item.getOrigin());
        }
    }
}
