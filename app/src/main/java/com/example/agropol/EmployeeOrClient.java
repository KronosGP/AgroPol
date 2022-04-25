package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeOrClient extends AppCompatActivity {

    private ConstraintLayout btnEmployee, btnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_or_client);
        findViews();
        createListeners();
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_employee:
                    {
                        Intent intent = new Intent(EmployeeOrClient.super.getApplicationContext(),
                                                   EmployeeSignIn.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_client:
                    {
                        Intent intent = new Intent(EmployeeOrClient.super.getApplicationContext(),
                                ClientSignIn.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnEmployee.setOnClickListener(listener);
        btnClient.setOnClickListener(listener);
    }

    private void findViews() {
        btnEmployee=findViewById(R.id.btn_employee);
        btnClient=findViewById(R.id.btn_client);
    }
}