package Conversation;

import Message.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int id;
    private List<Integer> userIds;

    // Constructor
    public Conversation(int id) {
        this.id = id;
        this.userIds = new ArrayList<>();
    }

    // Getter و Setter للـ id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter و Setter لقائمة معرّفات المستخدمين
    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    // إضافة معرّف مستخدم جديد إلى المحادثة
    public void addUserId(int userId) {
        this.userIds.add(userId);
    }



}

