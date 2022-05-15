package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class EmployeeComplaints extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataOfEmployeeComplaintsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfEmployeeComplaints> dataOfEmployeeComplaints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_complaints);
        findViews();
        startSettings();
        loadData();
    }

    private void loadData() {
        //wczytanie danych do recyclerView
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfEmployeeComplaintsAdapter(dataOfEmployeeComplaints);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DataOfEmployeeComplaintsAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                //wyświetlenie szczegółów reklamacji
            }
        });


    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
    }
}