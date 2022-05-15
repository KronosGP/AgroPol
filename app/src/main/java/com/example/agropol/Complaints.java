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

public class Complaints extends AppCompatActivity {

    private Button btnAddComplaint;
    private RecyclerView recyclerView;
    private DataOfComplaintsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfComplaints> dataOfComplaints = new ArrayList<>();

    private DBHelper AgroPol;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_complaints);
        Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void loadData() {
        AgroPol.delData("complaint","Status like 'In Make' and IDClient="+IdUser);
        Cursor result=AgroPol.getDate("Select * from complaint where IDClient="+IdUser);
        while (result.isAfterLast()==false)
        {
            dataOfComplaints.add(new DataOfComplaints(result.getInt(0),result.getString(5)));
            result.moveToNext();
        }

    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfComplaintsAdapter(dataOfComplaints);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DataOfComplaintsAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                //otwarcie intencji szczegółów reklamacj
                //trzeba wysłać dane z konkretnego itemu do tej reklamacji
                    Intent intent = new Intent(Complaints.super.getApplicationContext(),
                            DetailsOfClientComplaints.class);
                    intent.putExtra("IdUser", IdUser);
                    intent.putExtra("IdComplaint", dataOfComplaints.get(position).getId());
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
                Intent intent = new Intent(Complaints.super.getApplicationContext(),
                        ClientOrders.class);
                intent.putExtra("IdUser",IdUser);
                intent.putExtra("Flag",1);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        btnAddComplaint=findViewById(R.id.btn_add_complaint);
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(Complaints.this);
    }
}