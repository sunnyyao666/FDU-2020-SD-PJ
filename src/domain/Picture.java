package domain;

import java.sql.Timestamp;


public class Picture {
    private Long imageID;
    private String authorName;
    private Long uID;

    private String title;
    private String content;
    private String description;
    private int popularity;

    private String country;
    private String country_RegionCodeISO;

    private String city;
    private Long cityCode;

    private Timestamp releasedTime;

    private String path;

    public Picture() {
    }

    public Picture(String authorName, String title, String content, String description, int popularity, String country, String city, String path) {
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.description = description;
        this.popularity = popularity;
        this.country = country;
        this.city = city;
        this.path = path;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getUID() {
        return uID;
    }

    public void setUID(Long uID) {
        this.uID = uID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_RegionCodeISO() {
        return country_RegionCodeISO;
    }

    public void setCountry_RegionCodeISO(String country_RegionCodeISO) {
        this.country_RegionCodeISO = country_RegionCodeISO;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Timestamp getReleasedTime() {
        return releasedTime;
    }

    public void setReleasedTime(Timestamp releasedTime) {
        this.releasedTime = releasedTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
