package DAO;

import domain.Friend;

import java.util.List;

public class FriendDAO extends DAO<Friend> {
    public void deleteByUID(Long uID1, Long uID2) {
        String sql = "DELETE FROM friends WHERE UID1 = ? AND UID2 = ? AND State = ?";
        update(sql, uID1, uID2, 1);
    }

    public void save(Friend friend) {
        String sql = "INSERT INTO friends (UID1, UID2, State) VALUES (?, ?, ?)";
        update(sql, friend.getUID1(), friend.getUID2(), friend.getState());
    }

    public void updateStateByUID(Long uID1, Long uID2, int state) {
        String sql = "UPDATE friends SET State = ? WHERE UID1 = ? AND UID2 = ? AND State = ?";
        update(sql, state, uID1, uID2, 0);
    }

    public List<Friend> getListByUID1(Long uID1) {
        String sql = "SELECT * FROM friends WHERE UID1 = ? ";
        return completeFriends(getForList(sql, uID1));
    }

    public List<Friend> getListByUID2(Long uID2) {
        String sql = "SELECT * FROM friends WHERE UID2 = ?";
        return completeFriends(getForList(sql, uID2));
    }

    public List<Friend> getListByUIDAndState(Long uID, int state) {
        String sql = "SELECT * FROM friends WHERE State = ? AND (UID1 = ? OR UID2 = ?)";
        return completeFriends(getForList(sql, state, uID, uID));
    }

    private List<Friend> completeFriends(List<Friend> friends) {
        for (Friend friend : friends) completeFriend(friend);
        return friends;
    }

    private Friend completeFriend(Friend friend) {
        UserDAO userDAO = new UserDAO();
        friend.setUsername1(userDAO.getUsernameByUID(friend.getUID1()));
        friend.setUsername2(userDAO.getUsernameByUID(friend.getUID2()));
        return friend;
    }
}
