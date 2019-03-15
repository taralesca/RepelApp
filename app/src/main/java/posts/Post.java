package posts;

import util.Entity;

public abstract class Post extends Entity {
    Integer authorID;
    Integer postID;
    String content;
    Boolean anonimity;

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
