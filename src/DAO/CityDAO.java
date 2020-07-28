package DAO;

import domain.City;

import java.util.List;

public class CityDAO extends DAO<City> {
    public String getAsciiNameByGeoNameID(Long geoNameID) {
        String sql = "SELECT AsciiName FROM geocities WHERE GeoNameID = ?";
        return getForValue(sql, geoNameID);
    }

    public int getGeoNameIDByAsciiName(String asciiName) {
        String sql = "SELECT GeoNameID FROM geocities WHERE AsciiName = ?";
        return getForValue(sql, asciiName);
    }

    public List<City> getListByCountry_RegionCodeISO(String country_RegionCodeISO) {
        String sql = "SELECT * FROM geocities WHERE Country_RegionCodeISO = ? ORDER BY AsciiName";
        return completeCities(getForList(sql, country_RegionCodeISO));
    }

    private List<City> completeCities(List<City> cities) {
        for (City city : cities) completeCity(city);
        return cities;
    }

    private City completeCity(City city) {
        CountryDAO countryDAO = new CountryDAO();
        city.setCountry(countryDAO.getCountry_RegionNameByISO(city.getCountry_RegionCodeISO()));
        return city;
    }
}
