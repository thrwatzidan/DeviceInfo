package com.dev.thrwat_zidan.deviceinfo;

public class Model {
    private String name;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Model(String name, int img) {

        this.name = name;
        this.img = img;
    }

    public Model() {

    }
}
