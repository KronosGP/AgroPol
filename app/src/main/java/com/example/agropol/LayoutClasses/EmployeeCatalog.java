package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.MainClasses.Plant;
import com.example.agropol.R;

import java.util.ArrayList;

public class EmployeeCatalog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PlantItems> plantItems = new ArrayList<>();
    private Button btnAddNewPlant;
    private DBHelper AgroPol;

    //---------------------------SHOW WINDOWS VIEWS--------------------------------//

    private TextView  howVariety, howQuantity, howPrice;
    private ImageView imageOfVegetable;
    private Button btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_catalog);
        createToolbar();
        findViews();
        startSettings();
        createListeners();
        loadData();
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
                        Intent intent = new Intent(EmployeeCatalog.super.getApplicationContext(),
                                EmployeeMenu.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(EmployeeCatalog.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddNewPlant=findViewById(R.id.btn_add_new_plant);
        AgroPol=new DBHelper(EmployeeCatalog.this);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogAdapter(plantItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                openDialogWindow(position);
            }
        });
    }

    private void createListeners() {
        btnAddNewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeCatalog.super.getApplicationContext(),
                        AddPlant.class);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("species","");
                editor.apply();
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        try {
            Plant plant = new Plant();
            plantItems=plant.loadPlants(getApplicationContext(),plantItems);

        }
        catch (SQLiteException ex)
        {
            System.out.println(ex);
        }


    }

    private void openDialogWindow(int position) {
        Dialog showWindow = new Dialog(this);
        showWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        showWindow.setContentView(R.layout.layout_dialog_window_in_catalog);
        showWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showWindow.show();
        findAddItemDialogViews(showWindow);
        setNames(position);
        createAndAddListeners(showWindow, position);
    }

    private void setNames(int position) {
        //ustawienie danych w oknie dialogowym danych z pozycji w którą klikneliśmy
        try {
            Cursor result = AgroPol.getDate("select * from plant where id=" + plantItems.get(position).getId());
            imageOfVegetable.setImageResource(Integer.parseInt(result.getString(5)));
            howVariety.setText("Odmiana: " + result.getString(2));
            howQuantity.setText("Ilość dostępnych sztuk:\n" + result.getString(3));
            howPrice.setText("Cena:\n"+result.getString(4)+ " zł");
            System.out.println(result.getString(0)+" " +result.getString(1)+" "+ result.getString(2)+" "+  (long) Integer.parseInt(result.getString(3))+" "+  Double.parseDouble(result.getString(4))+" "+  Integer.parseInt(result.getString(5)));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
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
                        Cursor result = AgroPol.getDate("select * from plant where id=" + plantItems.get(position).getId());
                        Intent intent = new Intent(EmployeeCatalog.super.getApplicationContext(),
                                AddPlant.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("variety",result.getString(2));
                        editor.putString("species",result.getString(1));
                        editor.putString("quantity",result.getString(3));
                        editor.putString("price",result.getString(4));
                        editor.putString("image",result.getString(5));
                        editor.putString("id",result.getString(0));
                        editor.apply();
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
        howVariety=showWindow.findViewById(R.id.how_variety);
        howQuantity=showWindow.findViewById(R.id.how_quantity);
        howPrice=showWindow.findViewById(R.id.how_price);
        imageOfVegetable=showWindow.findViewById(R.id.image_of_vegetable);
        btnEdit=showWindow.findViewById(R.id.btn_edit);
        btnDelete=showWindow.findViewById(R.id.btn_delete);
    }
}