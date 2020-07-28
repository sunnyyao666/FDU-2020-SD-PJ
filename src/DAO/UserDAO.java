package DAO;

import domain.User;

import java.util.List;

public class UserDAO extends DAO<User> {
    public void updateStateByUID(Long uID, int state) {
        String sql = "UPDATE traveluser SET STATE = ? WHERE UID = ?";
        update(sql, state, uID);
    }

    public List<User> getListByUsernameContaining(String username) {
        String sql = "SELECT * FROM traveluser WHERE Username LIKE ?";
        return getForList(sql, "%" + username + "%");
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM traveluser WHERE Username = ?";
        return get(sql, username);
    }

    public void save(User user) {
        String sql = "INSERT INTO traveluser (username, password, email) VALUES (? ,? ,?)";
        update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User getByUID(Long uID) {
        String sql = "SELECT * FROM traveluser WHERE UID = ?";
        return get(sql, uID);
    }

    public User getByEmail(String email) {
        String sql = "SELECT * FROM traveluser WHERE Email = ?";
        return get(sql, email);
    }

    public String getUsernameByUID(Long UID) {
        String sql = "SELECT Username FROM traveluser WHERE UID = ?";
        return getForValue(sql, UID);
    }
}
