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

@WebServlet(name = "CommentServlet")
public class CommentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Picture picture = (Picture) session.getAttribute("picture");
        String content = request.getParameter("comment");
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.save(new Comment(picture.getImageID(), user.getUID(), content));
        session.setAttribute("comments", commentDAO.getListByImageID(picture.getImageID()));
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('发布成功！'); window.location.href='" + request.getContextPath() + "/detail.jsp?imageID=" + picture.getImageID() + "'; </script>");
    }
}
