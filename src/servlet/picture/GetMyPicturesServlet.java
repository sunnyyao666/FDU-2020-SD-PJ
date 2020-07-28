package servlet.picture;

import DAO.PictureDAO;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetMyPicturesServlet")
public class GetMyPicturesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        PictureDAO pictureDAO = new PictureDAO();
        request.getSession().setAttribute("myPictures", pictureDAO.getListByUID(user.getUID()));
        request.getRequestDispatcher("/myPictures.jsp").forward(request, response);
    }
}
