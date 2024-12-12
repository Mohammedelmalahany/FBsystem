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
                "\n Message From:'" + sender + '\'' +
                "\n Message:'" + content + '\'' +
                "\n Date/time:" + getFormattedTimestamp() + "\nMessageId:" + messageId +' ' ;
    }
}

/*
*main*
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;
        do{

            System.out.print("Enter your name: ");
            String senderName = scanner.nextLine();
            System.out.print("Enter your message: ");
            String messageContent = scanner.nextLine();
            message message = new message(messageContent, senderName);
            System.out.println(message);
            System.out.println("press one to send a new message:");
            choice = scanner.nextInt();
            scanner.nextLine();
            while (true) {
                try {
                    System.out.println("Press 1 to send another message, or any other number to exit:");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.nextLine();
                }
            }

        }while(choice ==1);

        scanner.close(); // Close the scanner
    }
}*/


