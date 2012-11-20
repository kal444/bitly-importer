package com.yellowaxe.googlereader.json;

/**
 * @author kal
 * 
 *         defines the set of Google Reader JSON file you can download from
 *         Google Reader's import/export page
 */
public enum GoogleReaderJsonFiles {

    COMMENTED_ITEMS("commented-items.json"), //
    FOLLOWERS("followers.json"), //
    FOLLOWING("following.json"), //
    LIKED_ITEMS("liked-items.json"), //
    NOTES("notes.json"), //
    SHARED_BY_FRIENDS("shared-by-friends-items.json"), //
    SHARED_BY_ME("shared-items.json"), //
    STARRED("starred-items.json");

    private String filename;

    private GoogleReaderJsonFiles(String filename) {
        this.filename = filename;
    }

    public String filename() {
        return filename;
    }

}
