package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class Catalog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Plant> plants = new ArrayList<>();
    private Button btnAddNewPlant;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_catalog);
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void createListeners() {
        btnAddNewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.super.getApplicationContext(),
                        AddPlant.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddNewPlant=findViewById(R.id.btn_add_new_plant);
        AgroPol=new DBHelper(Catalog.this);
    }

    private void loadData() {
        //zczytanie danych z bazy danych i dodanie do RecyclerView poprzez dodanie do listy plants
        //Cursor result= AgroPol.getDate("");//Todo Wymyslec selecta


    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogAdapter(plants);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                //po kliknięciu na konkretny item zostanie otworzone okno dialogowe ze szczegółami,
                // z pozycji którego będzie można edytować lub usunąć dany rekord
                // Todo wykonać po stworzeniu intencji dodawania sadzonki do katalogu
            }
        });
    }
}