package User;
import Friend.Friend;
import Post.Post;
import Conversation.Conversation;
import DataStore.DataStore;
import java.util.*;


public class User {
    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private String gender;
    private Date birthdate;
    private final List<Integer> friendIds;

    public User(int id, String username, String email, String password, String gender, Date birthdate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.friendIds = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
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
    public void FriendIds() {
        for (Integer i : friendIds){
            System.out.println(i);
        }
    }

    public List<Integer> getFriendIds() {
        return friendIds;
    }

    public void addFriendId(int friendId) {
        if (!friendIds.contains(friendId)) {
            friendIds.add(friendId);
        }
    }

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

}
