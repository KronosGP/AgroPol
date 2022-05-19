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
import com.example.agropol.MainClasses.Order;
import com.example.agropol.MainClasses.Plant;
import com.example.agropol.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ClientCatalog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PlantItems> plantItems = new ArrayList<>();

    private TextInputEditText howQuantity;
    private TextView attention;
    private Button btnCancel, btnAdd;
    private TextView title;

    private DBHelper AgroPol;
    private int flag;
    private int IdRequest;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_catalog);
        createToolbar();
        findViews();
        startSettings();
        loadData();
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
                        finish();
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(ClientCatalog.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        super.onResume();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        title=findViewById(R.id.title);
        AgroPol=new DBHelper(ClientCatalog.this);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        IdUser = sharedPreferences.getInt("IdUser", 0);
        IdRequest = sharedPreferences.getInt("IdRequest",0);
        flag =sharedPreferences.getInt("flag",0);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogAdapter(plantItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if(flag==1)
            title.setText("Dodaj pozycję");
        else
            title.setText("Katalog sadzonek");

        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                if(flag==1)
                    openHowQuantityDialog(position);
            }
        });
    }

    private void openHowQuantityDialog(int position) {
        Dialog howQuantityWindow = new Dialog(this);
        howQuantityWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        howQuantityWindow.setContentView(R.layout.layout_dialog_how_quantity);
        howQuantityWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        howQuantityWindow.show();
        findHowQuantityWindowDialogViews(howQuantityWindow);
        createAndAddListeners(howQuantityWindow, position);
    }

    private void createAndAddListeners(Dialog howQuantityWindow, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_add:
                    {
                        //walidacja danych w sensie czy liczba sadzonek nie jest liczbą ujemną lub
                        // jest większa od tej w katalogu, jeżeli coś się nie zgadza wyświetlenie
                        //uwagi w textView (attention) a wcześniej jej pokazanie bo domyślnie jest niewidoczna
                        Cursor result=AgroPol.getDate("Select ID,Quantity from plant where ID="+ plantItems.get(position).getId());
                        if(Integer.parseInt(result.getString(1))<Integer.parseInt(howQuantity.getText().toString())){
                            attention.setVisibility(View.VISIBLE);
                            attention.setText("Za duża ilość!!!");
                        }
                        else if(Integer.parseInt(howQuantity.getText().toString())<=0) {
                            attention.setVisibility(View.VISIBLE);
                            attention.setText("Musi być większe od 0!!!");
                        }
                        else
                        {
                            try {
                                Order order=new Order();
                                //Dodanie sadzonki oraz jej ilość do szczegółów zamówienia
                                order.AddDetailsOrder(getApplicationContext(),String.valueOf(IdRequest), String.valueOf(plantItems.get(position).getId()), howQuantity.getText().toString());
                                result=AgroPol.getDate("Select Price from request where ID="+IdRequest);
                                Double cost=Double.parseDouble(result.getString(0))+Integer.parseInt(howQuantity.getText().toString())* plantItems.get(position).getPrice();

                                //zmiana ceny zamówienia
                                order.EditOrder(getApplicationContext(),"ID="+IdRequest,new String[]{"Price"},new String[]{String.valueOf(cost)});
                                int update= (int) (plantItems.get(position).getQuantity()-Integer.parseInt(howQuantity.getText().toString()));

                                //zmiana ilość sztuk w szklarniach
                                Plant plant=new Plant();
                                plant.editPlant(getApplicationContext(),String.valueOf(plantItems.get(position).getId()),plantItems.get(position).getSpecies(),plantItems.get(position).getVariety(),String.valueOf(update),String.valueOf(plantItems.get(position).getPrice()),String.valueOf(plantItems.get(position).getImage()));

                                Intent intent = new Intent(ClientCatalog.super.getApplicationContext(),
                                        MakeOrder.class);
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("IdUser",IdUser);
                                editor.apply();
                                startActivity(intent);//moje testy Adam
                            }
                            catch (Exception ex)
                            {
                                System.out.println(ex);
                            }
                        }


                    }break;
                    case R.id.btn_cancel:
                    {
                        howQuantityWindow.dismiss();
                    }break;
                }
            }
        };
        btnCancel.setOnClickListener(listener);
        btnAdd.setOnClickListener(listener);
    }

    private void findHowQuantityWindowDialogViews(Dialog howQuantityWindow) {
        howQuantity=howQuantityWindow.findViewById(R.id.how_quantity);
        attention=howQuantityWindow.findViewById(R.id.attention);
        btnCancel=howQuantityWindow.findViewById(R.id.btn_cancel);
        btnAdd=howQuantityWindow.findViewById(R.id.btn_add);
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

}