package Friend;
import java.lang.*;
import User.User;

public class Friend {
    boolean isRistricted;
    int userid;

    public Friend(int userid,boolean isRistricted){
        this.userid = userid;
        this.isRistricted = isRistricted;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isRistricted() {
        return isRistricted;
    }

    public void setRistricted(boolean ristricted) {
        isRistricted = ristricted;
    }
}
