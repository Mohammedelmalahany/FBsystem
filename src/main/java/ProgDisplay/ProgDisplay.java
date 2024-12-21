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

public class ProgDisplay {

    public void displayWelcomeScreen(DataStore dataStore) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Facebook System");
            System.out.println("Please choose an option:");
            System.out.println("1. Register an account");
            System.out.println("2. Log in to your account");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    User registereduser = registerAccount(dataStore);
                    if (registereduser != null) {
                        System.out.println("Welcome, " + registereduser.getUsername() + "! You are now registered.");
                        return;
                    }
                    break;
                case 2:
                    User loggedInUser = login(dataStore.getUsers());
                    if (loggedInUser != null) {
                        System.out.println("Welcome, " + loggedInUser.getUsername() + "! You are now logged in.");
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public User registerAccount(DataStore dataStore) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("You chose to register an account.");
        System.out.println("Please, Enter username:");
        String username = scanner.nextLine();



        System.out.println("Please, Enter email:");
        String email = scanner.nextLine();

        System.out.println("Please, Enter password:");
        String password = scanner.nextLine();

        System.out.println("Please, Enter gender:");
        String gender = scanner.nextLine();

        System.out.println("Please, Enter birthdate (format: dd/MM/yyyy):");
        Date birthdate = null;
        boolean validDate = false;

        while (!validDate) {
            String birthdateInput = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);

            try {
                birthdate = dateFormat.parse(birthdateInput);
                validDate = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please, enter the birthdate again (format: dd/MM/yyyy):");
            }
        }


        User user = new User(email, username, password, gender, birthdate);
        user.setId();
        dataStore.addUser(user);
        System.out.println("Account created successfully for user: " + username);
        return user;
    }

    public User login(List<User> users) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("You chose to log in.");
        System.out.println("Please, Enter your email:");
        String email = scanner.nextLine();

        System.out.println("Please, Enter your password:");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + user.getUsername() + "!");
                return user;
            }
        }

        System.out.println("Invalid email or password.");
        System.out.println("1. Try again");
        System.out.println("2. Return to the main menu");
        System.out.print("Your choice: ");

        int retryChoice = scanner.nextInt();
        scanner.nextLine();

        if (retryChoice == 1) {
            return login(users);
        } else {
            System.out.println("Returning to the main menu...");
            return null;
        }
    }

    private static void userActions(Scanner scanner) {
        while (true) {
            System.out.println("\n--- User Actions ---");
            System.out.println("1. Browse the main menu");
            //show posts according to the privacy level then
            // To make the user interact with posts after they are shown to him,
            // we can create a function that displays posts one by one,
            // and then asks the user to choose whether he wants to interact with the current post (write a comment, like, etc.).
            // We'll add a mechanism that allows them to choose between "Engage", "Skip to next post", or "Exit".
            //after engage read the last tow-lines of the comment of the conversation, it is the same logic

            System.out.println("2. Write a post");
            //add the post created to datastore and don't forget tagged users(take the usernames and check if there exist or not)

            System.out.println("3. Search for a user");
            //take the username, ask user if he wants to see posts(read the comment of Browse the main menu)
            // or add friend(contact with me for more explanation)

            System.out.println("4. See friend's posts");
            //ask user to choose one of his friend

            System.out.println("5. Browse conversations ");
            //show conversations he participated in
            // then asks the user to choose whether he wants to interact with the current conversation,
            // show him all messages in this conversation then ask if he wants to send message or like||dislike a message,
            // if he chooses to like ask about the id of the message that you already printed its id

            System.out.println("6. Shared Connections Analysis");
            //Explanation of the requirement:
            //8. Display friendship between users using the + operator:
            //Description:
            //When using the + operator between two users (User1 + User2), all common posts that both users can see are displayed based on their privacy settings.
            //How does this work?:
            //The lists of posts for each user are compared.
            //Shared posts are posts that were created by one user and allowed to be seen by the other user.
            //Shared posts are displayed.

            //9. Display mutual friends using the & operator:
            //Description:
            //When using the & operator between two users (User1 & User2), the list of mutual friends is displayed.
            //How does this work?:
            //The list of friends for each user is compared.
            //Mutual friends are users that appear in both lists.
            //The list of mutual friends is displayed.
            System.out.println("7. Log out");

            System.out.print("Enter your choice: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // لمعالجة السطر الجديد

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


}










