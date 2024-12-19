package Comment;
import Content.Content;
import User.User;
import java.util.List;

public class Comment extends Content {
    private int postId;
    private Integer parentCommentId;

    public Comment(int id, String content, int postId,int userid, Integer parentCommentId) {
        super(id, content,userid);
        this.postId = postId;
        this.parentCommentId = parentCommentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
