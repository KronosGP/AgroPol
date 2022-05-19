package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.agropol.MainClasses.Complaint;
import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.R;

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
        createToolbar();
        findViews();
        startSettings();
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
                        finish();
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(EmployeeComplaints.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private void loadData() {
        //wczytanie danych do recyclerView
       /* Cursor result= AgroPol.getDate("Select * from complaint where Status not like 'In Make'");
        while(result.isAfterLast()==false)
        {
            int IdClient=result.getInt(1);
            int IdComplaint=result.getInt(0);
            String status=result.getString(5);
            dataOfEmployeeComplaints.add(new DataOfEmployeeComplaints(IdClient,IdComplaint,status));
            result.moveToNext();
        }*/
        Complaint complaint=new Complaint();
        dataOfEmployeeComplaints=complaint.loadEmployeeComplaint(getApplicationContext(),dataOfEmployeeComplaints);
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
//                intent.putExtra("IdComplaint",dataOfEmployeeComplaints.get(position).getComplaintId());
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("IdComplaint",dataOfEmployeeComplaints.get(position).getComplaintId());
                editor.apply();
                startActivity(intent);
            }
        });


    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(EmployeeComplaints.this);
    }
}