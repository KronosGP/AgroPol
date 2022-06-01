package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.agropol.MainClasses.Complaint;
import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.R;

import java.util.ArrayList;

public class ClientComplaints extends AppCompatActivity {

    private Button btnAddComplaint;
    private RecyclerView recyclerView;
    private DataOfClientComplaintsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfClientComplaints> dataOfClientComplaints = new ArrayList<>();
    private DBHelper AgroPol;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_complaints);
        createToolbar();
        getSharedPreferences();
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
                        Intent intent = new Intent(ClientComplaints.super.getApplicationContext(),
                                ClientMenu.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(ClientComplaints.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        IdUser = sharedPreferences.getInt("IdUser", 0);
    }

    private void findViews() {
        btnAddComplaint=findViewById(R.id.btn_add_complaint);
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(ClientComplaints.this);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfClientComplaintsAdapter(dataOfClientComplaints);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DataOfClientComplaintsAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                //otwarcie intencji szczegółów reklamacj
                //trzeba wysłać dane z konkretnego itemu do tej reklamacji
                    Intent intent = new Intent(ClientComplaints.super.getApplicationContext(),
                            DetailsOfClientComplaints.class);
                    System.out.println(IdUser + "    " + dataOfClientComplaints.get(position).getId());
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("IdUser",IdUser);
                editor.putInt("IdComplaint",dataOfClientComplaints.get(position).getId());
                editor.apply();
                startActivity(intent);
            }
        });

    }

    private void createListeners() {
        btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wysyłyanie flagi do aktywności świadczącej o otwarciu katalogu Orders w trybie
                //składania reklamacji
                Intent intent = new Intent(ClientComplaints.super.getApplicationContext(),
                        ClientOrders.class);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("IdUser",IdUser);
                editor.putInt("Flag",1);
                editor.apply();
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        //Usuwanie nie dokończonych reklamacji
        AgroPol.delData("complaint","Status like 'In Make' and IDClient="+IdUser);
        //Wczytanie danych z bazy danych do listy
        Complaint complaint=new Complaint();
        dataOfClientComplaints=complaint.loadClientComplaint(getApplicationContext(),dataOfClientComplaints,IdUser);

    }
}