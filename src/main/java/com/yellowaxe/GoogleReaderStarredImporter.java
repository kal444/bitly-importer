package com.yellowaxe;

import static java.lang.String.format;

import java.io.InputStream;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.yellowaxe.bitly.Bitter;
import com.yellowaxe.delicious.Delish;
import com.yellowaxe.googlereader.json.GoogleReaderJsonData;
import com.yellowaxe.googlereader.json.GoogleReaderJsonData.Item;
import com.yellowaxe.googlereader.json.GoogleReaderJsonFiles;
import com.yellowaxe.googlereader.json.GoogleReaderJsonParser;

/**
 * @author kal
 * 
 *         imports google reader json starred file as bitmarks into bitly. In
 *         the process, it checks against Delicious to get suggested tags and
 *         use them in the notes section in bitly
 */
@Component
public class GoogleReaderStarredImporter {

    private static final Logger LOG = Logger.getLogger(GoogleReaderStarredImporter.class);

    private GoogleReaderJsonParser googleReaderJsonParser;

    private Delish delish;

    private Bitter bitter;

    @Inject
    public GoogleReaderStarredImporter(GoogleReaderJsonParser googleReaderJsonParser, Delish delish, Bitter bitter) {
        this.googleReaderJsonParser = googleReaderJsonParser;
        this.delish = delish;
        this.bitter = bitter;
    }

    public void doImport(InputStream jsonStream) throws Exception {

        GoogleReaderJsonData data = googleReaderJsonParser.parse(jsonStream);

        for (Item item : data.getItems()) {
            String title = item.getTitle();

            String alternateUrl = null;
            if (item.getAlternate() != null && !item.getAlternate()
                    .isEmpty()) {
                alternateUrl = item.getAlternate()
                        .get(0)
                        .getHref();
            }
            String canonicalUrl = null;
            if (item.getCanonical() != null && !item.getCanonical()
                    .isEmpty()) {
                canonicalUrl = item.getCanonical()
                        .get(0)
                        .getHref();
            }

            String url = canonicalUrl != null ? canonicalUrl : alternateUrl;

            String author = item.getAuthor();

            Iterable<String> categories = Iterables.filter(item.getCategories(),
                    Predicates.not(Predicates.contains(Pattern.compile("state.com.google"))));

            String originTitle = item.getOrigin()
                    .getTitle();

            Set<String> tags = delish.getSuggestedTags(url);

            Function<String, String> lowercaseString = new Function<String, String>() {
                public String apply(String s) {
                    return s.toLowerCase();
                }
            };

            Set<String> combinedTags = ImmutableSet.<String> builder()
                    .addAll(Iterables.transform(categories, lowercaseString))
                    .addAll(Iterables.transform(tags, lowercaseString))
                    .build();

            LOG.info(format("%s (%s) by %s Via %s (%s)", title, url, author, originTitle, combinedTags));

            Thread.sleep(5 * 1000);

            bitter.saveBitmark(url, title, format("by %s Via %s (%s)", author, originTitle, combinedTags), new Date(),
                    true);
        }

    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/application-context.xml");
        GoogleReaderStarredImporter importer = context.getBean(GoogleReaderStarredImporter.class);

        try {
            InputStream jsonStream1 =
                    GoogleReaderStarredImporter.class.getClassLoader()
                            .getResourceAsStream(
                                    format("all4kh-googlereader-json-files/%s",
                                            GoogleReaderJsonFiles.STARRED.filename()));

            InputStream jsonStream2 =
                    GoogleReaderStarredImporter.class.getClassLoader()
                            .getResourceAsStream(
                                    format("kyle-zhegg-googlereader-json-files/%s",
                                            GoogleReaderJsonFiles.STARRED.filename()));

            importer.doImport(jsonStream1);
            importer.doImport(jsonStream2);
        } catch (Exception e) {
            LOG.error(Throwables.getStackTraceAsString(e));
        } finally {
            System.exit(0);
        }

    }
}
