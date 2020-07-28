package servlet.picture;

import DAO.CommentDAO;
import DAO.FavorDAO;
import DAO.PictureDAO;
import domain.Picture;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "getPictureServlet")
public class GetPictureServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PictureDAO pictureDAO = new PictureDAO();
        Long imageID = Long.parseLong(request.getParameter("imageID"));
        Picture picture = pictureDAO.getByImageID(imageID);
        session.setAttribute("picture", picture);
        FavorDAO favorDAO = new FavorDAO();
        CommentDAO commentDAO = new CommentDAO();
        session.setAttribute("comments", commentDAO.getListByImageID(imageID));
        User user = (User) session.getAttribute("user");
        if (user != null) session.setAttribute("isFavor", favorDAO.getByUIDAndImageID(user.getUID(), imageID) != null);
        response.sendRedirect(request.getContextPath() + "/detail.jsp?imageID=" + imageID);
    }
}
