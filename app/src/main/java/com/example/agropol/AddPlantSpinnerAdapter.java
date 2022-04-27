package com.example.agropol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AddPlantSpinnerAdapter extends ArrayAdapter<AddPlantSpinnerItem> {

    public AddPlantSpinnerAdapter(Context context, ArrayList<AddPlantSpinnerItem> List) {
        super(context, 0, List);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_spinner_add_plant_row, parent, false);
        }

        ImageView image = convertView.findViewById(R.id.image_row);
        TextView text = convertView.findViewById(R.id.text_view_row);

        AddPlantSpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            image.setImageResource(currentItem.getImage());
            text.setText(currentItem.getText());
        }

        return convertView;
    }
}

