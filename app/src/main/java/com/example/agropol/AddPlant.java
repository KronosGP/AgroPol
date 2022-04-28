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

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class AddPlant extends AppCompatActivity {

    private Spinner howSpecies;
    private ArrayList<AddPlantSpinnerItem> addPlantSpinnerItems;
    private AddPlantSpinnerAdapter adapter;
    private DBHelper AgroPol;
    private ImageView icon;
    private EditText howVariety, howQuantity, howPrice;
    private Button btnCancel, btnAccept;
    private TextView textViewRow;

    private String currentImage;

    private int[] images =
            {
                    R.drawable.image_pepper, R.drawable.image_beans, R.drawable.image_aubergine,
                    R.drawable.image_cabbagepekin, R.drawable.image_cucumber, R.drawable.image_carrot,
                    R.drawable.image_parsley, R.drawable.image_pumkin, R.drawable.image_radish, R.drawable.image_tomato
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

                        String[] col={"Species","Variety","Quantity","Price","Image"};
                        String[] value={currentSpecies,howVariety.getText().toString(),howQuantity.getText().toString(),howPrice.getText().toString(),currentImage};
                        AgroPol.setData("plant",col,value);//wpisanie danych do bazy

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
                currentImage=Integer.toString(images[position]);
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
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Fasola",R.drawable.image_beans));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Bakłażan",R.drawable.image_aubergine));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Kapusta Pekińska",R.drawable.image_cabbagepekin));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Ogórek",R.drawable.image_cucumber));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Marchewka",R.drawable.image_carrot));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Pietruszka",R.drawable.image_parsley));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Dynia",R.drawable.image_pumkin));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Rzodkiewka",R.drawable.image_radish));
        addPlantSpinnerItems.add(new AddPlantSpinnerItem("Pomidor",R.drawable.image_tomato));

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
        AgroPol=new DBHelper(AddPlant.this);

    }
}