package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class Catalog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Plant> plants = new ArrayList<>();
    private Button btnAddNewPlant;
    private DBHelper AgroPol;

    //---------------------------SHOW WINDOWS VIEWS--------------------------------//

    private TextView howSpecies, howVariety, howQuantity, howPrice;
    private ImageView imageOfVegetable;
    private Button btnEdit, btnDelete;

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
        try {
            Cursor result = AgroPol.getDate("Select * from plant");
            while (result.isAfterLast() == false) {
                plants.add(new Plant(result.getString(1), result.getString(2), (long) Integer.parseInt(result.getString(3)), Double.parseDouble(result.getString(4)), Integer.parseInt(result.getString(5))));
                result.moveToNext();
            }


        }
        catch (SQLiteException ex)
        {
            System.out.println(ex);
        }

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
                openDialogWindow(position);
            }
        });
    }

    private void openDialogWindow(int position) {
        Dialog showWindow = new Dialog(this);
        showWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        showWindow.setContentView(R.layout.layout_dialog_window_in_catalog);
        showWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showWindow.show();
        setNames(position);
        findAddItemDialogViews(showWindow);
        createAndAddListeners(showWindow, position);
    }

    private void setNames(int position) {
        //ustawienie danych w oknie dialogowym danych z pozycji w którą klikneliśmy
    }

    private void createAndAddListeners(Dialog showWindow, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_edit:
                    {
                        //spakowanie danych w paczkę i wysłanie do aktywności Addplant w calu ich wyświetlenia
                        //i ewentualnej edycji
                        Intent intent = new Intent(Catalog.super.getApplicationContext(),
                                AddPlant.class);
                        startActivity(intent);

                    }break;
                    case R.id.btn_delete:
                    {
                        //usunięcie z bazy danych
                        showWindow.dismiss();
                    }break;
                }
            }
        };
        btnEdit.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);
    }

    private void findAddItemDialogViews(Dialog showWindow) {
        howSpecies=showWindow.findViewById(R.id.how_species);
        howVariety=showWindow.findViewById(R.id.how_variety);
        howQuantity=showWindow.findViewById(R.id.how_quantity);
        howPrice=showWindow.findViewById(R.id.how_price);
        imageOfVegetable=showWindow.findViewById(R.id.image_of_vegetable);
        btnEdit=showWindow.findViewById(R.id.btn_edit);
        btnDelete=showWindow.findViewById(R.id.btn_delete);
    }
}