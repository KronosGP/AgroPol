package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MakeComplaint extends AppCompatActivity {

    private TextView titleOfComplaintId, titleOfOrderID;
    private TextView name, surname, adress, email, number;
    private EditText howComplaint;
    private Button btnAddComplaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_make_complaint);
        findViews();
        createListeners();
        loadData();
    }

    private void loadData() {
        // wczytanie id reklamacji, id zamówienia oraz danych osobowych
    }

    private void createListeners() {
        btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //walidacja treści reklamacji, czy oby nie jest pusta, po czym dodanie do bazy danych oraz
                //powrót do aktywności z listą reklamacji

            }
        });
    }

    private void findViews() {
        titleOfComplaintId=findViewById(R.id.title_of_complaint_id);
        titleOfOrderID=findViewById(R.id.title_of_order_id);
        name=findViewById(R.id.name);
        surname=findViewById(R.id.surname);
        adress=findViewById(R.id.adress);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        howComplaint=findViewById(R.id.how_complaint);
        btnAddComplaint=findViewById(R.id.btn_add_complaint);
    }
}