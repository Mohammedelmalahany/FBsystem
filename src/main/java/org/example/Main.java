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

        for (User user : dataStore.getUsers()) {
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Birthdate: " + new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthdate()));
            user.FriendIds();
            System.out.println("------------");
        }
        System.out.println("------------");

        for (Post post : dataStore.getPosts()){
            System.out.println(post.getUserId());
            System.out.println(post.getId());
            System.out.println(post.getPrivacy());
            System.out.println(post.getContent());
            System.out.println("------------");


        }
        System.out.println("------------");


        for (Comment comment : dataStore.getComments()){
            System.out.println(comment.getUserId());
            System.out.println(comment.getId());
            System.out.println(comment.getPostId());
            System.out.println(comment.getContent());
            System.out.println(comment.getParentCommentId());
            System.out.println("------------");


        }
        System.out.println("------------");

        for (Conversation conversation : dataStore.getConversations()){
            System.out.println(conversation.getId());
            System.out.println(conversation.getUserIds());
            System.out.println("------------");

        }
        System.out.println("------------");

        for (Message message : dataStore.getMessages()){
            System.out.println(message.getId());
            System.out.println(message.getUserId());
            System.out.println(message.getConversationId());
            System.out.println(message.getContent());
            System.out.println("------------");

        }
        System.out.println("------------");

        fileManager.writeDataToFile();






    }
}