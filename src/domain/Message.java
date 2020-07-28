package domain;

import java.sql.Timestamp;

public class Message {
    private Long messageID;
    private Long fromUID;
    private Long toUID;
    private String content;
    private int type;
    private Timestamp createdTime;
    private int isSent;

    public Message() {
    }

    public Message(Long fromUID, Long toUID, String content, int type, int isSent) {
        this.fromUID = fromUID;
        this.toUID = toUID;
        this.content = content;
        this.type = type;
        this.isSent = isSent;
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Long getFromUID() {
        return fromUID;
    }

    public void setFromUID(Long fromUID) {
        this.fromUID = fromUID;
    }

    public Long getToUID() {
        return toUID;
    }

    public void setToUID(Long toUID) {
        this.toUID = toUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public int getIsSent() {
        return isSent;
    }

    public void setIsSent(int isSent) {
        this.isSent = isSent;
    }
}
