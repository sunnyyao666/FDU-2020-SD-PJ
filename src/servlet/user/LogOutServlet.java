package servlet.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogOutServlet")
public class LogOutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("myPictures");
        session.removeAttribute("favoritePictures");
        session.removeAttribute("favoriteFriend");
        session.removeAttribute("isFavor");
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0))
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.startsWith("TRAVEL_PICTURE_")) {
                    c.setMaxAge(0);
                    c.setPath(request.getContextPath());
                    response.addCookie(c);
                }
            }
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('退出成功！'); window.location.href='" + request.getContextPath() + "/index.jsp'; </script>");
    }
}
