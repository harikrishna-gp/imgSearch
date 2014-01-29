package com.imgsearch;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.loopj.android.image.SmartImageView;


public class imageResultsArrayAdapter extends ArrayAdapter<imgSrchResult> {

	public imageResultsArrayAdapter(Context context, List<imgSrchResult> images) {
		super(context,R.layout.image_view, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		imgSrchResult imageInfo = getItem(position);
		SmartImageView ivImage;
         
        View view = convertView;
        if (view == null) {
        	LayoutInflater inflater = LayoutInflater.from(getContext());
            ivImage = (SmartImageView) inflater.inflate(R.layout.image_view, parent, false);
        }
        else {
            ivImage = (SmartImageView) convertView;
            ivImage.setImageResource(android.R.color.transparent);
    }
    ivImage.setImageUrl(imageInfo.getThumbUrl());
    return ivImage;
	}

}
