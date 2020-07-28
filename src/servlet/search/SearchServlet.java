package servlet.search;

import DAO.PictureDAO;
import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet")
public class SearchServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String searchContent = request.getParameter("searchContent");
        String method = request.getParameter("method");
        String order = request.getParameter("order");
        PictureDAO pictureDAO = new PictureDAO();
        List<Picture> searchResultPopularity = pictureDAO.getListByMethodContainingOrderByOrder(searchContent, method, "Popularity");
        session.setAttribute("searchResultPopularity", searchResultPopularity);
        List<Picture> searchResultTime = pictureDAO.getListByMethodContainingOrderByOrder(searchContent, method, "ReleasedTime");
        session.setAttribute("searchResultTime", searchResultTime);
        if ("ReleasedTime".equals(order)) session.setAttribute("searchResult", searchResultTime);
        else session.setAttribute("searchResult", searchResultPopularity);
        session.setAttribute("searchPageNow", 1);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
