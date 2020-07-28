package servlet.user;

import DAO.UserDAO;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChangeStateServlet")
public class ChangeStateServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int state = Integer.parseInt(request.getParameter("state"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        UserDAO userDAO = new UserDAO();
        userDAO.updateStateByUID(user.getUID(), state);
        session.setAttribute("user", userDAO.getByUID(user.getUID()));
    }
}
