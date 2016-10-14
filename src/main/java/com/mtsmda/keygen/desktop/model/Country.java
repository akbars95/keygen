package com.mtsmda.keygen.desktop.model;

import java.util.List;

/**
 * Created by dminzat on 9/29/2016.
 */
public class Country {

    private String countryName;
    private List<City> cities;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(String countryName, List<City> cities) {
        this.countryName = countryName;
        this.cities = cities;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}