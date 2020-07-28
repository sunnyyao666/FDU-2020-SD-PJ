<%@ page import="domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Country" %>
<%@ page import="domain.Picture" %>
<%@ page import="domain.City" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Travel-upload</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/reset.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/style-all.css">
    <link rel="stylesheet" title="default" href="${pageContext.request.contextPath}/css/upload.css">
</head>
<body>
<%
    if (request.getParameter("imageID") == null) {
        response.sendRedirect(request.getContextPath() + "/upload.jsp?imageID=-1");
        return;
    }
    if (request.getAttribute("countries") == null) {
        request.getRequestDispatcher("/getCountries").forward(request, response);
        return;
    }
    User user = (User) session.getAttribute("user");
    if (user == null) {
        out.print("<script>alert('请先登录！');</script>");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    Long imageID = Long.parseLong(request.getParameter("imageID"));
    Picture picture = (Picture) request.getAttribute("uploadingPicture");
    List<Country> countries = (List<Country>) request.getAttribute("countries");
    List<City> cities = (List<City>) request.getAttribute("cities");
    String countryNow = "China";
    String cityNow = "Shanghai";
    if (picture != null) {
        countryNow = picture.getCountry();
        cityNow = picture.getCity();
    }
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
                     "<tr><td><a href=\"javascript:;\" class=\"now\">上传图片</a><td><tr>" +
                      "<tr><td><a href=\""+ request.getContextPath()+"/myPictures.jsp\">我的图片</a><td><tr>" +
                       "<tr><td><a href=\""+ request.getContextPath()+"/friends.jsp\">我的好友</a><td><tr>" +
                        "<tr><td><a href=\""+ request.getContextPath()+"/logOut\">退出登录</a><td><tr></table></div></span>";

            out.print(nav);
        %>
    </nav>
</header>

<div id="head">图片<%
    if (imageID == -1) out.print("上传");
    else out.print("修改");
%>
</div>

<form action="${pageContext.request.contextPath}/upload" method="post" id="change" enctype="multipart/form-data">
    <div style="display: none"><input type="text"
                                      name="imageID"
                                      id="imageID"
                                      required
                                      value="<%=imageID%>"></div>
    <div>
        <span class="input-group-addon"><span class="fa fa-picture-o fa-fw"></span></span><input type="text"
                                                                                                 name="name"
                                                                                                 required
                                                                                                 id="name"
                                                                                                 placeholder="标题"

        <%if (picture!=null)out.print("value=\""+picture.getTitle()+"\"");%>>
        <p id="nameNull"><span class="fa fa-close"></span> 名称不能为空</p>
        <p id="nameChecked" style="color:green"><span class="fa fa-check"></span></p>
    </div>
    <div>
        <textarea
                name="description"
                id="description"
                placeholder="简介"
        ><%if (picture != null) out.print(picture.getDescription());%></textarea>
        <p id="descriptionChecked" style="color:green;display:inline-block"><span class="fa fa-check"></span></p>
    </div>
    <div>
        <span class="input-group-addon"><span class="fa fa-paint-brush fa-fw"></span></span><input type="text"
                                                                                                   name="content"
                                                                                                   id="content"
                                                                                                   required
                                                                                                   placeholder="主题"
        <%if (picture!=null)out.print("value=\""+picture.getContent()+"\"");%>>
        <p id="contentNull"><span class="fa fa-close"></span> 主题不能为空</p>
        <p id="contentChecked" style="color:green"><span class="fa fa-check"></span></p>
    </div>
    <div>
        <b>国家：</b>
        <select name="country" id="country" required>
            <%
                for (Country country : countries) {
            %>
            <option value="<%=country.getCountry_RegionName()%>" <%
                if (country.getCountry_RegionName().equals(countryNow)) out.print("selected=\"selected\"");%>>
                <%=country.getCountry_RegionName()%>
            </option>
            <%
                }
            %>
        </select>
        <p id="countryChecked" style="color:green;display:inline-block"><span class="fa fa-check"></span></p>
    </div>
    <div>
        <b>城市：</b>
        <select name="city" id="city" required>
            <%
                for (City city : cities) {
            %>
            <option value="<%=city.getAsciiName()%>" <%
                if (city.getAsciiName().equals(cityNow)) out.print("selected=\"selected\"");%>>
                <%=city.getAsciiName()%>
            </option>
            <%
                }
            %>
        </select>
        <p id="cityChecked" style="color:green;display:inline-block"><span class="fa fa-check"></span></p>
    </div>
    <div>
        <button type="button" onclick="FileImg.click()">上传文件</button>
        <input type="file" id="FileImg" onchange="UploadImg(this)" accept="image/*" style="display: none"
               name="FileImg">
        <img id="Img"
             style="width: 500px;" <%if (picture!=null)out.print("src=\""+request.getContextPath()+"/img/large/"+picture.getPath()+"\"");%>>
    </div>
    <button type="button" id="changeButton" disabled><%
        if (imageID == -1) out.print("上传");
        else out.print("修改");
    %></button>
    <button type="submit" id="btn3" style="display: none" name="submit4"></button>
    <button type="reset" id="reset">重置</button>
</form>
<footer>Produced by 18SS YHT</footer>
<script src="js/upload.js"></script>
</body>
</html>
