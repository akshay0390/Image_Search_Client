package com.walmartlabs.google_image_search.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by akulka2 on 10/17/15.
 */
public class ImageResult implements Serializable {

    private static final long serialVersionUID = 1131581681282643907L;
    private String fullUrl;
    private String title;
    private String thumbUrl;

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public ImageResult(JSONObject jsonObject){
        try{
            this.fullUrl = jsonObject.getString("url");
            this.title = jsonObject.getString("title");
            this.thumbUrl = jsonObject.getString("tbUrl");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray jsonArray){
        ArrayList<ImageResult> imageResults = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            try{
                imageResults.add(new ImageResult(jsonArray.getJSONObject(i)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return imageResults;
    }
}
