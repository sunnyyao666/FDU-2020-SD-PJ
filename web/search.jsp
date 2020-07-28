<%@ page import="domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Picture" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-search</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = true;
    if (user == null) isLoggedIn = false;
%>
<header>
    <div id="title">Travel</div>
    <div id="slogan">身未动,心已远。</div>
    <div id="navigator">
        <span><a href="${pageContext.request.contextPath}/index.jsp"> 首页 </a></span>
        &nbsp;&nbsp;
        <span class="now"> 搜索</span>
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
            <input type="text" value="search.jsp" name="url" style="display: none">
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
            <input type="text" value="search.jsp" name="url" style="display: none">
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

<div id="head">搜索</div>
<form action="${pageContext.request.contextPath}/search" method="post" id="searchForm">
    <input type="text"
           name="searchContent"
           id="searchContent"
           placeholder="搜索内容"
           value="${param.searchContent}">
    &nbsp;
    <label for="submit"><span class="fa fa-search fa-2x"></span></label>
    <%
        boolean isTitle = !"Content".equals(request.getParameter("method"));
        boolean isReleasedTime = !"Popularity".equals(request.getParameter("order"));
    %>
    <p>搜索方式
        &nbsp;
        <input type="radio" value="Title" name="method" <%
            if (isTitle)out.print("checked");
        %>>标题
        &nbsp;
        <input type="radio" value="Content" name="method" <%
               if (!isTitle)out.print("checked");
        %>>主题
    </p>
    <p>排序方式
        &nbsp;
        <input type="radio" value="ReleasedTime" name="order" onclick="orderChange('ReleasedTime')"<%
               if (isReleasedTime)out.print("checked");
        %>>时间
        &nbsp;
        <input type="radio" value="Popularity" name="order" onclick="orderChange('Popularity')"<%
               if (!isReleasedTime)out.print("checked");
        %>>热度
    </p>
    <input type="submit" id="submit" style="display: none">
</form>

<%
    List<Picture> pictures = (List<Picture>) session.getAttribute("searchResult");
    if (pictures == null) {
%>
<p style="font-size:50px;color:red;margin-left: 100px;">请开始搜索！</p>
<%
} else if (pictures.isEmpty()) {
%>
<p style="font-size:50px;color:red;margin-left: 100px;">暂无结果！</p>
<%
} else {
    if (isReleasedTime) pictures = (List<Picture>) session.getAttribute("searchResultTime");
    int pageNow = 1;
    if (session.getAttribute("searchPageNow") != null) pageNow = (Integer) session.getAttribute("searchPageNow");
    else session.setAttribute("searchPageNow", 1);
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
                <div class="author"><%=picture.getAuthorName()%>
                </div>
                <a href="${pageContext.request.contextPath}/picture/get?imageID=<%=picture.getImageID()%>">
                    <button>查看详情</button>
                </a>
                <div class="heat"><span class="fa fa-star"></span> 热度：<%=picture.getPopularity()%>
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
    <script src="${pageContext.request.contextPath}/js/js-all.js"></script>
    <script src="${pageContext.request.contextPath}/js/search.js"></script>
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
