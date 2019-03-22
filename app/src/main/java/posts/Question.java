package posts;

import java.util.List;

public class Question extends Post {
    List<Integer> answers;
    Integer stars;

    public Question(List<Integer> answers, Integer stars) {
        this.answers = answers;
        this.stars = stars;
    }

    public Question(Integer authorID, Integer postID, String content, Boolean anonimity, List<Integer> answers, Integer stars) {
        super(authorID, postID, content, anonimity);
        this.answers = answers;
        this.stars = stars;
    }
}
