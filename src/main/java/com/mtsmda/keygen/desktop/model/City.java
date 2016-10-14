package com.mtsmda.keygen.desktop.model;

/**
 * Created by dminzat on 9/29/2016.
 */
public class City {

    private String cityName;
    private Country country;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}