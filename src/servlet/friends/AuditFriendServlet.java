package servlet.friends;

import DAO.FriendDAO;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AuditFriendServlet")
public class AuditFriendServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long uID = Long.parseLong(request.getParameter("uID"));
        int state = Integer.parseInt(request.getParameter("state"));
        FriendDAO friendDAO = new FriendDAO();
        friendDAO.updateStateByUID(uID, user.getUID(), state);
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('操作成功！'); window.location.href='" + request.getContextPath() + "/friends.jsp'; </script>");
    }
}
