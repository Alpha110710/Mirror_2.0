package com.example.dllo.mirror_20.Bean;

/**
 * Created by dllo on 16/6/29.
 */
public class DBDataBean {
    private String introContent;
    private String cellHeight;
    private String name;
    private String location;
    private String country;
    private String english;

    public DBDataBean() {
    }

    public DBDataBean(String introContent, String cellHeight, String name, String location, String country, String english) {
        this.introContent = introContent;
        this.cellHeight = cellHeight;
        this.name = name;
        this.location = location;
        this.country = country;
        this.english = english;
    }

    public String getIntroContent() {

        return introContent;
    }

    public void setIntroContent(String introContent) {
        this.introContent = introContent;
    }

    public String getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(String cellHeight) {
        this.cellHeight = cellHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
