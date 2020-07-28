<%@ page import="domain.Picture" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-favorites</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/favorites.css">
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        out.print("<script>alert('请先登录！');</script>");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    Long uID = user.getUID();
    if (request.getParameter("uID") != null) uID = Long.parseLong(request.getParameter("uID"));

    if (session.getAttribute("favoritePictures") == null || !uID.equals(((User) (session.getAttribute("favoriteFriend"))).getUID())) {
        request.getRequestDispatcher("/favorites/get?uID=" + uID).forward(request, response);
        return;
    }
    User friend = (User) (session.getAttribute("favoriteFriend"));
    List<Picture> pictures = (List<Picture>) session.getAttribute("favoritePictures");
%>
<header>
    <div id="title">Travel</div>
    <div id="slogan">身未动,心已远。</div>
    <div id="navigator">
        <span><a href="${pageContext.request.contextPath}/index.jsp"> 首页 </a></span>
        &nbsp;&nbsp;
        <span><a href="${pageContext.request.contextPath}/search.jsp"> 搜索</a></span>
    </div>
    <nav>
        <span>
                <%
            String nav;
            nav = " <span class=\"fa fa-user-o\"><div id=\"drop\"><span id=\"username\"> &nbsp;" + user.getUsername() + "</span>" +
                    "<table id=\"dropDownList\"><tr><td><a href=\"";
            if (uID.equals(user.getUID()))nav+="javascript:;";
            else nav+=request.getContextPath()+"/favorites.jsp";
                    nav+="\"";
                    if (uID.equals(user.getUID())) nav+=" class=\"now\"";
                    nav+=">我的收藏</a><td><tr>" +
                     "<tr><td><a href=\""+ request.getContextPath()+"/upload.jsp?imageID=-1\">上传图片</a><td><tr>" +
                      "<tr><td><a href=\""+ request.getContextPath()+"/myPictures.jsp\">我的图片</a><td><tr>" +
                       "<tr><td><a href=\""+ request.getContextPath()+"/friends.jsp\">我的好友</a><td><tr>" +
                        "<tr><td><a href=\""+ request.getContextPath()+"/logOut\">退出登录</a><td><tr></table></div></span>";

            out.print(nav);
        %>
    </nav>
</header>

<div id="head"><%
    if (uID.equals(user.getUID())) out.print("我");
    else out.print(friend.getUsername());
%>的收藏
</div>
<%
    if (uID.equals(user.getUID())) {
        out.print("<div><div id=\"subtitle\">我的足迹</div>");
        out.print("<div id=\"cookie\">");
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0))
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.startsWith("TRAVEL_PICTURE_"))
                    out.print("<a href=\"" + request.getContextPath() + "/picture/get?imageID=" + cookieName.substring(15) + "\">" + URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8) + "</a> // ");
            }
        out.print("</div></div>");
    }
%>
<%
    if ((!uID.equals(user.getUID())) && friend.getState() == 0) {
%>
<p style="font-size:50px;color:red;margin-left: 100px;">对方设置不可被好友查看收藏！</p>
<%
} else if (pictures.isEmpty()) {
%>
<p style="font-size:50px;color:red;margin-left: 100px;">收藏夹空空如也！</p>
<%
} else {
    int pageNow = 1;
    int pages = (int) Math.ceil(pictures.size() / 6.0);
%>
<div id="content">
    <table id="result">
        <tr>
                <%
                int start = (pageNow - 1) * 6;
                int end = start + 5;
                if (end >= pictures.size()) end = pictures.size() - 1;
            for (int i = start; i <= end; i++) {
                Picture picture=pictures.get(i);
            %>
            <td><img src="${pageContext.request.contextPath}/img/large/<%=picture.getPath()%>">
                <div class="name"><%=picture.getTitle()%>
                </div>
                <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=picture.getImageID()%>">
                    <button>查看详情</button>
                </a>
                <%
                    if ((uID.equals(user.getUID()))) {
                %>
                <a onclick="return window.confirm('确定要删除吗？')"
                   href="${pageContext.request.contextPath}/favorites/delete?imageID=<%=picture.getImageID()%>&url=favorites.jsp">
                    <button>删除</button>
                </a>
                <%
                    }
                %>
                <div class="heat"><span class="fa fa-star"></span>热度：<%=picture.getPopularity()%>
                </div>
            </td>
                <%
            if (i - start == 2) out.print("</tr>");
            }
            %>
    </table>
        <%
        if (pictures.size() > 6) {
    %>
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <li>
                <a onclick="page(1)" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <%
                start = Math.max(pageNow - 4, 1);
                for (int i = start; i <= Math.min(start + 8, pages); i++)
                    if (i != pageNow) {
            %>
            <li><a onclick="page(<%=i%>)"><%=i%>
            </a></li>
            <%
            } else {
            %>
            <li class="active"><a onclick="page(<%=i%>)"><%=i%>
            </a></li>
            <%}%>
            <li>
                <a onclick="page(<%=pages%>)" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
        <br>
        <span>共<%=pages%>页</span>
    </nav>

        <%
        }
        out.print("</div>");
    }
%>
    <footer>Produced by 18SS YHT</footer>
    <script src="${pageContext.request.contextPath}/js/favorites.js"></script>
</body>
</html>
