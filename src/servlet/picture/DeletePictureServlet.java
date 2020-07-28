package servlet.picture;

import DAO.FavorDAO;
import DAO.PictureDAO;
import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DeletePictureServlet")
public class DeletePictureServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("myPictures");
        session.removeAttribute("favoritePictures");
        session.removeAttribute("favoriteFriend");
        session.removeAttribute("searchResult");
        session.removeAttribute("searchResultPopularity");
        session.removeAttribute("searchResultTime");
        session.removeAttribute("searchPageNow");
        session.removeAttribute("picture");
        session.removeAttribute("comments");
        session.removeAttribute("isFavor");
        PictureDAO pictureDAO = new PictureDAO();
        Long imageID = Long.parseLong(request.getParameter("imageID"));
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0))
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.equals("TRAVEL_PICTURE_" + imageID)) {
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath());
                    response.addCookie(c);
                    break;
                }
            }
        Picture picture = pictureDAO.getByImageID(imageID);
        String deleteUrl = getServletContext().getRealPath("/img/") + "/large/" + picture.getPath();
        new File(deleteUrl).delete();
        pictureDAO.deleteByImageID(imageID);
        FavorDAO favorDAO = new FavorDAO();
        favorDAO.deleteByImageID(imageID);
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('删除成功！'); window.location.href='" + request.getContextPath() + "/myPictures.jsp'; </script>");
    }
}
