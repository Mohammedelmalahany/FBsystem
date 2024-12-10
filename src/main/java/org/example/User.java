package org.example;

import java.util.*;

public class User {
    private String email;
    private String name;
    private String password;
    private Gender gender;
    private Date birthdate;
    private List<Post> posts;
    private List<clsFriend> friends;
    private List<Conversation> conversations;

    public User(String email, String name, String password, Gender gender, Date birthdate) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.posts = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.conversations = new ArrayList<>();
    }

    private enum Gender {
        MALE, FEMALE;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return new ArrayList<>(this.posts);
    }

    public List<User> getFriends() {
        return new ArrayList<>(this.friends);
    }

    public void addPost(String content, String privacy) {
        posts.add(new Post(content, privacy));
    }

    public List<Post> getCommonPosts(User otherUser) {
        List<Post> commonPosts = new ArrayList<>();
        for (Post post : otherUser.getPosts()) {
            if (post.getTaggedUsers().contains(this)) {
                commonPosts.add(post);
            }
        }
        for (Post post : this.getPosts()) {
            if (post.getTaggedUsers().contains(otherUser)) {
                commonPosts.add(post);
            }
        }
        return commonPosts;
    }

    public List<User> getMutualFriends(User otherUser) {
        List<User> mutualFriends = new ArrayList<>();
        for (User friend : this.friends) {
            if (otherUser.getFriends().contains(friend)) {
                mutualFriends.add(friend);
            }
        }
        return mutualFriends;
    }

    public boolean addFriend(User friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend cannot be null.");
        }
        if (this.friends.contains(friend)) {
            return false;
        }
        this.friends.add(friend);
        return true;
    }

    public void createConversation(List<User> participants) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Participants cannot be null or empty.");
        }
        Conversation newConversation = new Conversation(participants);
        this.conversations.add(newConversation);
    }

    public void showConversations() {
        for (Conversation conversation : conversations) {
            System.out.println(conversation);
        }
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
