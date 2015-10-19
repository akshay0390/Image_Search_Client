package com.walmartlabs.google_image_search.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.walmartlabs.google_image_search.R;
import com.walmartlabs.google_image_search.models.ImageResult;

import java.util.List;

/**
 * Created by akulka2 on 10/17/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {


    public ImageResultsAdapter(Context context, List<ImageResult> objects) {
        super(context, R.layout.item_image, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResult imageResult = (ImageResult) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image,parent,false);
        }

        ImageView ivResultImage = (ImageView) convertView.findViewById(R.id.ivImage);
        //TextView tvImageTitle = (TextView) convertView.findViewById(R.id.tvImageTitle);
        final ProgressBar pgImageLoad = (ProgressBar) convertView.findViewById(R.id.pgImageLoad);
        pgImageLoad.setVisibility(View.VISIBLE);
        //tvImageTitle.setText(Html.fromHtml(imageResult.getTitle()));
        ivResultImage.setImageResource(0);
        Picasso.with(getContext()).load(imageResult.getFullUrl()).into(ivResultImage, new Callback() {
            @Override
            public void onSuccess() {
                pgImageLoad.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        return convertView;
    }
}
