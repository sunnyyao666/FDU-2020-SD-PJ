package domain;

import java.sql.Timestamp;

public class Friend {
    private Long friendID;

    private Long uID1;
    private String username1;

    private Long uID2;
    private String username2;

    private int state;
    private Timestamp createdTime;

    public Friend() {
    }

    public Friend(Long uID1, Long uID2, int state) {
        this.uID1 = uID1;
        this.uID2 = uID2;
        this.state = state;
    }

    public Long getFriendID() {
        return friendID;
    }

    public void setFriendID(Long friendID) {
        this.friendID = friendID;
    }

    public Long getUID1() {
        return uID1;
    }

    public void setUID1(Long uID1) {
        this.uID1 = uID1;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public Long getUID2() {
        return uID2;
    }

    public void setUID2(Long uID2) {
        this.uID2 = uID2;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
