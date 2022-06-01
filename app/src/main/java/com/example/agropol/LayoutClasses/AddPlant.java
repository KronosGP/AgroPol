package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.MainClasses.Plant;
import com.example.agropol.R;

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
    private int editOrNew=0;
    private String currentImage;
    private String idOfPlant;
    private String currentSpecies;
    private int[] images =
            {
                    R.drawable.image_pepper, R.drawable.image_beans, R.drawable.image_aubergine,
                    R.drawable.image_cabbagepekin, R.drawable.image_cucumber, R.drawable.image_carrot,
                    R.drawable.image_parsley, R.drawable.image_pumkin, R.drawable.image_radish, R.drawable.image_tomato
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_plant);
        createToolbar();
        findViews();
        startSettings();
        createListeners();
        setInformation();
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        super.onResume();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void createToolbar() {
        ImageView btnBack=findViewById(R.id.btn_back);
        ImageView btnLogout=findViewById(R.id.btn_logout);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= v.getId();
                switch (id)
                {
                    case R.id.btn_back:
                    {
                        Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                EmployeeCatalog.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
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

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        if(howVariety.getText().toString().compareTo("")==0 || howQuantity.getText().toString().compareTo("")==0 ||
                                howPrice.getText().toString().compareTo("")==0)
                            Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola!", Toast.LENGTH_LONG).show();
                        else {
                            //dodanie pozycji do bazy danych chyba raczej bez żadnej walidacji danych
                            //
                            if (editOrNew == 0) {
                                Plant plant = new Plant(currentSpecies,howVariety.getText().toString(), Integer.parseInt(howQuantity.getText().toString()), Double.parseDouble(howPrice.getText().toString()), Integer.parseInt(currentImage) );
                                plant.addPlant(getApplicationContext());
                            }//wpisanie danych do bazy
                            else {
                                Plant plant = new Plant(currentSpecies,howVariety.getText().toString(), Integer.parseInt(howQuantity.getText().toString()), Double.parseDouble(howPrice.getText().toString()), Integer.parseInt(currentImage));
                                plant.editPlant(getApplicationContext(), idOfPlant);
                            }//edycja bazy danych

                            //powrót do aktywności Katalogu
                            Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                    EmployeeCatalog.class);
                            startActivity(intent);
                        }
                    }break;
                    case R.id.btn_cancel:
                    {
                        Intent intent = new Intent(AddPlant.super.getApplicationContext(),
                                EmployeeCatalog.class);
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

    private void setInformation() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("species","").equals("")==false)
        {
            editOrNew=1;
            currentSpecies=sharedPreferences.getString("species","");
            for(int i=0;i<howSpecies.getAdapter().getCount();i++) {
                if (addPlantSpinnerItems.get(i).getText().contains(currentSpecies))
                    howSpecies.setSelection(i);
            }
            howVariety.setText(sharedPreferences.getString("variety",""));
            howPrice.setText(sharedPreferences.getString("price",""));
            howQuantity.setText(sharedPreferences.getString("quantity",""));
            idOfPlant=sharedPreferences.getString("id","");
            howVariety.setEnabled(false);
        }
    }


}