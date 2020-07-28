package DAO;

import domain.Picture;

import java.util.List;

public class PictureDAO extends DAO<Picture> {
    public List<Picture> getListByUID(Long uID) {
        String sql = "SELECT * FROM travelimage WHERE UID = ?";
        return completePictures(getForList(sql, uID));
    }

    public void deleteByImageID(Long imageID) {
        String sql = "DELETE FROM travelimage WHERE ImageID = ?";
        update(sql, imageID);
    }

    public void updatePicture(Picture picture) {
        String sql = "UPDATE travelimage SET Title = ?, Description = ?, CityCode = ?, Country_RegionCodeISO = ?, PATH = ?, Content = ?, ReleasedTime = ? WHERE ImageID = ?";
        update(sql, picture.getTitle(), picture.getDescription(), picture.getCityCode(), picture.getCountry_RegionCodeISO(), picture.getPath(), picture.getContent(), picture.getReleasedTime(), picture.getImageID());
    }

    public void save(Picture picture) {
        String sql = "INSERT INTO travelimage (Title, Description, CityCode, Country_RegionCodeISO, UID, PATH, Content) VALUES (? ,? ,? ,? ,? ,? ,?)";
        update(sql, picture.getTitle(), picture.getDescription(), picture.getCityCode(), picture.getCountry_RegionCodeISO(), picture.getUID(), picture.getPath(), picture.getContent());
    }

    public List<Picture> getListByMethodContainingOrderByOrder(String searchContent, String method, String order) {
        String sql;
        if ("Title".equals(method)) {
            if ("Popularity".equals(order))
                sql = "SELECT * FROM travelimage WHERE Title LIKE ? ORDER BY Popularity DESC";
            else
                sql = "SELECT * FROM travelimage WHERE Title LIKE ? ORDER BY ReleasedTime DESC";
        } else {
            if ("Popularity".equals(order))
                sql = "SELECT * FROM travelimage WHERE Content LIKE ? ORDER BY Popularity DESC";
            else sql = "SELECT * FROM travelimage WHERE Content LIKE ? ORDER BY ReleasedTime DESC";
        }
        return completePictures(getForList(sql, "%" + searchContent + "%"));
    }

    public void updatePopularityByImageID(Long imageID, int i) {
        Picture picture = getByImageID(imageID);
        String sql = "UPDATE travelimage SET Popularity = ? WHERE ImageID = ?";
        update(sql, i + picture.getPopularity(), imageID);
    }

    public List<Picture> getThreeByMostPopular() {
        String sql = "SELECT * FROM travelimage ORDER BY Popularity DESC LIMIT 3";
        return completePictures(getForList(sql));
    }

    public List<Picture> getThreeByNewest() {
        String sql = "SELECT * FROM travelimage ORDER BY ReleasedTime DESC LIMIT 3";
        return completePictures(getForList(sql));
    }

    public Picture getByImageID(Long imageID) {
        String sql = "SELECT * FROM travelimage WHERE ImageID = ?";
        return completePicture(get(sql, imageID));
    }

    private List<Picture> completePictures(List<Picture> pictures) {
        for (Picture picture : pictures) completePicture(picture);
        return pictures;
    }

    private Picture completePicture(Picture picture) {
        UserDAO userDAO = new UserDAO();
        picture.setAuthorName(userDAO.getUsernameByUID(picture.getUID()));
        CityDAO cityDAO = new CityDAO();
        picture.setCity(cityDAO.getAsciiNameByGeoNameID(picture.getCityCode()));
        CountryDAO countryDAO = new CountryDAO();
        picture.setCountry(countryDAO.getCountry_RegionNameByISO(picture.getCountry_RegionCodeISO()));
        return picture;
    }
}
