package Post;
import Comment.Comment;
import Content.Content;
import User.User;

import java.util.*;

public class Post extends Content {
    private String privacy;
    private List<Comment> comments;
    private List<String> taggedUser;//user name

}
