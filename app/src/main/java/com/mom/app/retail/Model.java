package com.mom.app.retail;


public class Model {

    private String name;
    private String place;
    private boolean selected;

    public Model(String name, String place) {
        this.name = name;
        this.place = place;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
