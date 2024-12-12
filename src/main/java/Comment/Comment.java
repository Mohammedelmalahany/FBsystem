package Comment;
import Content.Content;
import User.User;
import java.util.*;
import java.util.List;

public class Comment extends Content {
    private int commentId;
    private List<Comment> replies;
    private User user;
    
        public Comment(String content, User user) {
        super(content);  
        this.user = user; 
        this.replies = new ArrayList<>();  
        this.commentId = iD++; 
    }
    
    public int getCommentId() {
        return commentId;
    }

    public User getUser() {
        return user;
    }

    public void addReply(Comment reply) {
        replies.add(reply);  
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void addReactor(int react, User user) {
        super.addReactor(react, user);  
    }
    
    public void removeReactor(User user) {
        super.removeReactor(user);  
    }

    public void addComment(Comment comment) {
        super.addComment(comment);  
    } return commentsId;
    
    }

