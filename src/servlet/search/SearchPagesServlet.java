package servlet.search;

import domain.Picture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchPagesServlet")
public class SearchPagesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int pageNow;
        if (request.getParameter("page") != null) pageNow = Integer.parseInt(request.getParameter("page"));
        else {
            pageNow = 1;
            String order = request.getParameter("order");
            if ("ReleasedTime".equals(order))
                session.setAttribute("searchResult", session.getAttribute("searchResultTime"));
            else session.setAttribute("searchResult", session.getAttribute("searchResultPopularity"));
        }
        session.setAttribute("searchPageNow", pageNow);
        List<Picture> pictures = (List<Picture>) request.getSession().getAttribute("searchResult");
        StringBuilder result = new StringBuilder();
        if (pictures == null)
            result.append("<p style=\"font-size:50px;color:red;margin-left: 100px;\">请开始搜索！</p>");
        else if (pictures.isEmpty())
            result.append("<p style=\"font-size:50px;color:red;margin-left: 100px;\">暂无结果！</p>");
        else {
            int pages = (int) Math.ceil(pictures.size() / 6.0);

            result.append("<table id=\"result\"><tr>");
            int start = (pageNow - 1) * 6;
            int end = start + 5;
            if (end >= pictures.size()) end = pictures.size() - 1;
            for (int i = start; i <= end; i++) {
                Picture picture = pictures.get(i);
                result.append("<td><img src = \"");
                result.append(request.getContextPath());
                result.append("/img/large/");
                result.append(picture.getPath());
                result.append("\" ><div class=\"name\" >");
                result.append(picture.getTitle());
                result.append("</div ><div class=\"author\" >");
                result.append(picture.getAuthorName());
                result.append("</div ><a href = \"");
                result.append(request.getContextPath());
                result.append("/picture/get?imageID=");
                result.append(picture.getImageID());
                result.append("\" ><button > 查看详情 </button ></a ><div class=\"heat\" ><span class=\"fa fa-star\" ></span > 热度：");
                result.append(picture.getPopularity());
                result.append("</div ></td >");
                if (i - start == 2) result.append("</tr>");
            }
            result.append("</table >");
            if (pictures.size() > 6) {
                result.append("<nav aria-label = \"Page navigation\" ><ul class=\"pagination\" ><li ><a onclick = \"page(1)\" aria-label = \"Previous\" ><span aria-hidden = \"true\" > &laquo;</span ></a ></li >");
                start = Math.max(pageNow - 4, 1);
                for (int i = start; i <= Math.min(start + 8, pages); i++)
                    if (i != pageNow) {
                        result.append("<li ><a onclick = \"page(");
                        result.append(i);
                        result.append(")\" >");
                        result.append(i);
                        result.append("</a ></li >");
                    } else {
                        result.append("<li class=\"active\" ><a onclick = \"page(");
                        result.append(i);
                        result.append(")\" >");
                        result.append(i);
                        result.append("</a ></li >");
                    }
                result.append("<li ><a onclick = \"page(");
                result.append(pages);
                result.append(")\" aria-label = \"Next\" ><span aria-hidden = \"true\" > &raquo;</span ></a ></li ></ul ><br ><span > 共");
                result.append(pages);
                result.append("页 </span ></nav >");
            }
        }
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().print(result.toString());
    }
}
