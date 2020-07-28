package servlet.favorites;

import DAO.FavorDAO;
import DAO.PictureDAO;
import domain.Favor;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddServlet")
public class AddServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("myPictures");
        session.removeAttribute("picture");
        session.removeAttribute("comments");
        session.removeAttribute("isFavor");
        session.removeAttribute("favoritePictures");
        session.removeAttribute("favoriteFriend");
        session.removeAttribute("searchResult");
        session.removeAttribute("searchResultPopularity");
        session.removeAttribute("searchResultTime");
        session.removeAttribute("searchPageNow");
        long imageID = Long.parseLong(request.getParameter("imageID"));
        User user = (User) session.getAttribute("user");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        if (user == null) {
            response.getWriter().print("<script> alert('请先登录！'); window.location.href='" + request.getContextPath() + "/detail.jsp'; </script>");
            return;
        }
        FavorDAO favorDAO = new FavorDAO();
        favorDAO.save(new Favor(user.getUID(), imageID));
        PictureDAO pictureDAO = new PictureDAO();
        pictureDAO.updatePopularityByImageID(imageID, 1);
        session.setAttribute("picture",pictureDAO.getByImageID(imageID));
        response.getWriter().print("<script> alert('已收藏！'); window.location.href='" + request.getContextPath() + "/detail.jsp'; </script>");
    }
}
