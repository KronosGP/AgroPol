package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddPlant extends AppCompatActivity {

    private Spinner howSpecies;
    private ArrayList<AddPlantSpinnerItem> addPlantSpinnerItems;
    private AddPlantSpinnerAdapter adapter;

    private ImageView icon;
    private EditText howVariety, howQuantity, howPrice;
    private Button btnCancel, btnAccept;
    private TextView textViewRow;

    private int[] images =
            {
                    R.drawable.image_pepper
            };
    private String currentSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_plant);
        findViews();
        startSettings();
        createListeners();
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        //dodanie pozycji do bazy danych
                        //powrót do aktywności Katalogu
                        Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                Catalog.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel:
                    {
                        Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                Catalog.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        howSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewRow= view.findViewById(R.id.text_view_row);
                icon.setImageResource(images[position]);
                currentSpecies=textViewRow.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startSettings() {
        addPlantSpinnerItems = new ArrayList<>();
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Papryka",R.drawable.image_pepper));

        adapter=new AddPlantSpinnerAdapter(this,addPlantSpinnerItems);
        howSpecies.setAdapter(adapter);
        currentSpecies="Papryka";
    }

    private void findViews() {
        howSpecies=findViewById(R.id.how_species);
        howVariety=findViewById(R.id.how_variety);
        howQuantity=findViewById(R.id.how_quantity);
        howPrice=findViewById(R.id.how_price);
        icon=findViewById(R.id.icon);
        btnCancel=findViewById(R.id.btn_cancel);
        btnAccept=findViewById(R.id.btn_accept);

    }
}