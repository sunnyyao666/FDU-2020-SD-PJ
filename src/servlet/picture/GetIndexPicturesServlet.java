package servlet.picture;

import DAO.PictureDAO;
import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetIndexPicturesServlet")
public class GetIndexPicturesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PictureDAO pictureDAO = new PictureDAO();
        request.setAttribute("mostPopularPictures", pictureDAO.getThreeByMostPopular());
        request.setAttribute("newestPictures", pictureDAO.getThreeByNewest());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
