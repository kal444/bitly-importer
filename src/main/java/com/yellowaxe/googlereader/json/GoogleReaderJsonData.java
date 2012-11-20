package com.yellowaxe.googlereader.json;

import java.util.List;

import com.google.common.base.Objects;

public class GoogleReaderJsonData {

    public static class Content {

        private String content;

        private String direction;

        public String getContent() {
            return content;
        }

        public String getDirection() {
            return direction;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("content", content)
                    .add("direction", direction)
                    .toString();
        }
    }

    public static class Item {

        private List<Link> alternate;

        private List<String> annotations;

        private String author;

        private List<Link> canonical;

        private List<String> categories;

        private List<String> comments;

        private Content content;

        private String crawlTimeMsec;

        private String id;

        private String isReadStateLocked;

        private Origin origin;

        private String published;

        private List<Link> related;

        private List<Link> replies;

        private Content summary;

        private String timestampUsec;

        private String title;

        private String updated;

        public List<Link> getAlternate() {
            return alternate;
        }

        public List<String> getAnnotations() {
            return annotations;
        }

        public String getAuthor() {
            return author;
        }

        public List<Link> getCanonical() {
            return canonical;
        }

        public List<String> getCategories() {
            return categories;
        }

        public List<String> getComments() {
            return comments;
        }

        public Content getContent() {
            return content;
        }

        public String getCrawlTimeMsec() {
            return crawlTimeMsec;
        }

        public String getId() {
            return id;
        }

        public String getIsReadStateLocked() {
            return isReadStateLocked;
        }

        public Origin getOrigin() {
            return origin;
        }

        public String getPublished() {
            return published;
        }

        public List<Link> getRelated() {
            return related;
        }

        public List<Link> getReplies() {
            return replies;
        }

        public Content getSummary() {
            return summary;
        }

        public String getTimestampUsec() {
            return timestampUsec;
        }

        public String getTitle() {
            return title;
        }

        public String getUpdated() {
            return updated;
        }

        public void setAlternate(List<Link> alternate) {
            this.alternate = alternate;
        }

        public void setAnnotations(List<String> annotations) {
            this.annotations = annotations;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setCanonical(List<Link> canonical) {
            this.canonical = canonical;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public void setComments(List<String> comments) {
            this.comments = comments;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public void setCrawlTimeMsec(String crawlTimeMsec) {
            this.crawlTimeMsec = crawlTimeMsec;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIsReadStateLocked(String isReadStateLocked) {
            this.isReadStateLocked = isReadStateLocked;
        }

        public void setOrigin(Origin origin) {
            this.origin = origin;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public void setRelated(List<Link> related) {
            this.related = related;
        }

        public void setReplies(List<Link> replies) {
            this.replies = replies;
        }

        public void setSummary(Content summary) {
            this.summary = summary;
        }

        public void setTimestampUsec(String timestampUsec) {
            this.timestampUsec = timestampUsec;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("alternate", alternate)
                    .add("canonical", canonical)
                    .toString();
        }
    }

    public static class Link {

        private String href;

        private String title;

        private String type;

        public String getHref() {
            return href;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("href", href)
                    .add("title", title)
                    .add("type", type)
                    .toString();
        }
    }

    public static class Origin {
        private String htmlUrl;

        private String streamId;

        private String title;

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public String getStreamId() {
            return streamId;
        }

        public String getTitle() {
            return title;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("title", title)
                    .add("streamId", streamId)
                    .add("htmlUrl", htmlUrl)
                    .toString();
        }

    }

    private String author;

    private String direction;

    private String id;

    private List<Item> items;

    private List<Link> self;

    private String title;

    private String updated;

    public String getAuthor() {
        return author;
    }

    public String getDirection() {
        return direction;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Link> getSelf() {
        return self;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setSelf(List<Link> self) {
        this.self = self;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("author", author)
                .add("direction", direction)
                .add("id", id)
                .add("title", title)
                .add("updated", updated)
                .add("self", self)
                .add("items", items)
                .toString();
    }
}
