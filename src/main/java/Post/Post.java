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

    // added by Wajdi, method for showing posts to the user
    public void showPosts(int currentVal,ArrayList<Post> posts){
            String postUsername = findUser(posts.get(currentVal).getUserId());
            String [] showContent = posts.get(currentVal).getContent().split("\\s+");
            System.out.println(postUsername);
            // show content
            viewContnet(showContent);
            // show reacts number
            int postLikes = posts.get(currentVal).getLikeCount();
            int postDislikes = posts.get(currentVal).getDislikeCount();
            System.out.print("Likes: "+postLikes+"\t\t\t\tDislikes: "+postDislikes);
    }


}

