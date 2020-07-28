package servlet.messages;

import DAO.MessageDAO;
import DAO.UserDAO;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetMessagesHistoryServlet")
public class GetMessagesHistoryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long friendUID = Long.parseLong(request.getParameter("uID"));
        User user = (User) request.getSession().getAttribute("user");
        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO=new UserDAO();
        request.setAttribute("messagesHistory", messageDAO.getListByFromUIDANDToUID(user.getUID(), friendUID));
        request.setAttribute("chattingFriend",userDAO.getByUID(friendUID));
        request.getRequestDispatcher("/chat.jsp").forward(request, response);
    }
}
