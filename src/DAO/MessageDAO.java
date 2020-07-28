package DAO;

import domain.Message;

import java.util.List;

public class MessageDAO extends DAO<Message> {
    public List<Message> getListByFromUIDANDToUID(Long fromUID, Long toUID) {
        String sql = "SELECT * FROM messages WHERE (FromUID = ? AND ToUID = ?) OR (FromUID = ? AND ToUID = ?) ORDER BY CreatedTime";
        return getForList(sql, fromUID, toUID, toUID, fromUID);
    }

    public void save(Message message) {
        String sql = "INSERT INTO messages (FromUID, ToUID, Content, Type) VALUES (? ,? ,?, ?)";
        update(sql, message.getFromUID(), message.getToUID(), message.getContent(), message.getType());
    }
}
