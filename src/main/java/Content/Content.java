package Content;
import User.User;
import java.util.*;
public class Content {
    //unique iD for each comment or post or react
    public static int iD = 0;
    protected String content;
    protected List<User> likes, dislikes;
    protected List<Comment> comments;

    public Content (){
        //unique iD for like or dislike
        iD++;
    }
    public Content (String content){
        //content of comment or post
        this.content = content;
        iD++;
    }
    public void addReactor(int react, User user){
        if (react == 1){
            likes.add(user);
        }
        if (react == 0){
            dislikes.add(user);
        }
    }
    public void removeReactor(User user){
        if (likes.contains(user)){
            likes.remove(user);
        }
        else if (dislikes.contains(user)){
            dislikes.remove(user);
        }
    }
    public void addComment(Comment comment){
        comments.add(comment);
    }

}