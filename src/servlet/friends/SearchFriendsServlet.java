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

@WebServlet(name = "SearchFriendsServlet")
public class SearchFriendsServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String searchContent = request.getParameter("searchContent");
        UserDAO userDAO = new UserDAO();
        List<User> friends = userDAO.getListByUsernameContaining(searchContent);
        User user = (User) request.getSession().getAttribute("user");
        FriendDAO friendDAO = new FriendDAO();
        List<Friend> friendsNow = friendDAO.getListByUIDAndState(user.getUID(), 1);
        friendsNow.addAll(friendDAO.getListByUIDAndState(user.getUID(), 0));
        List<User> result = new LinkedList<User>();
        for (User friend : friends) {
            boolean isFriend = false;
            for (Friend f : friendsNow)
                if ((friend.getUID().equals(f.getUID1())) || (friend.getUID().equals(f.getUID2()))) {
                    isFriend = true;
                    break;
                }
            if (friend.getUID().equals(user.getUID())) isFriend = true;
            if (!isFriend) result.add(friend);
        }
        request.setAttribute("searchResult", result);
        request.getRequestDispatcher("/friends.jsp").forward(request, response);
    }
}
