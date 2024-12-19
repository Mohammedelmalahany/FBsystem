package ProgDisplay;
import User.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import DataStore.DataStore;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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


        int id = 1;
        User user = new User(id,email, username, password, gender, birthdate);
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

}








