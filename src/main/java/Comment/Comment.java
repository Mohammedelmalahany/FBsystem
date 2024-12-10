package Comment;
import Content.Content;
import User.User;

import java.util.List;

public class Comment extends Content {
    private int commentId;
    private List<Comment> replies;
    private User user;
}
