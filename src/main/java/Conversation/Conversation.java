package Conversation;

import Message.Message;
import UniqueIdGenerator.UniqueIdGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int id;
    private List<Integer> userIds;
    private String conversation_name;

    // Constructor
    public Conversation(String conversation_name) {

        this.userIds = new ArrayList<>();
        this.conversation_name = conversation_name;
    }

    // Getter و Setter للـ id
    public int getId() {
        return id;
    }

    public String getConversation_name(){
        return conversation_name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setId(){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(3,1000000);
        this.id = uniqueIdGenerator.generateUniqueId();
    }

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

