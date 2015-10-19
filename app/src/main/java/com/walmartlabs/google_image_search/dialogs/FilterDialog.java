package com.walmartlabs.google_image_search.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.walmartlabs.google_image_search.R;
import com.walmartlabs.google_image_search.models.ImageFilter;

import org.w3c.dom.Text;

/**
 * Created by akulka2 on 10/17/15.
 */
public class FilterDialog extends DialogFragment {

    private FilterDialogEventListener caller;
    private Spinner imageSizeValues;
    private Spinner imageColorValues;
    private Spinner imageTypeValues;
    private EditText filterSites;
    private ImageFilter imageFilter;

    private static final String[] image_size_array ={"none","small","medium","large","xlarge"};
    private static final String[] image_color_array ={"none","black","blue","brown","gray","green","orange","pink","purple","red","teal","white","yellow"};
    private static final String[] image_type_array = {"none","face","photo","clipart","lineart"};

    public FilterDialog(){
    }

    public static FilterDialog newInstance(FilterDialogEventListener caller, String title, ImageFilter filter) {
        FilterDialog fragment = new FilterDialog();
        fragment.caller = caller;
        fragment.imageFilter = filter;
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public interface FilterDialogEventListener{
        public void cancelFilters();
        public void saveFilters(ImageFilter filter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_filters, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        // Fetch arguments from bundle and set title
        setUpViews(view);
        setFilterValues(imageFilter);
       // getDialog().getWindow().setSoftInputMode(
        //        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ((Button) view.findViewById(R.id.btnSaveFilter)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.

                ImageFilter filter = createImageFilter();
                dismiss();
                caller.saveFilters(filter);
            }
        });
        ((Button) view.findViewById(R.id.btnCancelFilter)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, dismiss this dialog by clearing out the filters
                dismiss();
                caller.cancelFilters();
            }
        });
    }

    private ImageFilter createImageFilter(){
        ImageFilter filter = new ImageFilter();
        String size = (String)imageSizeValues.getSelectedItem().toString();
        if(!size.equalsIgnoreCase("none")){
            filter.setSize(size);
        }
        String color = (String) imageColorValues.getSelectedItem().toString();
        if(!color.equalsIgnoreCase("none")){
            filter.setColor(color);
        }
        String type = (String) imageTypeValues.getSelectedItem().toString();
        if(!type.equalsIgnoreCase("none")){
            filter.setType(type);
        }
        filter.setSites(filterSites.getText().toString());
        return  filter;
    }

    private void setUpViews(View view){
        imageSizeValues = (Spinner) view.findViewById(R.id.spImageSizeValues);
        imageColorValues = (Spinner) view.findViewById(R.id.spImageColorValues);
        imageTypeValues = (Spinner) view.findViewById(R.id.spImageTypeValues);
        filterSites = (EditText) view.findViewById(R.id.etFilterSite);
    }

    private void setFilterValues(ImageFilter imageFilter){

        if(!TextUtils.isEmpty(imageFilter.getType())) {
            for (int i = 0; i < image_type_array.length; i++) {
                if (image_type_array[i].equals(imageFilter.getType())) {
                    imageTypeValues.setSelection(i);
                    break;
                }
            }
        }
        if(!TextUtils.isEmpty(imageFilter.getColor())) {
            for (int i = 0; i < image_color_array.length; i++) {
                if (image_color_array[i].equals(imageFilter.getColor())) {
                    imageColorValues.setSelection(i);
                    break;
                }
            }
        }
        if(!TextUtils.isEmpty(imageFilter.getSize())) {
            for (int i = 0; i < image_size_array.length; i++) {
                if (image_size_array[i].equals(imageFilter.getSize())) {
                    imageSizeValues.setSelection(i);
                    break;
                }
            }
        }
        if(!TextUtils.isEmpty(imageFilter.getSites())){
            filterSites.setText(imageFilter.getSites());
        }
    }
}
