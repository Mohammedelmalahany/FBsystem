import java.text.SimpleDateFormat;
import java.util.Date;

public class message {
    private static int idCounter = 0;
    protected int messageId;
    protected String content;
    protected String sender;
    protected Date timestamp;

    public message(String content, String sender) {
        this.messageId = ++idCounter;
        this.content = content;
        this.sender = sender;
        this.timestamp = new Date();
    }

    public int getmessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    // Method to format and display the date and time
    public String getFormattedTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }
    
    public String toString() {
        return
                "\n Message From:'" + get.username() + '\'' +
                "\n Message:'" + content + '\'' +
                "\n Date/time:" + getFormattedTimestamp() + "\nMessageId:" + messageId +' ' ;
    }
}



