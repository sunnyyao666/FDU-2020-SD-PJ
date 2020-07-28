package domain;

public class Country {
    private String iSO;
    private String country_RegionName;

    public Country() {
    }

    public String getISO() {
        return iSO;
    }

    public void setISO(String iSO) {
        this.iSO = iSO;
    }

    public String getCountry_RegionName() {
        return country_RegionName;
    }

    public void setCountry_RegionName(String country_RegionName) {
        this.country_RegionName = country_RegionName;
    }
}
