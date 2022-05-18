package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class EmployeeComplaints extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataOfEmployeeComplaintsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfEmployeeComplaints> dataOfEmployeeComplaints = new ArrayList<>();
    private DBHelper AgroPol;

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
        Cursor result= AgroPol.getDate("Select * from complaint where Status not like 'In Make'");
        while(result.isAfterLast()==false)
        {
            int IdClient=result.getInt(1);
            int IdComplaint=result.getInt(0);
            String status=result.getString(5);
            dataOfEmployeeComplaints.add(new DataOfEmployeeComplaints(IdClient,IdComplaint,status));
            result.moveToNext();
        }
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
                Intent intent=new Intent(EmployeeComplaints.super.getApplicationContext(),DetailsOfEmployeeComplaints.class);
                intent.putExtra("IdComplaint",dataOfEmployeeComplaints.get(position).getComplaintId());
                startActivity(intent);
            }
        });


    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(EmployeeComplaints.this);
    }
}