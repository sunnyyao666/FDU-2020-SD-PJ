package domain;

import java.sql.Timestamp;

public class Comment {
    private Long commentID;
    private Long imageID;

    private Long uID;
    private String username;

    private String content;
    private Timestamp createdTime;

    private int popularity;
    private String likedUIDs;

    public Comment() {
    }

    public Comment(Long imageID, Long uID, String content) {
        this.imageID = imageID;
        this.uID = uID;
        this.content = content;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public Long getUID() {
        return uID;
    }

    public void setUID(Long uID) {
        this.uID = uID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getLikedUIDs() {
        return likedUIDs;
    }

    public void setLikedUIDs(String likedUIDs) {
        this.likedUIDs = likedUIDs;
    }
}
