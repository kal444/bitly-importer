package com.yellowaxe;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.yellowaxe.bitly.Bitter;
import com.yellowaxe.delicious.Delish;

import del.icio.us.beans.Post;

/**
 * @author kal
 * 
 *         migrates delicious bookmark to bitly bitmarks. it will also keep the
 *         tags from delicious in the notes field for bitmarks
 */
@Component
public class Migrator {

    private static final Logger LOG = Logger.getLogger(Migrator.class);

    private Delish delish;

    private Bitter bitter;

    @Inject
    public Migrator(Delish delish, Bitter bitter) {
        this.delish = delish;
        this.bitter = bitter;
    }

    public void migrate() throws InterruptedException {
        List<Post> allPosts = delish.getAllPosts();
        for (Post post : allPosts) {
            LOG.debug(post);
            bitter.saveBitmark(post.getHref(), post.getDescription(), post.getTag(), post.getTimeAsDate(), true);
            Thread.sleep(5 * 1000);
            break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/application-context.xml");
        Migrator migrator = context.getBean(Migrator.class);
        migrator.migrate();

        System.exit(0);
    }

}
