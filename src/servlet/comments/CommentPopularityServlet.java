package servlet.comments;

import DAO.CommentDAO;
import domain.Comment;
import domain.Picture;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CommentPopularityServlet")
public class CommentPopularityServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long commentID = Long.parseLong(request.getParameter("commentID"));
        int i = Integer.parseInt(request.getParameter("i"));
        User user = (User) session.getAttribute("user");
        CommentDAO commentDAO = new CommentDAO();
        Comment comment = commentDAO.getByCommentID(commentID);
        String likedUIDs = comment.getLikedUIDs();
        if (i == 1) likedUIDs += user.getUID() + ",";
        else {
            String first = likedUIDs.substring(0, likedUIDs.indexOf("," + user.getUID() + ","));
            String last = likedUIDs.substring(likedUIDs.indexOf("," + user.getUID() + ",") + 1 + (int) Math.log10(user.getUID()) + 1);
            likedUIDs = first + last;
        }
        commentDAO.updatePopularityANDLikedUIDsByCommentID(commentID, i, likedUIDs);
        session.setAttribute("comments", commentDAO.getListByImageID(((Picture) session.getAttribute("picture")).getImageID()));
    }
}
