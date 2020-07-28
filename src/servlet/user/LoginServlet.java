package servlet.user;

import DAO.UserDAO;
import domain.User;
import util.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username1");
        String password = request.getParameter("password1");
        String url = request.getParameter("url");
        HttpSession session = request.getSession();
        String PIN = (String) session.getAttribute("PIN");
        String PIN2 = request.getParameter("PIN");
        if (!PIN.equalsIgnoreCase(PIN2)) {
            request.setAttribute("loginMessage", "验证码错误！");
            request.getRequestDispatcher("/" + url).forward(request, response);
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsername(username);
        request.setAttribute("loginMessage", "用户名或密码错误！");
        if (user != null) {
            if (user.getPassword().equals(MD5Utils.getInstance().getMd5(password))) {
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
                session.setAttribute("user", user);
                response.setHeader("Content-Type", "text/html; charset=UTF-8");
                response.getWriter().print("<script> alert('登录成功！'); window.location.href='" + request.getContextPath() + "/" + url + "'; </script>");
            } else request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        user = userDAO.getByEmail(username);
        if (user != null) {
            if (user.getPassword().equals(MD5Utils.getInstance().getMd5(password))) {
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
                session.setAttribute("user", user);
                response.setHeader("Content-Type", "text/html; charset=UTF-8");
                response.getWriter().print("<script> alert('登录成功！'); window.location.href='" + request.getContextPath() + "/" + url + "'; </script>");
            } else request.getRequestDispatcher("/" + url).forward(request, response);
            return;
        }
        request.getRequestDispatcher("/" + url).forward(request, response);
    }
}
