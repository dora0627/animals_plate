package com.example.animals_plate;

public class ChatMessage {
    private String messageContent;
    private String sender;
    private long timestamp;
    private MessageType messageType;

    public ChatMessage(String content,String sender,MessageType type){
        this.messageContent = content;
        this.sender = UserData.username;
        this.timestamp = System.currentTimeMillis();
        this.messageType = type;
    }
    public String getMessageContent(){
        return messageContent;
    }
    public String getSender(){
        return sender;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public MessageType getMessageType(){
        return messageType;
    }
}

