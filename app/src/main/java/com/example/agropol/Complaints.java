package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Complaints extends AppCompatActivity {

    private Button btnAddComplaint;
    private RecyclerView recyclerView;
    private DataOfComplaintsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfComplaints> dataOfComplaints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_complaints);
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void loadData() {
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
                //wyświetlanie szczegółów reklamacji w oknie dialogowym
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
                        Orders.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        btnAddComplaint=findViewById(R.id.btn_add_complaint);
        recyclerView=findViewById(R.id.recycler_view);
    }
}