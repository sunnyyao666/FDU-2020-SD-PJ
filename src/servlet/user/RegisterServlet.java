package servlet.user;

import DAO.UserDAO;
import domain.User;
import util.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String url = request.getParameter("url");
        String PIN = (String) session.getAttribute("PIN");
        String PIN2 = request.getParameter("PIN");
        if (!PIN.equalsIgnoreCase(PIN2)) {
            request.setAttribute("registerMessage", "验证码错误！");
            request.getRequestDispatcher("/" + url).forward(request, response);
            return;
        }
        String username = request.getParameter("username2");
        String password = request.getParameter("password2");
        String email = request.getParameter("email");
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getByUsername(username);
        if (currentUser != null) {
            request.setAttribute("registerMessage", "用户名已被注册！");
            request.getRequestDispatcher("/" + url).forward(request, response);
            return;
        }
        currentUser = userDAO.getByEmail(email);
        if (currentUser != null) {
            request.setAttribute("registerMessage", "邮箱已被注册！");
            request.getRequestDispatcher("/" + url).forward(request, response);
            return;
        }
        session.removeAttribute("myPictures");
        session.removeAttribute("favoritePictures");
        session.removeAttribute("isFavor");
        session.removeAttribute("favoriteFriend");
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
        User user = new User(username, MD5Utils.getInstance().getMd5(password), email);
        userDAO.save(user);
        user = userDAO.getByUsername(username);
        request.getSession().setAttribute("user", user);
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print("<script> alert('注册成功！'); window.location.href='" + request.getContextPath() + "/" + url + "'; </script>");
    }
}
