<%@ page import="domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Friend" %>
<%@ page import="java.nio.file.attribute.UserDefinedFileAttributeView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-friends</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/friends.css">
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        out.print("<script>alert('请先登录！');</script>");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    if (request.getAttribute("friends") == null) {
        request.getRequestDispatcher("/friends/get").forward(request, response);
        return;
    }
    List<User> friends = (List<User>) request.getAttribute("friends");
    List<Friend> friendsInviting = (List<Friend>) request.getAttribute("friendsInviting");
    List<Friend> friendsInvited = (List<Friend>) request.getAttribute("friendsInvited");
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
                     "<tr><td><a href=\""+ request.getContextPath()+"/upload.jsp?imageID=-1\">上传图片</a><td><tr>" +
                      "<tr><td><a href=\""+ request.getContextPath()+"/myPictures.jsp\">我的图片</a><td><tr>" +
                       "<tr><td><a href=\"javascript:;\" class=\"now\">我的好友</a><td><tr>" +
                        "<tr><td><a href=\""+ request.getContextPath()+"/logOut\">退出登录</a><td><tr></table></div></span>";

            out.print(nav);
        %>
    </nav>
</header>

<div id="head">我的好友</div>
<form action="${pageContext.request.contextPath}/friends/search" method="post" id="searchForm">
    <input type="text"
           name="searchContent"
           id="searchContent"
           placeholder="输入用户名搜索用户"
           value="${param.searchContent}">
    &nbsp;
    <label for="submit"><span class="fa fa-search fa-2x"></span></label>
    <input type="submit" id="submit" style="display: none">
</form>
<div id="content">
    <%
        List<User> searchResult = (List<User>) request.getAttribute("searchResult");
        if (searchResult != null) {
            if (searchResult.isEmpty()) {
    %>
    <div id="searchResult">
        <p class="title">搜索结果</p>
        <p style="font-size:30px;color:red;margin-left: 30px;">暂无结果！</p>
    </div>
    <%
    } else {
    %>
    <div id="searchResult">
        <p class="title">搜索结果</p>
        <table>
            <tr>
                <th>用户名</th>
                <th>邮箱</th>
                <th>注册时间</th>
                <th>操作</th>
            </tr>
            <%
                for (User friend : searchResult) {
            %>
            <tr>
                <td><%=friend.getUsername()%>
                </td>
                <td><%=friend.getEmail()%>
                </td>
                <td><%=friend.getDateJoined()%>
                </td>
                <td><a href="${pageContext.request.contextPath}/friends/add?uID=<%=friend.getUID()%>">
                    <button>添加</button>
                </a></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
            }
        }
    %>
    <div id="mine">
        <%
            if (friends.isEmpty()) {
        %>
        <p class="title">好友列表</p>
        <p style="font-size:30px;color:red;margin-left: 30px;">暂无好友</p>
        <%
        } else {
        %>
        <p class="title">好友列表</p>
        <table>
            <tr>
                <th>用户名</th>
                <th>邮箱</th>
                <th>注册时间</th>
                <th>操作</th>
            </tr>
            <%
                for (User friend : friends) {
            %>
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/favorites.jsp?uID=<%=friend.getUID()%>"><%=friend.getUsername()%>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/favorites.jsp?uID=<%=friend.getUID()%>"><%=friend.getEmail()%>
                    </a>
                </td>
                <td><%=friend.getDateJoined()%>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/getMessagesHistory?uID=<%=friend.getUID()%>">
                    <button>聊天</button>
                    </a>
                    <a onclick="return window.confirm('确定要删除吗？')"
                       href="${pageContext.request.contextPath}/friends/delete?uID=<%=friend.getUID()%>">
                        <button>删除</button>
                    </a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
        <p id="showFavorites">向好友展示我的收藏
            &nbsp;
            <input type="radio" value="true" onclick="stateChange(1)" name="showFavorites" <%
            if (user.getState()==1)out.print("checked");
        %>> 是
            &nbsp;
            <input type="radio" value="false" onclick="stateChange(0)" name="showFavorites"<%
               if (user.getState()==0)out.print("checked");
        %>> 否
        </p>
    </div>

    <%
        if (friendsInviting.isEmpty()) {
    %>
    <div id="invite">
        <p class="title">我的申请</p>
        <p style="font-size:30px;color:red;margin-left: 30px;">暂无申请</p>
    </div>
    <%
    } else {
    %>
    <div id="invite">
        <p class="title">我的申请</p>
        <table>
            <tr>
                <th>用户名</th>
                <th>申请时间</th>
                <th>状态</th>
            </tr>
            <%
                for (Friend friend : friendsInviting) {
            %>
            <tr>
                <td><%=friend.getUsername2()%>
                </td>
                <td><%=friend.getCreatedTime()%>
                </td>
                <td>
                    <%
                        if (friend.getState() == 1) out.print("<span style=\"color:green\">已通过</span>");
                        else if (friend.getState() == 0) out.print("<span>申请中</span>");
                        else out.print("<span style=\"color:red\">已拒绝</span>");
                    %>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
        if (friendsInvited.isEmpty()) {
    %>
    <div id="invited">
        <p class="title">好友申请</p>
        <p style="font-size:30px;color:red;margin-left: 30px;">暂无申请</p>
    </div>
    <%
    } else {
    %>
    <div id="invited">
        <p class="title">好友申请</p>
        <table>
            <tr>
                <th>用户名</th>
                <th>申请时间</th>
                <th>操作/状态</th>
            </tr>
            <%
                for (Friend friend : friendsInvited) {
            %>
            <tr>
                <td><%=friend.getUsername1()%>
                </td>
                <td><%=friend.getCreatedTime()%>
                </td>
                <td>
                    <%
                        if (friend.getState() == 1) out.print("<span style=\"color:green\">已通过</span>");
                        else if (friend.getState() == -1) out.print("<span style=\"color:red\">已拒绝</span>");
                        else {
                    %>
                    <a href="${pageContext.request.contextPath}/friends/audit?uID=<%=friend.getUID1()%>&state=1">
                        <button>通过</button>
                    </a>
                    <a href="${pageContext.request.contextPath}/friends/audit?uID=<%=friend.getUID1()%>&state=-1">
                        <button>拒绝</button>
                    </a><%
                    }
                %>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
    %>
</div>
<footer>Produced by 18SS YHT</footer>
<script src="${pageContext.request.contextPath}/js/friends.js"></script>
</body>
</html>
