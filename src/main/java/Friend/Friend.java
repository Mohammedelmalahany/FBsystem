package Friend;

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
