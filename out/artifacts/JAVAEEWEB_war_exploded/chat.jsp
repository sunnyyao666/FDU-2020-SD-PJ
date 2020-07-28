<%@ page import="domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Message" %><%--
  Created by IntelliJ IDEA.
  User: 姚鸿韬
  Date: 2020/7/25
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-chat</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/chat.css">

    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        out.print("<script>alert('请先登录！');</script>");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    Long friendUID = Long.parseLong(request.getParameter("uID"));
    if (request.getAttribute("messagesHistory") == null) {
        response.sendRedirect(request.getContextPath() + "/getMessagesHistory?uID=" + friendUID);
        return;
    }
    List<Message> messagesHistory = (List<Message>) request.getAttribute("messagesHistory");
    User chattingFriend = (User) request.getAttribute("chattingFriend");
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
                    "<table id=\"dropDownList\"><tr><td><a href=\""+ request.getContextPath()+"/favorites.jsp\">我的收藏</a><td><tr>" +
                     "<tr><td><a href=\"javascript:;\">上传图片</a><td><tr>" +
                      "<tr><td><a href=\""+ request.getContextPath()+"/myPictures.jsp\">我的图片</a><td><tr>" +
                       "<tr><td><a href=\""+ request.getContextPath()+"/friends.jsp\">我的好友</a><td><tr>" +
                        "<tr><td><a href=\""+ request.getContextPath()+"/logOut\">退出登录</a><td><tr></table></div></span>";

            out.print(nav);
        %>
    </nav>
</header>
<div id="head">与<%=chattingFriend.getUsername()%>的聊天</div>
<div id="messagesDiv">
    <table id="messages">
        <%
            for (Message message : messagesHistory) {
                if (message.getToUID().equals(friendUID)) out.print("<tr class=\"mine\">");
                else out.print("<tr class=\"friend\">");
        %>
        <td>
            <div class="createdTime"><%=message.getCreatedTime()%>
            </div>
            <div class="messageContent"><%=message.getContent()%></div>
        </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<div id="sendMessage">
    <textarea id="message" required onkeyup="dealEnter(event.keyCode)"></textarea>
    <button id="send" onclick="sendMessage(<%=user.getUID()%>,<%=friendUID%>,0)">发送</button>
</div>
<footer>Produced by 18SS YHT</footer>
<script src="${pageContext.request.contextPath}/js/chat.js"></script>
<script>loadData(<%=user.getUID()%>);</script>
</body>
</html>
