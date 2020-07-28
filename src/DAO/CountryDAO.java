package DAO;

import domain.Country;

import java.util.List;

public class CountryDAO extends DAO<Country> {
    public String getCountry_RegionNameByISO(String iSO) {
        String sql = "SELECT Country_RegionName FROM geocountries_regions WHERE ISO = ?";
        return getForValue(sql, iSO);
    }

    public String getISOByCountry_RegionName(String country_RegionName) {
        String sql = "SELECT ISO FROM geocountries_regions WHERE Country_RegionName = ?";
        return getForValue(sql, country_RegionName);
    }

    public List<Country> getAll() {
        String sql = "SELECT * FROM geocountries_regions ORDER BY Country_RegionName";
        return getForList(sql);
    }
}
