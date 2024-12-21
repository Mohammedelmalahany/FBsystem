package User;
import DataStore.DataStore;
import Friend.Friend;
import Post.Post;
import Conversation.Conversation;
import DataStore.DataStore;
import java.util.*;
import UniqueIdGenerator.UniqueIdGenerator;
import Message.Message;
public class User {
    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private String gender;
    private Date birthdate;
<<<<<<< HEAD
    private final List<Integer> friendIds;
=======
    private List<Friend> friends;
>>>>>>> upstream/master

    public User( String username, String email, String password, String gender, Date birthdate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.friends = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(3,1000000);
        this.id = uniqueIdGenerator.generateUniqueId();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getFriendUsernames() {
        DataStore dataStore = DataStore.getInstance();
        List<String> friendUsernames = new ArrayList<>();
        for (Friend friend : friends) {
            User user = dataStore.getUserById(friend.getUserid());
            if (user != null) {
                friendUsernames.add(user.getUsername());
            }
        }
        return friendUsernames;
    }

    public List<Friend> getFriends() {
        return friends;
    }
    public void addFriend(int friendId,boolean isRestricted ) {
        Friend friend = new Friend(friendId,isRestricted);
        friends.add(friend);

    }


    public List<Post> getCommonPosts(User otherUser) {
        DataStore dataStore = DataStore.getInstance();
        List<Post> commonPosts = new ArrayList<>();

        for (Post post : dataStore.getPosts()) {
            if ((post.getUserId() == this.id || post.getUserId() == otherUser.getId()) &&
                    (post.getPrivacy().equals("public") ||
                            (post.getPrivacy().equals("friends") &&
                                    (this.getFriendUserIds(false).contains(post.getUserId()) || otherUser.getFriendUserIds(false).contains(post.getUserId()))))) {
                commonPosts.add(post);
            }
        }

        return commonPosts;
    }

<<<<<<< HEAD
    // "added by Wajdi" method for getting the username of the post creator
    public String findUser(int userId){
        List<User> usernames = DataStore.getInstance().getUsers();
        for(User counter : usernames){
            if (userId == counter.id){
                return counter.username;
            }
        }
        // NOT handled case: if id doesn't exist
        return "";
    }
=======
    public List<User> getMutualFriends(User otherUser) {
        DataStore dataStore = DataStore.getInstance();
        List<User> mutualFriends = new ArrayList<>();

        for (Friend friend1 : this.friends) {
            if (friend1.isRestricted()) {
                for (Friend friend2 : otherUser.getFriends()) {
                    if (friend2.isRestricted() && friend1.getUserid() == friend2.getUserid()) {
                        mutualFriends.add(dataStore.getUserById(friend1.getUserid()));
                    }
                }
            }
        }

        return mutualFriends;
    }

    private List<Integer> getFriendUserIds(boolean includeRestricted) {
        List<Integer> friendUserIds = new ArrayList<>();
        for (Friend friend : friends) {
            if (includeRestricted || friend.isRestricted()) {
                friendUserIds.add(friend.getUserid());
            }
        }
        return friendUserIds;
    }
    public void displayUserConversations() {
        DataStore dataStore = DataStore.getInstance();
        List<Conversation> conversations = dataStore.getConversations();

        System.out.println("Your Conversations:");
        boolean hasConversations = false;
        for (Conversation conversation : conversations) {
            if (conversation.getUserIds().contains(this.id)) {
                System.out.println("ID: " + conversation.getId() + " | Name: " + conversation.getConversation_name());
                hasConversations = true;
            }
        }

        if (!hasConversations) {
            System.out.println("You have no conversations.");
        }
        System.out.println("Enter 0 to go back.");
    }
    public boolean sendNewMessage(int conversationId, String content) {
        if (content.equals("0")) {
            return false; // الرجوع
        }

        DataStore dataStore = DataStore.getInstance();

        // تحقق من وجود المحادثة
        Conversation conversation = dataStore.getConversationbyid(conversationId);
        if (conversation == null) {
            System.out.println("Conversation not found.");
            return false;
        }

        // إنشاء رسالة جديدة
        Message message = new Message(content, conversationId, this.id);
        message.setId(); // توليد ID فريد للرسالة

        // إضافة الرسالة إلى DataStore
        dataStore.addMessage(message);

        System.out.println("Message sent successfully!");
        return true;
    }
    public boolean interactWithMessage(int messageId, boolean isLike) {
        if (messageId == 0) {
            return false; // الرجوع
        }

        DataStore dataStore = DataStore.getInstance();
        Message message = dataStore.findMessageById(messageId);

        if (message == null) {
            System.out.println("Message not found. Please try again.");
            return false;
        }

        if (isLike) {
            message.addLike(this.username);
            System.out.println("You liked the message.");
        } else {
            message.addDislike(this.username);
            System.out.println("You disliked the message.");
        }
        return true;
    }



>>>>>>> upstream/master

}
