package servlet.upload;

import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.PictureDAO;
import domain.Picture;
import domain.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("myPictures");
        session.removeAttribute("searchResult");
        session.removeAttribute("searchResultPopularity");
        session.removeAttribute("picture");
        session.removeAttribute("comments");
        session.removeAttribute("isFavor");
        session.removeAttribute("searchResultTime");
        session.removeAttribute("searchPageNow");
        session.removeAttribute("favoritePictures");
        session.removeAttribute("favoriteFriend");
        Picture picture = new Picture();
        User user = (User) session.getAttribute("user");
        picture.setUID(user.getUID());
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        servletFileUpload.setHeaderEncoding("ISO8859_1");
        boolean isImgChanged = true;
        try {
            Long imageID = -1L;
            List<FileItem> items = servletFileUpload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField())
                    switch (item.getFieldName()) {
                        case "imageID":
                            imageID = Long.parseLong(item.getString());
                            break;
                        case "name":
                            picture.setTitle(new String(item.getString().getBytes("ISO8859_1"), StandardCharsets.UTF_8));
                            break;
                        case "content":
                            picture.setContent(new String(item.getString().getBytes("ISO8859_1"), StandardCharsets.UTF_8));
                            break;
                        case "description":
                            picture.setDescription(new String(item.getString().getBytes("ISO8859_1"), StandardCharsets.UTF_8));
                            break;
                        case "country":
                            picture.setCountry(item.getString());
                            break;
                        case "city":
                            picture.setCity(item.getString());
                            break;
                    }
                else {
                    String fileName = item.getName();
                    if (!fileName.contains(".")) {
                        isImgChanged = false;
                        continue;
                    }
                    InputStream in = item.getInputStream();
                    byte[] buffer = new byte[1024];
                    String path = (new Random().nextInt(1000) + System.currentTimeMillis()) + fileName.substring(fileName.indexOf("."));
                    picture.setPath(path);
                    String url = getServletContext().getRealPath("/img/") + "/large/" + path;
                    int len = 0;
                    OutputStream out = new FileOutputStream(url);
                    while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
                    out.close();
                    in.close();
                }
            }
            CountryDAO countryDAO = new CountryDAO();
            picture.setCountry_RegionCodeISO(countryDAO.getISOByCountry_RegionName(picture.getCountry()));
            CityDAO cityDAO = new CityDAO();
            picture.setCityCode((long) cityDAO.getGeoNameIDByAsciiName(picture.getCity()));
            PictureDAO pictureDAO = new PictureDAO();
            if (imageID == -1) {
                pictureDAO.save(picture);
                response.setHeader("Content-Type", "text/html; charset=UTF-8");
                response.getWriter().print("<script> alert('上传成功！'); window.location.href='" + request.getContextPath() + "/index.jsp'; </script>");
            } else {
                Picture pictureNow = pictureDAO.getByImageID(imageID);
                if (isImgChanged) {
                    String deleteUrl = getServletContext().getRealPath("/img/") + "/large/" + pictureNow.getPath();
                    new File(deleteUrl).delete();
                } else picture.setPath(pictureNow.getPath());
                picture.setImageID(imageID);
                picture.setReleasedTime(new Timestamp(new java.util.Date().getTime()));
                pictureDAO.updatePicture(picture);
                Cookie[] cookies = request.getCookies();
                if ((cookies != null) && (cookies.length > 0))
                    for (Cookie c : cookies) {
                        String cookieName = c.getName();
                        if (cookieName.equals("TRAVEL_PICTURE_" + imageID)) {
                            Cookie cookie = new Cookie(cookieName, URLEncoder.encode(picture.getTitle(), StandardCharsets.UTF_8));
                            cookie.setPath(request.getContextPath());
                            response.addCookie(cookie);
                            break;
                        }
                    }
                response.setHeader("Content-Type", "text/html; charset=UTF-8");
                response.getWriter().print("<script> alert('修改成功！'); window.location.href='" + request.getContextPath() + "/index.jsp'; </script>");
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
