package ProgDisplay;
import java.util.*;

public class ProgDisplay {
    public void displayWelcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Facebook Management System");
        System.out.println("Please choose an option:");
        System.out.println("1. Register an account");
        System.out.println("2. Log in to your account");
        System.out.print("Your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("You chose to register an account.");
                // Call the registration logic here
                break;
            case 2:
                System.out.println("You chose to log in to your account.");
                // Call the login logic here
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
                displayWelcomeScreen(); // Recursion to re-display the menu
        }
    }

}
