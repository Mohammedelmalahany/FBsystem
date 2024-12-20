package DataStore;
import java.util.ArrayList;
import java.util.List;
import Conversation.Conversation;
import Message.Message;
import Comment.Comment;
import User.User;
import Post.Post;

public class DataStore {
    private static DataStore instance;

    private List<User> users;
    private List<Post> posts;
    private List<Comment> comments;
    private List<Message> messages;
    private List<Conversation> conversations;

    private DataStore() {
        users = new ArrayList<>();
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        messages = new ArrayList<>();
        conversations = new ArrayList<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }


    public List<User> getUsers() {
        return users;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void addUser(User user) {
        users.add(user);
    }
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    public void addPost(Post post) {
        posts.add(post);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public static List<Comment> getCommentsForPost(int postId) {
        DataStore dataStore = DataStore.getInstance();
        List<Comment> allComments = dataStore.getComments();
        List<Comment> postComments = new ArrayList<>();

        for (Comment comment : allComments) {
            if (comment.getPostId() == postId) {
                postComments.add(comment);
            }
        }

        return postComments;
    }

    public List<Post> getPostsByUserId(int userId) {
        DataStore dataStore = DataStore.getInstance();
        List<Post> userPosts = new ArrayList<>();

        for (Post post : dataStore.getPosts()) {
            if (post.getUserId() == userId) {
                userPosts.add(post);
            }
        }

        return userPosts;
    }


    public Post findPostById(int itemId) {
        for (Post post : posts) {
            if (post.getId() == itemId) {
                return post;
            }
        }
        return null;
    }

    public Comment findCommentById(int itemId) {
        for (Comment comment : comments) {
            if (comment.getId() == itemId) {
                return comment;
            }
        }
        return null;
    }

    public Message findMessageById(int itemId) {
        for (Message message : messages) {
            if (message.getId() == itemId) {
                return message;
            }
        }
        return null;

    }
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Conversation getConversationbyid(int id){
        for (Conversation conversation : this.getConversations()){
            if(conversation.getId()==id){
                return conversation;
            }
        }
        return null;
    }

}


