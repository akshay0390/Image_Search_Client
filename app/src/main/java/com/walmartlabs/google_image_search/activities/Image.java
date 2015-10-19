package com.walmartlabs.google_image_search.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.walmartlabs.google_image_search.R;
import com.walmartlabs.google_image_search.models.ImageResult;

public class Image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView ivFullImage = (ImageView) findViewById(R.id.ivFullImage);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("imageResult");
        Picasso.with(this).load(imageResult.getFullUrl()).into(ivFullImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
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
}
