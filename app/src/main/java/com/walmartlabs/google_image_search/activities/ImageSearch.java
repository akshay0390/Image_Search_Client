package com.walmartlabs.google_image_search.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.walmartlabs.google_image_search.Listeners.EndlessScrollListener;
import com.walmartlabs.google_image_search.R;
import com.walmartlabs.google_image_search.adapters.ImageResultsAdapter;
import com.walmartlabs.google_image_search.dialogs.FilterDialog;
import com.walmartlabs.google_image_search.models.ImageFilter;
import com.walmartlabs.google_image_search.models.ImageResult;

import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageSearch extends AppCompatActivity implements FilterDialog.FilterDialogEventListener{

    private GridView gvImageResults;
    private static final String IMAGE_SEARCH_API = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private ImageFilter imageFilter;
    private String searchQuery;
    private int prevStart =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        setupViews();
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this,imageResults);
        gvImageResults.setAdapter(aImageResults);
        imageFilter = new ImageFilter();
        gvImageResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadMoreData(page);
                return true;
            }
        });
    }

    private void setupViews(){
        gvImageResults = (GridView) findViewById(R.id.gvImageResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                imageResults.clear();
                prevStart=0;
                StringBuilder baseSearchQuery = new StringBuilder(IMAGE_SEARCH_API+"&q="+query);
                String filterQuery = getSearchQuery(imageFilter);
                if(!TextUtils.isEmpty(filterQuery)){
                    baseSearchQuery.append(filterQuery);
                }
                searchView.clearFocus();
                searchImages(baseSearchQuery.toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    private void searchImages(String query){

        Boolean isNetworkAvailable = isNetworkAvailable();
        if(!isNetworkAvailable){
            Toast.makeText(this,"Network Not Available!",Toast.LENGTH_LONG).show();
        }else{
            AsyncHttpClient httpClient = new AsyncHttpClient();
            Log.i("INFO", "Calling Google API:"+query);
            httpClient.get(query, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray result = null;
                    try {
                        result = response.getJSONObject("responseData").getJSONArray("results");
                        aImageResults.addAll(ImageResult.fromJSONArray(result));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setUpOnImageClickHandler();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    private void setUpOnImageClickHandler(){
        gvImageResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageResult imageResult = imageResults.get(position);

                Intent i = new Intent(ImageSearch.this, Image.class);

                i.putExtra("imageResult", imageResult);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFilters(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialog filterDialog = FilterDialog.newInstance(this,"Image Filters",imageFilter);
        filterDialog.show(fm, "fragment_image_filters");

    }

    @Override
    public void cancelFilters() {
        //Do nothing
    }

    @Override
    public void saveFilters(ImageFilter filter) {
        this.imageFilter.setType(filter.getType());
        this.imageFilter.setColor(filter.getColor());
        this.imageFilter.setSize(filter.getSize());
        this.imageFilter.setSites(filter.getSites());
    }

    private String getSearchQuery(ImageFilter filter){
        StringBuilder queryURL = new StringBuilder();
        if(!TextUtils.isEmpty(filter.getType())){
            queryURL.append("&");
            queryURL.append(filter.getImageTypeQuery());
        }
        if(!TextUtils.isEmpty(filter.getColor())){
            queryURL.append("&");
            queryURL.append(filter.getImageColorQuery());
        }
        if(!TextUtils.isEmpty(filter.getSize())){
            queryURL.append("&");
            queryURL.append(filter.getImageSizeQuery());
        }
        if(!TextUtils.isEmpty(filter.getSites())){
            queryURL.append("&");
            queryURL.append(filter.getImageFilterSitesQuery());
        }
        return queryURL.toString();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void loadMoreData(int offset){
        Log.i("INFO","Load More Data:"+offset);
        StringBuilder baseSearchQuery = new StringBuilder(IMAGE_SEARCH_API + "&q=" + searchQuery);
        String filterQuery = getSearchQuery(imageFilter);
        if (!TextUtils.isEmpty(filterQuery)) {
            baseSearchQuery.append(filterQuery);
        }
        baseSearchQuery.append("&start=");
        prevStart = prevStart+8;
        baseSearchQuery.append(prevStart);
        searchImages(baseSearchQuery.toString());
    }

}
