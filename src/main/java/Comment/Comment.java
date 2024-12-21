package Comment;
import Content.Content;
import User.User;
import DataStore.DataStore;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

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

    // method add by Wajdi to show comments for user

    public ArrayList<Comment> showComments(int id){
        List<Comment> listOfComments = DataStore.getInstance().getComments();

        // The ArrayList can be scooped ???
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for(Comment counter : listOfComments){
            if(counter.postId == id) {
                comments.add(counter);
            }
        }
        if(comments.isEmpty()){
            System.out.println("No comments yet");
            return comments;
        }
        printComments(comments);
        return comments;
    }
    // method add by Wajdi to show replies for user
    public void  printComments(ArrayList<Comment> comments) {
        ArrayList<Comment> replies = new ArrayList<Comment>();
        int id = 0;
        int commentId = 0;
        int i = 0;
        String[] showContent;

        for (Comment counter : comments) {
            id = counter.getId();

            for (Comment counter2 : comments) {
                if (counter2.getId() == id) {
                    continue;
                }
                else if (counter2.parentCommentId == id) {
                        replies.add(counter2);
                    }
            }
            // print comment

            System.out.println(id);
            showContent = counter.getContent().split("\\s+");
            System.out.println(findUser(id));
            System.out.println("=======================================================");
                viewContnet(showContent);
            System.out.println("=======================================================");
            // show reacts
            System.out.print("Likes: "+ counter.getLikeCount()+"\t\t\t\t Dislikes: "+counter.getDislikeCount());
            System.out.print("\n\n");

            // check if there's replies
            if(!replies.isEmpty()){
                for(Comment counter3 : replies){
                    commentId = counter3.getId();
                    System.out.println(commentId);
                    showContent = counter3.getContent().split("\\s+");
                    System.out.println(findUser(commentId));
                    System.out.println("@"+findUser(id));
                    System.out.println("=======================================================");
                    viewContnet(showContent);
                    System.out.println("=======================================================");
                    // show reacts
                    System.out.print("Likes: "+ counter3.getLikeCount()+"\t\t\t\t Dislikes: "+counter3.getDislikeCount());
                    System.out.print("\n\n");

                }
            }


        }
    }
    // method add by Wajdi to check if user choice is invalid
    public boolean checkId(int commId, ArrayList<Comment> comments){
        for(Comment counter : comments){
            if(commId == counter.getId()){
                return true;
            }
        }
        return false;
    }


}
