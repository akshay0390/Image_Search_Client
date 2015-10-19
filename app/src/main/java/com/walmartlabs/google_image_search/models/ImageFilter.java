package com.walmartlabs.google_image_search.models;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by akulka2 on 10/17/15.
 */
public class ImageFilter implements Serializable{

    private static final long serialVersionUID = -8341186933031346152L;
    private String size;
    private String color;
    private String type;
    private String sites;

    private static final String IMAGE_COLOR_QUERY= "imgcolor";
    private static final String IMAGE_SIZE_QUERY = "imgsz";
    private static final String IMAGE_TYPE_QUERY = "imgtype";
    private static final String RESTRICT_SITE_QUERY = "as_sitesearch";
    private static final String EQUALS_OPERATOR = "=";


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getImageColorQuery(){
        StringBuilder sb = new StringBuilder();

        sb.append(IMAGE_COLOR_QUERY);
        sb.append(EQUALS_OPERATOR);
        sb.append(this.color);

        return sb.toString();
    }

    public String getImageSizeQuery(){
        StringBuilder sb = new StringBuilder();

        sb.append(IMAGE_SIZE_QUERY);
        sb.append(EQUALS_OPERATOR);
        sb.append(this.size);

        return sb.toString();
    }

    public String getImageTypeQuery(){
        StringBuilder sb = new StringBuilder();

        sb.append(IMAGE_TYPE_QUERY);
        sb.append(EQUALS_OPERATOR);
        sb.append(this.type);

        return sb.toString();
    }

    public String getImageFilterSitesQuery(){
        StringBuilder sb = new StringBuilder();

        sb.append(RESTRICT_SITE_QUERY);
        sb.append(EQUALS_OPERATOR);
        sb.append(this.sites);
        return sb.toString();
    }
}
