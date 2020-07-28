<%@ page import="domain.User" %>
<%@ page import="domain.Picture" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-index</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/index.css">
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<%
    if (request.getAttribute("newestPictures") == null)
        request.getRequestDispatcher("/index").forward(request, response);
    List<Picture> newestPictures = (List<Picture>) request.getAttribute("newestPictures");
    List<Picture> mostPopularPictures = (List<Picture>) request.getAttribute("mostPopularPictures");

    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = true;
    if (user == null) isLoggedIn = false;
%>
<header>
    <div id="title">Travel</div>
    <div id="slogan">身未动,心已远。</div>
    <div id="navigator">
        <span class="now">首页 </span>
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
            <input type="text" value="index.jsp" name="url" style="display: none">
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
            <input type="text" value="index.jsp" name="url" style="display: none">
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

<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=mostPopularPictures.get(0).getImageID()%>"><img
                    src="${pageContext.request.contextPath}/img/large/<%=mostPopularPictures.get(0).getPath()%>"></a>
            <div class="carousel-caption">
                <h3><%=mostPopularPictures.get(0).getTitle()%>
                </h3>
            </div>
        </div>
        <div class="item">
            <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=mostPopularPictures.get(1).getImageID()%>"><img
                    src="${pageContext.request.contextPath}/img/large/<%=mostPopularPictures.get(1).getPath()%>"></a>
            <div class="carousel-caption">
                <h3><%=mostPopularPictures.get(1).getTitle()%>
                </h3>
            </div>
        </div>
        <div class="item">
            <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=mostPopularPictures.get(2).getImageID()%>"><img
                    src="${pageContext.request.contextPath}/img/large/<%=mostPopularPictures.get(2).getPath()%>"></a>
            <div class="carousel-caption">
                <h3><%=mostPopularPictures.get(2).getTitle()%>
                </h3>
            </div>
        </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<ul id="more">
    <% for (Picture picture : newestPictures) {
    %>
    <li>
        <p class="name"><%=picture.getTitle()%>
        </p>
        <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=picture.getImageID()%>"><img
                src="${pageContext.request.contextPath}/img/large/<%=picture.getPath()%>"></a>
        <p>作者:<%=picture.getAuthorName()%>
        </p>
        <p>主题:<%=picture.getContent()%>
        </p>
        <p>发布时间：<%=picture.getReleasedTime()%>
        </p>
    </li>
    <%
        }
    %>
    <li style="float:none;clear:both"></li>
</ul>
<footer>Produced by 18SS YHT</footer>
<script src="${pageContext.request.contextPath}/js/js-all.js"></script>
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
