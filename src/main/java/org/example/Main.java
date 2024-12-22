package org.example;
import Comment.Comment;
import Conversation.Conversation;
import Message.Message;
import Post.Post;
import ProgDisplay.ProgDisplay;
import User.User;
import DataStore.DataStore;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import FileManager.FileManager;
public class Main {
    public static void main(String[] args) {

        DataStore dataStore = DataStore.getInstance();
        FileManager fileManager = new FileManager();

        System.out.println("Reading data from file...");
        fileManager.readDataFromFile();
        ProgDisplay progDisplay = new ProgDisplay();
        progDisplay.displayWelcomeScreen(dataStore);

        fileManager.writeDataToFile();






    }
}