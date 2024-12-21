package Friend;
<<<<<<< HEAD
import java.lang.*;
import java.util.List;

import User.User;
=======
>>>>>>> upstream/master

public class Friend {
    boolean isRestricted;
    int userid;

    public Friend(int userid,boolean isRestricted){
        this.userid = userid;
        this.isRestricted = isRestricted;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isRestricted() {
        return !isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }
}
// method added by Wajdi to check if the user is in frinedlist of post creator
/*public boolean checkFriend(int postUserId, int userId){
    List<User> friends = DataStore.getInstance().getUsers();
     getFriendIds();
}*/
