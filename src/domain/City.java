package domain;

public class City {
    private Long geoNameID;
    private String asciiName;
    private String country_RegionCodeISO;
    private String country;

    public City() {
    }

    public Long getGeoNameID() {
        return geoNameID;
    }

    public void setGeoNameID(Long geoNameID) {
        this.geoNameID = geoNameID;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    public String getCountry_RegionCodeISO() {
        return country_RegionCodeISO;
    }

    public void setCountry_RegionCodeISO(String country_RegionCodeISO) {
        this.country_RegionCodeISO = country_RegionCodeISO;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
