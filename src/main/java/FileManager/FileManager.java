package FileManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Conversation.Conversation;
import DataStore.DataStore;
import User.User;
import Post.Post;
import Comment.Comment;
import Message.Message;
import java.io.*;
import java.util.*;
import Friend.Friend;

@SuppressWarnings("ALL")
public class FileManager {

    private static final String USERS_FILE         = "C:/Users/moham/FBsystem/src/main/java/users.txt";
    private static final String POSTS_FILE         = "C:/Users/moham/FBsystem/src/main/java/posts.txt";
    private static final String COMMENTS_FILE      = "C:/Users/moham/FBsystem/src/main/java/comments.txt";
    private static final String MESSAGES_FILE      = "C:/Users/moham/FBsystem/src/main/java/messages.txt";
    private static final String CONVERSATIONS_FILE = "C:/Users/moham/FBsystem/src/main/java/Conversations.txt";
    private static final String LIKES_FILE         = "C:/Users/moham/FBsystem/src/main/java/likes.txt";
    private static final String DIS_LIKES_FILE     = "C:/Users/moham/FBsystem/src/main/java/dislikes.txt";
    private static final String TAGGED_POSTS       = "C:/Users/moham/FBsystem/src/main/java/tagedposts";


    public void readDataFromFile() {
        DataStore dataStore = DataStore.getInstance();

        readUsers();

        readPosts(dataStore);

        readcomments(dataStore);

        readConversations(dataStore);

        readmessages(dataStore);

        readLikes(LIKES_FILE,dataStore,true);

        readLikes(DIS_LIKES_FILE,dataStore,false);

        readTaggedPosts();

    }
    public void writeDataToFile() {

        writeUsers();

        writePosts();

        writeComments();

        writeConversations();

        writemessage();

        writeLikes(LIKES_FILE,true);

        writeLikes(DIS_LIKES_FILE,false);

        writeTaggedPosts();

    }

    public void readUsers() {
        DataStore dataStore = DataStore.getInstance();

        try {
            String content = Files.readString(Path.of(USERS_FILE));
            String[] blocks = content.split("\\r?\\n\\r?\\n");

            for (String block : blocks) {
                processUserBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processUserBlock(String block, DataStore dataStore) {
        String[] lines = block.split("\\r?\\n");

        String[] basicInfo = lines[0].split(",");

        int id = Integer.parseInt(basicInfo[0]);
        String username = basicInfo[1];
        String email = basicInfo[2];
        String password = basicInfo[3];
        String gender = basicInfo[4];
        Date birthdate = null;

        try {
            birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(basicInfo[5]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = new User(username, email, password, gender, birthdate);
        user.setId(id);

        // قراءة بيانات الأصدقاء من السطر الثاني
        if (lines.length > 1) {
            String friendData = lines[1].trim(); // بيانات الأصدقاء
            String[] friendEntries = friendData.split(";");

            for (String entry : friendEntries) {
                String[] parts = entry.split(",");
                try {
                    int friendId = Integer.parseInt(parts[0].trim());
                    boolean isRestricted = Boolean.parseBoolean(parts[1].trim());
                    user.addFriend(friendId, isRestricted); // تعديل لإضافة الصديق مع حالة "Restricted"
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("خطأ في تحليل بيانات الصديق: " + entry);
                }
            }
        }

        dataStore.addUser(user);
    }
    public void writeUsers() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (User user : dataStore.getUsers()) {
                // كتابة البيانات الأساسية للمستخدم
                sb.append(user.getId()).append(",")
                        .append(user.getUsername()).append(",")
                        .append(user.getEmail()).append(",")
                        .append(user.getPassword()).append(",")
                        .append(user.getGender()).append(",")
                        .append(dateFormat.format(user.getBirthdate())).append("\n");

                // كتابة بيانات الأصدقاء
                List<Friend> friends = user.getFriends();
                for (int i = 0; i < friends.size(); i++) {
                    Friend friend = friends.get(i);
                    sb.append(friend.getUserid()).append(",").append(friend.isRestricted() ? "true" : "false");
                    if (i < friends.size() - 1) {
                        sb.append(";");
                    }
                }

                sb.append("\n\n"); // إضافة فاصلات جديدة للفصل بين المستخدمين
            }

            // كتابة النص النهائي إلى الملف
            Files.writeString(Path.of("users.txt"), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readPosts(DataStore dataStore) {
        try {
            String content = Files.readString(Path.of(POSTS_FILE));
            String[] blocks = content.split("\\r?\\n\\r?\\n");
            for (String block : blocks) {
                processPostBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processPostBlock(String block, DataStore dataStore) {

        String[] lines = block.split("\\r?\\n");


        String[] basicInfo = lines[0].split(",");
        int postId = Integer.parseInt(basicInfo[0]);
        int userId = Integer.parseInt(basicInfo[1]);
        String privacy = basicInfo[2];

        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                break;
            }
            contentBuilder.append(line).append("\n");
        }

        String content = contentBuilder.toString().trim();


        Post post = new Post(content,privacy,userId);
        post.setId(postId);
        dataStore.addPost(post);
    }
    private void writePosts() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder sb = new StringBuilder();

            for (Post post : dataStore.getPosts()) {

                sb.append(post.getId()).append(",")
                        .append(post.getUserId()).append(",")
                        .append(post.getPrivacy()).append("\n");

                sb.append(post.getContent()).append("\n\n");
            }

            Files.writeString(Path.of(POSTS_FILE), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readcomments(DataStore dataStore) {
        try {
            String content = Files.readString(Path.of(COMMENTS_FILE));
            String[] blocks = content.split("\\r?\\n\\r?\\n");
            for (String block : blocks) {
                processCommentBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processCommentBlock(String block, DataStore dataStore) {

        String[] lines = block.split("\\r?\\n");


        String[] basicInfo = lines[0].split(",");
        int commentId = Integer.parseInt(basicInfo[0]);
        int userId = Integer.parseInt(basicInfo[1]);
        int postId = Integer.parseInt(basicInfo[2]);
        Integer parentCommentId = (basicInfo[3].isEmpty() || basicInfo[3].equals("NULL")) ? null : Integer.parseInt(basicInfo[3]);

        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                break;
            }
            contentBuilder.append(line).append("\n");
        }

        String content = contentBuilder.toString();

        Comment comment = new Comment( content, postId, userId, parentCommentId);
        comment.setId();
        dataStore.addComment(comment);
    }
    private void writeComments() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder sb = new StringBuilder();


            for (Comment comment : dataStore.getComments()) {

                sb.append(comment.getId()).append(",")
                        .append(comment.getUserId()).append(",")
                        .append(comment.getPostId()).append(",")
                        .append(comment.getParentCommentId() != null ? comment.getParentCommentId() : "NULL").append(",").append("\n")
                        .append(comment.getContent()).append("\n");
            }

            // كتابة البيانات إلى الملف
            Files.writeString(Path.of(COMMENTS_FILE), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readConversations(DataStore dataStore) {
        try {

            String content = Files.readString(Path.of(CONVERSATIONS_FILE));
            String[] blocks = content.split("\\r?\\n\\r?\\n");
            for (String block : blocks) {
                processConversationBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processConversationBlock(String block, DataStore dataStore) {
        String[] lines = block.split("\\r?\\n");

        String[] basicInfo = lines[0].split(",");
        int conversationId  = Integer.parseInt(basicInfo[0]);
        String convname = basicInfo[1];

        Conversation conversation = new Conversation(convname);
        conversation.setId(conversationId);

        // بناء المحتوى بعد السطر الأول
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                break;  // إنهاء القراءة عند السطر الفارغ
            }
            contentBuilder.append(line).append(",");
        }

        // إزالة الفاصلة الزائدة في نهاية السطر
        String content = contentBuilder.toString().trim();
        if (content.endsWith(",")) {
            content = content.substring(0, content.length() - 1);
        }

        // تقسيم ids بناءً على الفواصل
        List<Integer> userIds = new ArrayList<>();
        String[] userIdsArray = content.split(",");

        for (String userIdStr : userIdsArray) {
            if (!userIdStr.isEmpty()) {
                int userId = Integer.parseInt(userIdStr.trim());
                userIds.add(userId);
            }
        }

        // إضافة ids المستخدمين إلى المحادثة
        conversation.setUserIds(userIds);

        // إضافة المحادثة إلى DataStore
        dataStore.addConversation(conversation);
    }
    private void writeConversations() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder sb = new StringBuilder();

            for (Conversation conversation : dataStore.getConversations()) {
                sb.append(conversation.getId()).append(",")
                        .append(conversation.getConversation_name()).append("\n");

                List<Integer> userIds = conversation.getUserIds();
                StringBuilder userIdsLine = new StringBuilder();
                for (int i = 0; i < userIds.size(); i++) {
                    userIdsLine.append(userIds.get(i));
                    if (i < userIds.size() - 1) {
                        userIdsLine.append(",");
                    }
                }
                sb.append(userIdsLine.toString()).append("\n");

                sb.append("\n");
            }

            Files.writeString(Path.of(CONVERSATIONS_FILE), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readmessages(DataStore dataStore) {
        try {
            String content = Files.readString(Path.of(MESSAGES_FILE));
            String[] blocks = content.split("\\r?\\n\\r?\\n");
            for (String block : blocks) {
                processMessageBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processMessageBlock(String block, DataStore dataStore) {

        String[] lines = block.split("\\r?\\n");

        String[] basicInfo = lines[0].split(",");
        int messageId = Integer.parseInt(basicInfo[0]);
        int userId = Integer.parseInt(basicInfo[1]);
        int conversationId = Integer.parseInt(basicInfo[2]);

        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                break;
            }
            contentBuilder.append(line).append("\n");
        }

        String content = contentBuilder.toString();

        Message message = new Message(content,conversationId,userId);
        message.setId();
        dataStore.addMessage(message);
    }
    private void writemessage() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder sb = new StringBuilder();


            for (Message message : dataStore.getMessages()) {

                sb.append(message.getId()).append(",")
                        .append(message.getUserId()).append(",")
                        .append(message.getConversationId()).append(",").append("\n")
                        .append(message.getContent())
                        .append("\n");
            }

            Files.writeString(Path.of(MESSAGES_FILE), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readLikes(String filePath, DataStore dataStore, boolean isLike) {
        try {
            String content = Files.readString(Path.of(filePath));
            String[] blocks = content.split("\\r?\\n\\r?\\n");

            for (String block : blocks) {
                processLikeBlock(block.trim(), dataStore, isLike);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processLikeBlock(String block, DataStore dataStore, boolean isLike) {
        String[] lines = block.split("\\r?\\n");

        if (lines.length < 2) {
            System.out.println("Invalid block: " + block);
            return;
        }

        int itemId = Integer.parseInt(lines[0].trim());

        StringBuilder usernamesBuilder = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            usernamesBuilder.append(lines[i].trim());
            usernamesBuilder.append(",");
        }

        if (usernamesBuilder.length() > 0) {
            usernamesBuilder.setLength(usernamesBuilder.length() - 1);
        }

        String[] usernamesArray = usernamesBuilder.toString().split(",");

        List<String> usernames = new ArrayList<>();

        for (String username : usernamesArray) {
            username = username.trim();
            if (!username.isEmpty()) {
                usernames.add(username);
            }
        }

        Post post = dataStore.findPostById(itemId);
        if (post != null) {
            if (isLike) {
                post.setLikes(usernames);
            } else {
                post.setDislikes(usernames);
            }
            return; // إذا وجدنا البوست، نوقف البحث هنا
        }

        Comment comment = dataStore.findCommentById(itemId);
        if (comment != null) {
            if (isLike) {
                comment.setLikes(usernames);
            } else {
                comment.setDislikes(usernames);
            }
            return; // إذا وجدنا التعليق، نوقف البحث هنا
        }

        Message message = dataStore.findMessageById(itemId);
        if (message != null) {
            if (isLike) {
                message.setLikes(usernames);
            } else {
                message.setDislikes(usernames);
            }
            return; // إذا وجدنا الرسالة، نوقف البحث هنا
        }

        System.out.println("Content with ID " + itemId + " not found in any of the lists.");
    }
    private void writeLikes(String filePath, boolean isLike) {
        DataStore dataStore = DataStore.getInstance();

        try {

            StringBuilder content = new StringBuilder();


            if (isLike) {

                for (Post post : dataStore.getPosts()) {
                    if (!post.getLikes().isEmpty()) {
                        content.append(post.getId()).append("\n");
                        for (String username : post.getLikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }

                for (Comment comment : dataStore.getComments()) {
                    if (!comment.getLikes().isEmpty()) {
                        content.append(comment.getId()).append("\n");
                        for (String username : comment.getLikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }

                for (Message message : dataStore.getMessages()) {
                    if (!message.getLikes().isEmpty()) {
                        content.append(message.getId()).append("\n");
                        for (String username : message.getLikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }

            } else {

                for (Post post : dataStore.getPosts()) {
                    if (!post.getDislikes().isEmpty()) {
                        content.append(post.getId()).append("\n");
                        for (String username : post.getDislikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }

                for (Comment comment : dataStore.getComments()) {
                    if (!comment.getDislikes().isEmpty()) {
                        content.append(comment.getId()).append("\n");
                        for (String username : comment.getDislikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }

                for (Message message : dataStore.getMessages()) {
                    if (!message.getDislikes().isEmpty()) {
                        content.append(message.getId()).append("\n");
                        for (String username : message.getDislikes()) {
                            content.append(username).append(",");
                        }
                        content.deleteCharAt(content.length() - 1);
                        content.append("\n\n");
                    }
                }
            }


            Files.writeString(Path.of(filePath), content.toString());
            System.out.println("File written successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTaggedPosts() {
        DataStore dataStore = DataStore.getInstance();

        try {
            String content = Files.readString(Path.of(TAGGED_POSTS));
            String[] blocks = content.split("\\r?\\n\\r?\\n");

            for (String block : blocks) {
                processTaggedPostBlock(block.trim(), dataStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processTaggedPostBlock(String block, DataStore dataStore) {
        String[] lines = block.split("\\r?\\n");

        if (lines.length < 2) {
            System.out.println("Invalid block format: " + block);
            return;
        }

        // السطر الأول: معرف المنشور
        int postId = Integer.parseInt(lines[0].trim());

        // بقية الأسطر: أسماء المستخدمين المميزين
        for (int i = 1; i < lines.length; i++) {
            String username = lines[i].trim();

            // البحث عن المستخدم وإضافة المنشور إلى قائمة taggedPosts
            User user = dataStore.findUserByUsername(username);
            if (user != null) {
                user.addTaggedPost(postId);
            } else {
                System.out.println("User not found: " + username);
            }
        }
    }
    public void writeTaggedPosts() {
        DataStore dataStore = DataStore.getInstance();

        try {
            StringBuilder content = new StringBuilder();

            for (Post post : dataStore.getPosts()) {
                // جمع المستخدمين المميزين لكل منشور
                List<String> taggedUsers = post.getTaggedUsersForPost(dataStore);

                if (!taggedUsers.isEmpty()) {
                    content.append(post.getId()).append("\n");
                    for (String username : taggedUsers) {
                        content.append(username).append("\n");
                    }
                    content.append("\n"); // فصل بين ال blocks
                }
            }

            Files.writeString(Path.of(TAGGED_POSTS), content.toString());
            System.out.println("Tagged posts written successfully to " + TAGGED_POSTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
