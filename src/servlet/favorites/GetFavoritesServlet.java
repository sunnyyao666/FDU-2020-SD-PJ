package servlet.favorites;

import DAO.FavorDAO;
import DAO.PictureDAO;
import DAO.UserDAO;
import domain.Favor;
import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "getFavoritesServlet")
public class GetFavoritesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long uID = Long.parseLong(request.getParameter("uID"));
        List<Picture> pictures = new LinkedList<Picture>();
        FavorDAO favorDAO = new FavorDAO();
        List<Favor> favors = favorDAO.getListByUID(uID);
        PictureDAO pictureDAO = new PictureDAO();
        for (Favor favor : favors) pictures.add(pictureDAO.getByImageID(favor.getImageID()));
        session.setAttribute("favoritePictures", pictures);
        UserDAO userDAO=new UserDAO();
        session.setAttribute("favoriteFriend", userDAO.getByUID(uID));
        request.getRequestDispatcher("/favorites.jsp").forward(request, response);
    }
}
