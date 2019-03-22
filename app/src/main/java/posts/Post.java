package posts;

import java.util.Objects;

import util.Entity;

public abstract class Post extends Entity {
    Integer authorID;
    Integer postID;
    String content;
    Boolean anonimity;

    public Post() { }

    public Post(Integer authorID, Integer postID, String content, Boolean anonimity) {
        this.authorID = Objects.requireNonNull(authorID);
        this.postID = Objects.requireNonNull(postID);
        this.content = Objects.requireNonNull(content);
        this.anonimity = Objects.requireNonNull(anonimity);
    }

    public Boolean create() {
        /// TODO
        return false;
    }
    public Boolean read() {
        /// TODO
        return false;
    }
    public Boolean update() {
        /// TODO
        return false;
    }
    public Boolean delete() {
        /// TODO
        return false;
    }

}
