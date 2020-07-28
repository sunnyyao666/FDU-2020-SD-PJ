<%@ page import="domain.Picture" %>
<%@ page import="domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="domain.Comment" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/detail.css">
</head>
<body>

<%
    Picture picture = (Picture) session.getAttribute("picture");
    if (picture == null)
        if (request.getParameter("imageID") != null) {
            response.sendRedirect(request.getContextPath() + "/picture/get?imageID=" + request.getParameter("imageID"));
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/picture/get?imageID=1");
            return;
        }
    else if ((request.getParameter("imageID") != null) && (Long.parseLong(request.getParameter("imageID")) != (picture.getImageID()))) {
        response.sendRedirect(request.getContextPath() + "/picture/get?imageID=" + request.getParameter("imageID"));
        return;
    }
    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = false;
    boolean isFavor = false;
    if (user != null) {
        isLoggedIn = true;
        if (session.getAttribute("isFavor") == null) {
            response.sendRedirect(request.getContextPath() + "/picture/get?imageID=" + picture.getImageID());
            return;
        }
        isFavor = (Boolean) session.getAttribute("isFavor");

        Cookie[] cookies = request.getCookies();
        List<Cookie> pictureCookies = new ArrayList<Cookie>();
        Cookie deleteCookie = null;
        if ((cookies != null) && (cookies.length > 0))
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                if (cookieName.startsWith("TRAVEL_PICTURE_")) {
                    pictureCookies.add(c);
                    if (picture.getImageID().toString().equals(cookieName.substring(15))) deleteCookie = c;
                }
            }
        if ((pictureCookies.size() >= 10) && (deleteCookie == null)) deleteCookie = pictureCookies.get(0);
        if (deleteCookie != null) {
            deleteCookie.setMaxAge(0);
            deleteCookie.setPath(request.getContextPath());
            response.addCookie(deleteCookie);
        }
        Cookie cookie = new Cookie("TRAVEL_PICTURE_" + picture.getImageID(), URLEncoder.encode(picture.getTitle(), StandardCharsets.UTF_8));
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    List<Comment> comments = (List<Comment>) session.getAttribute("comments");
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
            if (!isLoggedIn) nav = "      <span class=\"fa fa-sign-in\"></span> <a href=\"#log-in\" onclick=\"resetPIN1()\">登录</a> or" +
                    "          <a href=\"#register\" onclick=\"reset()\">注册</a>";
            else nav = " <span class=\"fa fa-user-o\"><div id=\"drop\"><span id=\"username\"> &nbsp;" + user.getUsername() + "</span>" +
                    "<table id=\"dropDownList\"><tr><td><a href=\""+ request.getContextPath()+"/favorites.jsp\">我的收藏</a><td><tr>" +
                     "<tr><td><a href=\""+ request.getContextPath()+"/upload.jsp?imageID=-1\">上传图片</a><td><tr>" +
                      "<tr><td><a href=\""+ request.getContextPath()+"/myPictures.jsp\">我的图片</a><td><tr>" +
                       "<tr><td><a href=\""+ request.getContextPath()+"/friends.jsp\">我的好友</a><td><tr>" +
                        "<tr><td><a href=\""+ request.getContextPath()+"/logOut\">退出登录</a><td><tr></table></div></span>";

            out.print(nav);
        %>
    </nav>
</header>

<div id="log-in" class="popup">
    <div id="log-in-content">
        <p class="title">登录</p>
        <p class="message">${requestScope.loginMessage}</p>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="text" value="detail.jsp" name="url" style="display: none">
            <div>
                <span class="input-group-addon"><span class="fa fa-user-o fa-fw"></span></span><input type="text"
                                                                                                      name="username1"
                                                                                                      id="username1"
                                                                                                      required
                                                                                                      placeholder="用户名/邮箱"
                                                                                                      value="${param.username1}">
            </div>
            <div>
                <span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span><input type="password"
                                                                                                   name="password1"
                                                                                                   id="password1"
                                                                                                   required
                                                                                                   placeholder="密码">
            </div>
            <div>
                <span class="input-group-addon"><span class="glyphicon glyphicon-check"></span></span><input type="text"
                                                                                                             name="PIN"
                                                                                                             id="PIN1"
                                                                                                             required
                                                                                                             placeholder="验证码"/>
                <img src="${pageContext.request.contextPath}/PIN" id="PINImage1" style="cursor: pointer"
                     onclick="resetPIN1()">
            </div>
            <button type="button" id="loginButton">登录</button>
            <button type="reset" onclick="location.href='#'">取消</button>
            <button type="submit" id="btn1" style="display: none" name="submit2"></button>
        </form>
    </div>
</div>

<div id="register" class="popup">
    <div id="register-content">
        <p class="title">注册</p>
        <p class="message">${requestScope.registerMessage}</p>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="text" value="detail.jsp" name="url" style="display: none">
            <div>
                <span class="input-group-addon"><span class="fa fa-user-o fa-fw"></span></span><input type="text"
                                                                                                      name="username2"
                                                                                                      required
                                                                                                      id="username2"
                                                                                                      placeholder="用户名"
                                                                                                      value="${param.username2}">
                <p id="username2Null"><span class="fa fa-close"></span> 用户名不能为空</p>
                <p id="username2Wrong"><span class="fa fa-close"></span> 用户名格式不正确<br>格式：大小写字母、数字、下划线<br>4到15位，包含4与15</p>
                <p id="username2Checked" style="color:green"><span class="fa fa-check"></span></p>
            </div>
            <div>
                <span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span><input type="password"
                                                                                                   name="password2"
                                                                                                   id="password2"
                                                                                                   required
                                                                                                   placeholder="密码">
                <p id="password2Null"><span class="fa fa-close"></span> 密码不能为空</p>
                <p id="password2Wrong"><span class="fa fa-close"></span> 密码格式不正确<br>格式：大小写字母、数字、下划线<br>6到12位，包含6与12</p>
                <p id="password2Checked" style="color:green"><span class="fa fa-check"></span></p>
            </div>
            <table border="0px" cellpadding="0px" cellspacing="1px" id="password-check">
                <tr height="20px">
                    <td width="50px" id="lv1" style="border-top: 8px solid darkgrey;">弱</td>
                    <td width="50px" id="lv2" style="border-top: 8px solid darkgrey;">中</td>
                    <td width="50px" id="lv3" style="border-top: 8px solid darkgrey;">强</td>
                </tr>
            </table>
            <div>
                <span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span><input type="password"
                                                                                                   name="repassword"
                                                                                                   id="repassword"
                                                                                                   required
                                                                                                   placeholder="确认密码">
                <p id="repasswordNull"><span class="fa fa-close"></span> 确认密码不能为空</p>
                <p id="repasswordWrong"><span class="fa fa-close"></span> 确认密码与原密码不同</p>
                <p id="repasswordChecked" style="color:green"><span class="fa fa-check"></span></p>
            </div>
            <div>
                <span class="input-group-addon"><span class="fa fa-envelope-o fa-fw"></span></span><input type="text"
                                                                                                          name="email"
                                                                                                          id="email"
                                                                                                          required
                                                                                                          placeholder="邮箱"
                                                                                                          value="${param.email}">
                <p id="emailNull"><span class="fa fa-close"></span> 邮箱不能为空</p>
                <p id="emailWrong"><span class="fa fa-close"></span> 邮箱格式不正确<br>格式：例：xxx123@126.com</p>
                <p id="emailChecked" style="color:green"><span class="fa fa-check"></span></p>
            </div>
            <div>
                <span class="input-group-addon"><span class="glyphicon glyphicon-check"></span></span><input type="text"
                                                                                                             name="PIN"
                                                                                                             id="PIN2"
                                                                                                             required
                                                                                                             placeholder="验证码"/>
                <img src="${pageContext.request.contextPath}/PIN" id="PINImage2" style="cursor: pointer"
                     onclick="resetPIN2()">
            </div>
            <button type="button" id="registerButton" disabled>注册</button>
            <button type="submit" id="btn2" style="display: none" name="submit1"></button>
            <button type="reset" onclick="location.href='#'">取消</button>
        </form>
    </div>
</div>

<div id="head">
    <div id="name"><%=picture.getTitle()%>
    </div>
    <div id="author">By <%=picture.getAuthorName()%>
    </div>
</div>
<div id="detail">
    <div id="box">
        <div id="img-shadow">
            <img src="${pageContext.request.contextPath}/img/large/<%=picture.getPath()%>" id="small">
            <div id="shadow"></div>
        </div>
        <div id="showDetails">
            <img src="${pageContext.request.contextPath}/img/large/<%=picture.getPath()%>" id="big">
        </div>
    </div>
    <div id="description">
        <%out.print(picture.getDescription() != null ? picture.getDescription() : "Sorry, no description.");%>
    </div>
    <div id="content">主题 : <span> <%=picture.getContent()%></span></div>
    <%
        if (!isLoggedIn) {
    %>
    <a href="">
        <button onclick="alert('请先登录！');"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span> 收藏
        </button>
    </a>
    <%
    } else if (!isFavor) {
    %>
    <a href="${pageContext.request.contextPath}/favorites/add?imageID=<%=picture.getImageID()%>">
        <button><span class="glyphicon glyphicon-heart" aria-hidden="true"></span> 收藏
        </button>
    </a>
    <%
    } else {
    %>
    <a href="${pageContext.request.contextPath}/favorites/delete?imageID=<%=picture.getImageID()%>&url=detail.jsp">
        <button><span class="glyphicon glyphicon-heart" aria-hidden="true"></span> 取消收藏
        </button>
    </a>
    <%
        }
    %>

    <table>
        <tr>
            <th colspan="2">照片详情</th>
        </tr>
        <tr>
            <td>国家：</td>
            <td><%out.print(picture.getCountry() != null ? picture.getCountry() : "Unknown");%>
            </td>
        </tr>
        <tr>
            <td>城市：</td>
            <td><%out.print(picture.getCity() != null ? picture.getCity() : "Unknown");%>
            </td>
        </tr>
        <tr>
            <td>发布时间：</td>
            <td><%=picture.getReleasedTime()%>
            </td>
        </tr>
        <tr>
            <td>热度：</td>
            <td><%=picture.getPopularity()%>
            </td>
        </tr>
    </table>
</div>

<div id="commentDiv">
    <form method="post" action="${pageContext.request.contextPath}/comments/comment">
        <textarea name="comment" id="comment" placeholder="发表评论..." required <%
            if (!isLoggedIn) out.print("disabled");%>></textarea>
        <button type="submit"<%if (!isLoggedIn) out.print("disabled");%>>发表</button>
    </form>
    <div id="subtitle">全部评论</div>
    <%
        if (comments.isEmpty()) {
    %>
    <p style="font-size:30px;color:red;margin-left: 100px;">暂无评论！</p>
    <%
    } else {
    %>
    <table id="comments">
        <%
            for (Comment comment : comments) {
        %>
        <tr>
            <td>
                <div class="username"><%=comment.getUsername()%>
                </div>
                <div class="createdTime"><%=comment.getCreatedTime()%>
                </div>
                <br>
                <div class="commentContent"><%=comment.getContent()%></div>
                <div class="popularity"><a onclick="popularity(this,<%=comment.getCommentID()%>)" <%
                    if (isLoggedIn) {
                        if (comment.getLikedUIDs().contains("," + user.getUID() + ","))
                            out.print("style=\"color:red\"");
                        else out.print("style=\"color:#337ab7\"");
                    }
                %>><span class="glyphicon glyphicon-thumbs-up"
                         aria-hidden="true"></span></a>
                    <span><%=comment.getPopularity()%></span>
                </div>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>
</div>
<footer>Produced by 18SS YHT</footer>
<script src="${pageContext.request.contextPath}/js/js-all.js"></script>
<script src="${pageContext.request.contextPath}/js/detail.js"></script>
<%
    if (request.getAttribute("loginMessage") != null) {
%>
<script>location.href = "#log-in"</script>
<%
} else if (request.getAttribute("registerMessage") != null) {
%>
<script>
    reset();
    location.href = "#register"
</script>
<%
    }
%>
</body>
</html>
