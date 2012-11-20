package com.yellowaxe.delicious;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import del.icio.us.Delicious;
import del.icio.us.beans.Post;
import del.icio.us.beans.Suggestion;

/**
 * @author kal
 * 
 *         My delicious wrapper around delicious-java
 */
@Component
@SuppressWarnings("unchecked")
public class Delish {

    private Delicious delicious;

    @Inject
    public Delish(Delicious delicious) {
        super();
        this.delicious = delicious;
    }

    public List<Post> getAllPosts() {
        return delicious.getAllPosts();
    }

    public Set<String> getSuggestedTags(String url) {
        Suggestion suggestion = delicious.getSuggestion(url);
        Set<String> tags = suggestion.getSetOfPopularAndRecommendedTags();
        return tags;
    }

}
