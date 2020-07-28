package servlet.friends;

import DAO.FriendDAO;
import domain.Friend;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteFriendServlet")
public class DeleteFriendServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long uID = Long.parseLong(request.getParameter("uID"));
        FriendDAO friendDAO = new FriendDAO();
        friendDAO.deleteByUID(user.getUID(), uID);
        friendDAO.deleteByUID(uID, user.getUID());
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('删除成功！'); window.location.href='" + request.getContextPath() + "/friends.jsp'; </script>");
    }
}
