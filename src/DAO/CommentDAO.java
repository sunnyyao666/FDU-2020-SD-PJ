package DAO;

import domain.Comment;
import domain.Picture;

import java.util.List;

public class CommentDAO extends DAO<Comment> {
    public void updatePopularityANDLikedUIDsByCommentID(Long commentID, int i, String likedUIDs) {
        Comment comment = getByCommentID(commentID);
        String sql = "UPDATE comments SET Popularity = ?, LikedUIDS = ? WHERE CommentID = ?";
        update(sql, i + comment.getPopularity(), likedUIDs, commentID);
    }

    public Comment getByCommentID(Long commentID) {
        String sql = "SELECT * FROM comments WHERE CommentID = ?";
        return completeComment(get(sql, commentID));
    }

    public List<Comment> getListByImageID(Long imageID) {
        String sql = "SELECT * FROM comments WHERE ImageID = ? ORDER BY Popularity DESC, CreatedTime DESC";
        return completeComments(getForList(sql, imageID));
    }

    public void save(Comment comment) {
        String sql = "INSERT INTO comments (ImageID, UID, Content) VALUES (? ,? ,?)";
        update(sql, comment.getImageID(), comment.getUID(), comment.getContent());
    }

    private List<Comment> completeComments(List<Comment> comments) {
        for (Comment comment : comments) completeComment(comment);
        return comments;
    }

    private Comment completeComment(Comment comment) {
        UserDAO userDAO = new UserDAO();
        comment.setUsername(userDAO.getUsernameByUID(comment.getUID()));
        return comment;
    }
}
