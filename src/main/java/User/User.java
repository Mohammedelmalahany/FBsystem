package User;
import Friend.Friend;
import Post.Post;
import Conversation.Conversation;
import java.util.*;


public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Date birthdate;
    private List<Integer> friendIds; // قائمة معرفات الأصدقاء

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


}
