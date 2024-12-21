package Post;
import Comment.Comment;
import Content.Content;
import User.User;
import DataStore.DataStore;
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

    public List<String> getTaggedUsersForPost(DataStore dataStore) {
        List<String> taggedUsers = new ArrayList<>();

        for (User user : dataStore.getUsers()) {
            if (user.getTaggedPosts().contains(this.getId())) {
                taggedUsers.add(user.getUsername());
            }
        }

        return taggedUsers;
    }




}

