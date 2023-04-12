package com.rovaindu.serviesdashboard.model;

import java.io.Serializable;

public class AgentServies implements Serializable {

    private int id;
    private String name;
    private String desc;
    private int image;
    private int rate;
    private int ratecount;
    private double price;
    private boolean selected;

    public AgentServies(int id, String name, String desc, int image, int rate, int ratecount, double price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.rate = rate;
        this.ratecount = ratecount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRatecount() {
        return ratecount;
    }

    public void setRatecount(int ratecount) {
        this.ratecount = ratecount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}