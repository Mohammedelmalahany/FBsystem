package User;
import DataStore.DataStore;
import Friend.Friend;
import Post.Post;
import Conversation.Conversation;
import java.util.*;
import UniqueIdGenerator.UniqueIdGenerator;
import Message.Message;
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Date birthdate;
    private List<Friend> friends;
    private List<Integer> taggedPosts; // قائمة المنشورات المميزة
    private List<String> friendRequests; // قائمة طلبات الصداقة

    public User( String username, String email, String password, String gender, Date birthdate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.friends = new ArrayList<>();
        this.taggedPosts = new ArrayList<>(); // تهيئة القائمة
        this.friendRequests = new ArrayList<>(); // تهيئة قائمة طلبات الصداقة

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
    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void addFriendRequest(String username) {
        if (!friendRequests.contains(username)) {
            friendRequests.add(username);
        }
    }

    public void removeFriendRequest(String username) {
        friendRequests.remove(username);
    }

    public boolean acceptFriendRequest(String username, boolean restricted) {
        DataStore dataStore = DataStore.getInstance();
        User sender = dataStore.findUserByUsername(username);

        if (sender != null) {
            this.addFriend(sender.getId(), restricted);
            sender.addFriend(this.id, restricted);
            this.removeFriendRequest(username);
            return true;
        }
        return false;
    }

    public void declineFriendRequest(String username) {
        this.removeFriendRequest(username);
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

    public List<Integer> getFriendUserIds(boolean includeRestricted) {
        List<Integer> friendUserIds = new ArrayList<>();
        for (Friend friend : friends) {
            if (includeRestricted || friend.isRestricted()) {
                friendUserIds.add(friend.getUserid());
            }
        }
        return friendUserIds;
    }
    public void sendFriendRequest(Scanner scanner, User foundUser) {
        if (foundUser.getFriendRequests().contains(this.getUsername())) {
            System.out.println("Friend request already sent to " + foundUser.getUsername() + ".");
            return;
        }

        foundUser.addFriendRequest(this.getUsername());
        System.out.println("Friend request sent to " + foundUser.getUsername() + ".");
    }
    public void viewFriendRequests(Scanner scanner) {
        List<String> friendRequests = this.getFriendRequests();

        if (friendRequests.isEmpty()) {
            System.out.println("You have no pending friend requests.");
            return;
        }

        System.out.println("\nPending friend requests:");
        for (int i = 0; i < friendRequests.size(); i++) {
            System.out.println((i + 1) + ". " + friendRequests.get(i));
        }

        while (true) {
            System.out.print("Enter the number of the request to process (or 0 to go back): ");
            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            int choice = Integer.parseInt(input);

            if (choice == 0) {
                return; // الرجوع إلى القائمة السابقة
            }

            if (choice < 1 || choice > friendRequests.size()) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            String requesterUsername = friendRequests.get(choice - 1);
            processFriendRequest(scanner, requesterUsername);
            break;
        }
    }
    public void processFriendRequest(Scanner scanner, String requesterUsername) {
        while (true) {
            System.out.print("Would you like to accept this friend request? (1 for yes, 2 for no, 0 to go back): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "0" -> {
                    return; // الرجوع إلى قائمة الطلبات
                }
                case "1" -> {
                    System.out.print("Would you like to make this friend restricted? (1 for yes, 0 for no): ");
                    int restrictedInput = getValidBinaryInput(scanner);
                    boolean restricted = restrictedInput == 1;

                    boolean accepted = this.acceptFriendRequest(requesterUsername, restricted);

                    if (accepted) {
                        System.out.println("You are now friends with " + requesterUsername + ".");
                    } else {
                        System.out.println("Failed to accept the friend request.");
                    }
                    return;
                }
                case "2" -> {
                    this.declineFriendRequest(requesterUsername);
                    System.out.println("Friend request from " + requesterUsername + " declined.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
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
    public List<Integer> getTaggedPosts() {
        return taggedPosts;
    }

    public void addTaggedPost(int postId) {
        if (!taggedPosts.contains(postId)) {
            taggedPosts.add(postId);
        }
    }

    private static int getValidBinaryInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("0")) {
                return Integer.parseInt(input);
            } else {
                System.out.print("Invalid input. Please enter 1 for yes or 0 for no: ");
            }
        }
    }



}
