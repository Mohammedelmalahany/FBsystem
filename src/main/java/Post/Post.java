package Post;
import Comment.Comment;
import Content.Content;
import User.User;

import java.util.*;public class Post extends Content {

    private String privacy;


    public Post(int id, String content, String privacy, int userId) {
        super(id, content,userId);
        this.privacy = privacy;

    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }






}

