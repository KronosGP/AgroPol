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
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ClientCatalog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Plant> plants = new ArrayList<>();

    private TextInputEditText howQuantity;
    private TextView attention;
    private Button btnCancel, btnAdd;

    private DBHelper AgroPol;
    private int flag;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_catalog);
        findViews();
        startSettings();
        loadData();
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(ClientCatalog.this);
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
                        Cursor result=AgroPol.getDate("Select ID,Quantity from plant where ID="+plants.get(position).getId());
                        if(Integer.parseInt(result.getString(1))<Integer.parseInt(howQuantity.getText().toString())){
                            attention.setVisibility(View.VISIBLE);
                            attention.setText("Za duża ilość!!!");
                        }
                        else if(Integer.parseInt(howQuantity.getText().toString())<0) {
                            attention.setVisibility(View.VISIBLE);
                            attention.setText("Mniejsze od zera!!!");
                        }
                        else
                        {
                            //AgroPol.setData("details_request");
                            /*Intent intent = new Intent(ClientCatalog.super.getApplicationContext(),
                                    MakeOrder.class);
                            startActivity(intent);*///moje testy Adam
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
            Bundle get=getIntent().getExtras();
            flag=Integer.parseInt(get.getString("flag"));
            Cursor result = AgroPol.getDate("Select * from plant");
            while (result.isAfterLast() == false) {
                plants.add(new Plant(Integer.parseInt(result.getString(0)),result.getString(1), result.getString(2), (long) Integer.parseInt(result.getString(3)), Double.parseDouble(result.getString(4)), Integer.parseInt(result.getString(5))));
                //System.out.println(result.getString(0)+" " +result.getString(1)+" "+ result.getString(2)+" "+  (long) Integer.parseInt(result.getString(3))+" "+  Double.parseDouble(result.getString(4))+" "+  Integer.parseInt(result.getString(5)));
                result.moveToNext();
            }
        }
        catch (SQLiteException ex)
        {
            System.out.println(ex);
        }
    }

}