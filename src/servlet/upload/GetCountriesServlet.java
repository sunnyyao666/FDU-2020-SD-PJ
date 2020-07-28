package servlet.upload;

import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.PictureDAO;
import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GetCountriesServlet")
public class GetCountriesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long imageID = Long.parseLong(request.getParameter("imageID"));
        CountryDAO countryDAO = new CountryDAO();
        request.setAttribute("countries", countryDAO.getAll());
        CityDAO cityDAO = new CityDAO();
        if (imageID == -1)
            request.setAttribute("cities", cityDAO.getListByCountry_RegionCodeISO("CN"));
        else {
            PictureDAO pictureDAO = new PictureDAO();
            Picture picture = pictureDAO.getByImageID(imageID);
            request.setAttribute("uploadingPicture", picture);
            request.setAttribute("cities", cityDAO.getListByCountry_RegionCodeISO(picture.getCountry_RegionCodeISO()));
        }
        request.getRequestDispatcher("/upload.jsp").forward(request, response);
    }
}
