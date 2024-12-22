package ProgDisplay;
import Conversation.Conversation;
import User.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import DataStore.DataStore;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import Post.Post;
import Friend.Friend;
public class ProgDisplay {

    public void displayWelcomeScreen(DataStore dataStore) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to Facebook System");
            System.out.println("Please choose an option:");
            System.out.println("1. Register an account");
            System.out.println("2. Log in to your account");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User registeredUser = registerAccount(dataStore);
                    if (registeredUser != null) {
                        System.out.println("Welcome, " + registeredUser.getUsername() + "! You are now registered.");
                        userActions(scanner,registeredUser);
                        return;
                    }
                    break;

                case 2: // تسجيل الدخول
                    User loggedInUser = login(dataStore.getUsers());
                    if (loggedInUser != null) {
                        System.out.println("Welcome, " + loggedInUser.getUsername() + "! You are now logged in.");
                        userActions(scanner,loggedInUser);
                        return;
                    }
                    break;

                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 3.");
            }
        }
    }

    public User registerAccount(DataStore dataStore) {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String email = "";
        String password = "";
        String gender = "";
        Date birthdate = null;

        int currentStep = 1;

        while (true) {
            switch (currentStep) {
                case 1: // إدخال اسم المستخدم
                    System.out.print("Enter a unique username (or 0 to go back): ");
                    username = scanner.nextLine();
                    if (username.equals("0")) {
                        System.out.println("No previous step. Exiting registration...");
                        return null;
                    }
                    if (dataStore.findUserByUsername(username) != null) {
                        System.out.println("Username is already taken. Please choose another one.");
                    } else if (!username.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Username cannot be empty.");
                    }
                    break;

                case 2: // إدخال البريد الإلكتروني
                    System.out.print("Enter email (or 0 to go back): ");
                    email = scanner.nextLine();
                    if (email.equals("0")) {
                        currentStep--;
                    } else if (!email.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Email cannot be empty.");
                    }
                    break;

                case 3: // إدخال كلمة المرور
                    System.out.print("Enter password (or 0 to go back): ");
                    password = scanner.nextLine();
                    if (password.equals("0")) {
                        currentStep--;
                    } else if (!password.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Password cannot be empty.");
                    }
                    break;

                case 4: // إدخال النوع
                    System.out.print("Enter gender (or 0 to go back): ");
                    gender = scanner.nextLine();
                    if (gender.equals("0")) {
                        currentStep--;
                    } else if (!gender.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Gender cannot be empty.");
                    }
                    break;

                case 5: // إدخال تاريخ الميلاد
                    System.out.print("Enter birthdate (format: dd/MM/yyyy) or 0 to go back: ");
                    String birthdateInput = scanner.nextLine();
                    if (birthdateInput.equals("0")) {
                        currentStep--;
                    } else {
                        try {
                            birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateInput);
                            currentStep++;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }
                    break;

                case 6: // إنشاء الحساب
                    User user = new User(username, email, password, gender, birthdate);
                    user.setId();
                    dataStore.addUser(user);
                    System.out.println("Account created successfully for user: " + username);
                    return user;
            }
        }
    }

    public User login(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        int currentStep = 1;

        while (true) {
            switch (currentStep) {
                case 1: // إدخال اسم المستخدم
                    System.out.print("Enter your username (or 0 to go back): ");
                    username = scanner.nextLine();
                    if (username.equals("0")) {
                        System.out.println("Returning to the main menu...");
                        return null;
                    }
                    if (!username.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Username cannot be empty.");
                    }
                    break;

                case 2: // إدخال كلمة المرور
                    System.out.print("Enter your password (or 0 to go back): ");
                    password = scanner.nextLine();
                    if (password.equals("0")) {
                        currentStep--;
                    } else if (!password.isEmpty()) {
                        currentStep++;
                    } else {
                        System.out.println("Password cannot be empty.");
                    }
                    break;

                case 3: // التحقق من بيانات الدخول
                    for (User user : users) {
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            System.out.println("Login successful! Welcome, " + user.getUsername() + "!");
                            return user;
                        }
                    }
                    System.out.println("Invalid username or password.");
                    System.out.println("1. Try again");
                    System.out.println("2. Return to the main menu");
                    System.out.print("Your choice: ");

                    int retryChoice = scanner.nextInt();
                    scanner.nextLine(); // تنظيف الإدخال

                    if (retryChoice == 1) {
                        currentStep = 1;
                    } else {
                        System.out.println("Returning to the main menu...");
                        return null;
                    }
                    break;
            }
        }
    }

    private static void userActions(Scanner scanner, User currentUser) {
        DataStore dataStore = DataStore.getInstance();
        ProgDisplay progDisplay = new ProgDisplay();

        while (true) {
            System.out.println("\n--- User Actions ---");
            System.out.println("1. Write a post");
            System.out.println("2. Create a conversation");
            System.out.println("3. Search for a user");
            System.out.println("4. See friends");
            System.out.println("5. Browse conversations");
            System.out.println("6. Shared connections analysis");
            System.out.println("7. See tagged posts");
            System.out.println("8. See friend requests");
            System.out.println("0. Log out");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    progDisplay.createNewPost(currentUser);
                    break;
                case "2":
                    progDisplay.createNewConversation(currentUser);
                    break;
                case "3":
                    progDisplay.searchForUser(scanner, currentUser, dataStore);
                    break;
                case "4":
                    currentUser.viewFriendsPosts(scanner);
                    break;
                case "5":
                    progDisplay.manageUserConversations(currentUser);
                    break;
                case "6":
                    sharedConnectionsAnalysis(scanner);
                    break;
                case "7":
                    displayTaggedPosts(currentUser, dataStore);
                    break;
                case "8":
                    currentUser.viewFriendRequests(scanner);
                    break;
                case "0":
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void logout() {
        clearConsole();
        System.out.println("You have successfully logged out.");
        System.out.println("Returning to the main menu...");
    }

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.out.println("Unable to clear console.");
        }
    }

    private static void sharedConnectionsAnalysis(Scanner scanner) {
        DataStore dataStore = DataStore.getInstance();

        System.out.println("Enter the username of the first user:");
        String username1 = scanner.nextLine();
        System.out.println("Enter the username of the second user:");
        String username2 = scanner.nextLine();

        User user1 = dataStore.findUserByUsername(username1);
        User user2 = dataStore.findUserByUsername(username2);

        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found.");
            return;
        }

        System.out.println("Press '+' to show common posts or '&' to show mutual friends:");
        char choice = scanner.nextLine().charAt(0);

        switch (choice) {
            case '+':
                List<Post> commonPosts = user1.getCommonPosts(user2);
                System.out.println("Common posts:");
                for (Post post : commonPosts) {
                    System.out.println(post.getContent());
                }
                break;
            case '&':
                List<User> mutualFriends = user1.getMutualFriends(user2);
                System.out.println("Mutual friends:");
                for (User friend : mutualFriends) {
                    System.out.println(friend.getUsername());
                }
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    public void manageUserConversations(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            DataStore dataStore = DataStore.getInstance();
            user.displayUserConversations();

            System.out.print("Enter the ID of the conversation you want to view (or 0 to exit): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid conversation ID.");
                scanner.nextLine(); // تنظيف الإدخال الخاطئ
                continue;
            }

            int conversationId = scanner.nextInt();
            scanner.nextLine();

            if (conversationId == 0) {
                System.out.println("Exiting conversations...");
                break;
            }

            // تحقق من وجود المحادثة
            Conversation conversation = dataStore.getConversationbyid(conversationId);
            if (conversation == null) {
                System.out.println("Conversation not found. Please enter a valid ID.");
                continue;
            }

            // 3. عرض الرسائل داخل المحادثة
            while (true) {
                conversation.displayConversationMessages();

                System.out.println("Choose an option:");
                System.out.println("1. Send a new message");
                System.out.println("2. Interact with a message");
                System.out.println("0. Go back");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    break; // الرجوع إلى قائمة المحادثات
                } else if (choice == 1) {
                    // إرسال رسالة جديدة
                    System.out.print("Enter the content of the new message (or 0 to go back): ");
                    String content = scanner.nextLine();
                    if (!user.sendNewMessage(conversation.getId(), content)) {
                        continue; // الرجوع إلى نفس المحادثة
                    }
                } else if (choice == 2) {
                    // التفاعل مع الرسائل
                    System.out.print("Enter the ID of the message you want to interact with (or 0 to go back): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid message ID.");
                        scanner.nextLine();
                        continue;
                    }

                    int messageId = scanner.nextInt();
                    scanner.nextLine();

                    if (messageId == 0) {
                        continue; // الرجوع إلى نفس المحادثة
                    }

                    System.out.println("Do you want to like or dislike the message?");
                    System.out.println("1. Like");
                    System.out.println("2. Dislike");
                    System.out.println("0. Go back");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid option.");
                        scanner.nextLine();
                        continue;
                    }

                    int interactionChoice = scanner.nextInt();

                    if (interactionChoice == 0) {
                        continue; // الرجوع إلى نفس المحادثة
                    }
                    if (interactionChoice != 1 && interactionChoice != 2) {
                        System.out.println("Invalid choice. Please enter 1 (Like) or 2 (Dislike) or 0 (Go back");
                        continue;
                    }

                    boolean isLike = interactionChoice == 1;
                    if (!user.interactWithMessage(messageId, isLike)) {
                        continue; // الرجوع إلى نفس المحادثة
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    public void createNewConversation(User currentUser) {
        Scanner scanner = new Scanner(System.in);
        DataStore dataStore = DataStore.getInstance();

        String conversationName = "";
        while (true) {
            System.out.print("Enter a name for the new conversation (or 0 to go back): ");
            conversationName = scanner.nextLine();
            if (conversationName.equals("0")) {
                System.out.println("Operation cancelled. Returning to the previous menu.");
                return;
            }
            if (!conversationName.isEmpty()) {
                break;
            } else {
                System.out.println("Conversation name cannot be empty. Please try again.");
            }
        }

        int participantCount = 0;
        while (true) {
            System.out.print("How many participants do you want to add (including yourself)? (Enter 0 to go back): ");
            if (scanner.hasNextInt()) {
                participantCount = scanner.nextInt();
                scanner.nextLine();
                if (participantCount == 0) {
                    System.out.println("Operation cancelled. Returning to the previous menu.");
                    return;
                } else if (participantCount >= 2) {
                    break;
                } else {
                    System.out.println("A conversation must have at least 2 participants. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }


        List<Integer> participantIds = new ArrayList<>();
        participantIds.add(currentUser.getId());
        System.out.println("You are added to the conversation automatically.");

        for (int i = 1; i < participantCount; i++) {
            while (true) {
                System.out.print("Enter the username of participant " + i + " (or 0 to go back): ");
                String username = scanner.nextLine();

                if (username.equals("0")) {
                    System.out.println("Operation cancelled. Returning to the previous menu.");
                    return;
                }


                User user = dataStore.findUserByUsername(username);
                if (user != null) {
                    if (participantIds.contains(user.getId())) {
                        System.out.println("This user is already added. Please enter a different username.");
                    } else {
                        participantIds.add(user.getId());
                        System.out.println("User " + username + " added to the conversation.");
                        break;
                    }
                } else {
                    System.out.println("User not found. Please enter a valid username.");
                }
            }
        }


        Conversation newConversation = new Conversation(conversationName);
        newConversation.setId();
        newConversation.setUserIds(participantIds);
        dataStore.addConversation(newConversation);

        System.out.println("Conversation '" + conversationName + "' created successfully with ID: " + newConversation.getId());
    }

    public void createNewPost(User currentUser) {
        Scanner scanner = new Scanner(System.in);
        DataStore dataStore = DataStore.getInstance();

        StringBuilder content = new StringBuilder(); // محتوى المنشور
        String privacy = ""; // خصوصية المنشور
        List<String> taggedUsers = new ArrayList<>(); // قائمة المستخدمين المميزين
        int currentStep = 1; // الخطوة الحالية

        while (true) {
            switch (currentStep) {
                case 1: // إدخال محتوى المنشور
                    System.out.print("Enter the content of your post (or enter 0 to go back): ");
                    content = new StringBuilder(scanner.nextLine());
                    if (content.toString().equals("0")) {
                        System.out.println("No previous step. Exiting...");
                        return; // لا يوجد مرحلة سابقة
                    } else if (!content.isEmpty()) {
                        currentStep++; // الانتقال إلى المرحلة التالية
                    } else {
                        System.out.println("Content cannot be empty. Please try again.");
                    }
                    break;

                case 2: // اختيار الخصوصية
                    System.out.println("Select the privacy for your post:");
                    System.out.println("1. Public");
                    System.out.println("2. Private");
                    System.out.println("0. Go back");
                    System.out.print("Enter your choice: ");
                    if (scanner.hasNextInt()) {
                        int privacyChoice = scanner.nextInt();
                        scanner.nextLine(); // تنظيف الإدخال

                        if (privacyChoice == 0) {
                            currentStep--; // العودة إلى المرحلة السابقة
                        } else if (privacyChoice == 1) {
                            privacy = "public";
                            currentStep++; // الانتقال إلى المرحلة التالية
                        } else if (privacyChoice == 2) {
                            privacy = "private";
                            currentStep++; // الانتقال إلى المرحلة التالية
                        } else {
                            System.out.println("Invalid choice. Please enter 1, 2, or 0.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine(); // تنظيف الإدخال الخاطئ
                    }
                    break;

                case 3: // إضافة Tagged Users
                    System.out.print("Do you want to tag users in this post? (yes/no or 0 to go back): ");
                    String choice = scanner.nextLine().toLowerCase();

                    switch (choice) {
                        case "0" -> currentStep--; // العودة إلى المرحلة السابقة
                        case "yes" -> {
                            while (true) {
                                System.out.print("How many users do you want to tag? (Enter 0 to skip): ");
                                if (scanner.hasNextInt()) {
                                    int tagCount = scanner.nextInt();
                                    scanner.nextLine(); // تنظيف الإدخال

                                    if (tagCount == 0) {
                                        break; // تخطي إضافة Tagged Users
                                    } else if (tagCount > 0) {
                                        for (int i = 0; i < tagCount; i++) {
                                            System.out.print("Enter the username of user " + (i + 1) + " (or enter 0 to go back): ");
                                            String username = scanner.nextLine();

                                            if (username.equals("0")) {
                                                currentStep--; // العودة إلى المرحلة السابقة
                                                break;
                                            }

                                            User taggedUser = dataStore.findUserByUsername(username);

                                            if (taggedUser != null) {
                                                taggedUsers.add(username); // إضافة اسم المستخدم إلى قائمة Tagged Users
                                                System.out.println("User @" + username + " has been tagged.");
                                            } else {
                                                System.out.println("User " + username + " not found. Please try again.");
                                                i--; // السماح بإعادة المحاولة
                                            }
                                        }
                                        break; // الانتهاء من إضافة Tagged Users
                                    } else {
                                        System.out.println("Invalid number. Please enter a valid number of users.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a number.");
                                    scanner.nextLine(); // تنظيف الإدخال الخاطئ
                                }
                            }
                            currentStep++; // الانتقال إلى المرحلة التالية
                        }
                        case "no" -> currentStep++; // الانتقال إلى المرحلة التالية
                        default -> System.out.println("Invalid choice. Please enter 'yes', 'no', or 0.");
                    }
                    break;

                case 4: // إنشاء المنشور وإضافته إلى DataStore
                    // إضافة أسماء المستخدمين المميزين إلى محتوى المنشور
                    for (String taggedUser : taggedUsers) {
                        content.append(" @").append(taggedUser);
                    }

                    // إنشاء البوست
                    Post newPost = new Post(content.toString(), privacy, currentUser.getId());
                    newPost.setId(); // توليد ID فريد للبوست
                    dataStore.addPost(newPost); // إضافة البوست إلى DataStore

                    // إضافة معرف المنشور لكل مستخدم في قائمة Tagged Users
                    for (String username : taggedUsers) {
                        User taggedUser = dataStore.findUserByUsername(username);
                        if (taggedUser != null) {
                            taggedUser.addTaggedPost(newPost.getId());
                        }
                    }

                    System.out.println("Post created successfully with ID: " + newPost.getId());
                    System.out.println("Content: " + content);
                    System.out.println("Privacy: " + privacy);

                    return; // إنهاء العملية
            }
        }
    }

    private void searchForUser(Scanner scanner, User currentUser, DataStore dataStore) {
        while (true) {
            System.out.print("Enter the username of the user you want to search for (or 0 to go back): ");
            String searchUsername = scanner.nextLine().trim();

            if (searchUsername.equals("0")) {
                System.out.println("Returning to the previous menu...");
                return;
            }

            User foundUser = dataStore.findUserByUsername(searchUsername);

            if (foundUser == null) {
                System.out.println("User not found. Please try again.");
                continue;
            }

            System.out.println("User found: " + foundUser.getUsername());

            List<Post> posts = dataStore.getPostsByUserId(foundUser.getId());
            boolean isFriend = currentUser.getFriendUserIds(false).contains(foundUser.getId());
            boolean isRestricted = currentUser.getFriendUserIds(true).contains(foundUser.getId());

            System.out.println("\nPosts by " + foundUser.getUsername() + ":");
            boolean hasPosts = false;
            for (Post post : posts) {
                if (post.getPrivacy().equals("public") ||
                        (post.getPrivacy().equals("friends") && isFriend && !isRestricted)) {
                    System.out.println("Post ID: " + post.getId());
                    System.out.println("Content: " + post.getContent());
                    System.out.println("---------------");
                    hasPosts = true;
                }
            }

            if (!hasPosts) {
                System.out.println("No visible posts available.");
            }

            while (true) {
                System.out.println("1. Send friend request");
                System.out.println("2. Search for another user");
                System.out.println("0. Go back");
                System.out.print("Your choice: ");

                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1" -> {
                     currentUser.sendFriendRequest(scanner, foundUser);
                    }
                    case "2" -> {
                        break;
                    }
                    case "0" -> {
                        System.out.println("Returning to the previous menu...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static int getValidBinaryInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("0")) {
                return Integer.parseInt(input);
            } else {
                System.out.print("Invalid input. Please enter 1 for yes or 0 for no: ");
            }
        }
    }

    private static void displayTaggedPosts(User currentUser, DataStore dataStore) {
        List<Integer> taggedPostIds = currentUser.getTaggedPosts();
        if (taggedPostIds.isEmpty()) {
            System.out.println("No tagged posts found.");
            return;
        }
        System.out.println("\nTagged Posts:");
        for (int postId : taggedPostIds) {
            Post post = dataStore.findPostById(postId);
            if (post != null) {
                System.out.println("Post ID: " + post.getId());
                System.out.println("Content: " + post.getContent());
                System.out.println("---------------");
            }
        }
    }






}










