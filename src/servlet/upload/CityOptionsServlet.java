package servlet.upload;

import DAO.CityDAO;
import DAO.CountryDAO;
import domain.City;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CityOptionsServlet")
public class CityOptionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String country = request.getParameter("country");
        CountryDAO countryDAO = new CountryDAO();
        CityDAO cityDAO = new CityDAO();
        List<City> cities = cityDAO.getListByCountry_RegionCodeISO(countryDAO.getISOByCountry_RegionName(country));
        StringBuilder result = new StringBuilder();
        for (City city : cities) {
            result.append("<option value=\"");
            result.append(city.getAsciiName());
            result.append("\">");
            result.append(city.getAsciiName());
            result.append("</option>");
        }
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print(result.toString());
    }
}
