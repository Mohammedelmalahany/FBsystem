package Content;
import User.User;
import java.util.List;
import java.util.ArrayList;

public class Content {
    private int id;
    private int userId;
    private String content;
    private List<String> likes;
    private List<String> dislikes;

    public Content(int id, String content,int userId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<String> dislikes) {
        this.dislikes = dislikes;
    }

    public void addLike(String username) {
        if (!likes.contains(username)) {
            likes.add(username);
            dislikes.remove(username);
        }
    }

    public void addDislike(String username) {
        if (!dislikes.contains(username)) {
            dislikes.add(username);
            likes.remove(userId);
        }
    }

    public int getLikeCount() {
        return likes.size();
    }

    public int getDislikeCount() {
        return dislikes.size();
    }
}

