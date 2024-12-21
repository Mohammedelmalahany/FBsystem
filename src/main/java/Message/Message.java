package Message;
import Content.Content;
import User.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends Content {
    private int conversationId;

    public Message(String content, int conversationId, int userid) {
        super( content,userid);
        this.conversationId = conversationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }



    // Method to format and display the date and time
}
