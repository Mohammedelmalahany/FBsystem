package Message;
import Content.Content;
import User.User;
import java.util.Date;public class Message extends Content {

    private int conversationId;

    public Message(int id, String content, int conversationId, int userid) {
        super(id, content,userid);
        this.conversationId = conversationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }


}
