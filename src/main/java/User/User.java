package User;
import Friend.Friend;
import Post.Post;
import Conversation.Conversation;
import java.util.*;
import UniqueIdGenerator.UniqueIdGenerator;
import DataStore.DataStore;
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Date birthdate;
    private List<Friend> friends;

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


}
