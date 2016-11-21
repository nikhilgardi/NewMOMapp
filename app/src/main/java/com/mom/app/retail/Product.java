package com.mom.app.retail;

/**
 * Created by nikhilg on 7/16/2016.
 */
public class Product {
    String planMRP;
    String planValidity;
    int image;
    boolean box;
    String planDescription;


    Product(String _planMRP, String _planValidity,String _planDescription, int _image, boolean _box) {
        planMRP = _planMRP;
        planValidity = _planValidity;
        planDescription=_planDescription;
        image = _image;
        box = _box;
    }
}
