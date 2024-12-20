package Post;
import Comment.Comment;
import Content.Content;
import User.User;

import java.util.*;public class Post extends Content {

    private String privacy;
    List<String>usernames;


    public Post( String content, String privacy, int userId) {
        super( content,userId);
        this.privacy = privacy;
        usernames = new ArrayList<>();

    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }






}

