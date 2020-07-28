package servlet.friends;

import DAO.FriendDAO;
import DAO.UserDAO;
import domain.Friend;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "GetFriendsServlet")
public class GetFriendsServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        FriendDAO friendDAO = new FriendDAO();
        request.setAttribute("friends", getUsersByFriends(friendDAO.getListByUIDAndState(user.getUID(), 1), user.getUID()));
        request.setAttribute("friendsInviting", friendDAO.getListByUID1(user.getUID()));
        request.setAttribute("friendsInvited", friendDAO.getListByUID2(user.getUID()));
        request.getRequestDispatcher("/friends.jsp").forward(request, response);
    }

    private List<User> getUsersByFriends(List<Friend> friends, Long uID) {
        List<User> users = new LinkedList<User>();
        UserDAO userDAO = new UserDAO();
        for (Friend friend : friends)
            if (friend.getUID1().equals(uID)) users.add(userDAO.getByUID(friend.getUID2()));
            else users.add(userDAO.getByUID(friend.getUID1()));
        return users;
    }
}
