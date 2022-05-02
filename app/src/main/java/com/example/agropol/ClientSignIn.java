package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agropol.DBHelper.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ClientSignIn extends AppCompatActivity {

    private TextInputEditText login, password;
    private Button btnLogIn;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_sign_in);
        findViews();
        createListeners();
        AgroPol.showAllColumnsName();
    }

    private void createListeners() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(Logowanie()==true) {
                    //Todo dodać intencje
                Intent intent = new Intent(ClientSignIn.super.getApplicationContext(),
                                           ClientMenu.class);
                startActivity(intent);
                //}
            }
        });
    }

    private boolean Logowanie() {
        try {
            Cursor result=AgroPol.getDate("Select Count(*) from klient where Login like '"+login.getText().toString()+"' and Haslo like '"+password.getText().toString()+"';");
            if(Integer.parseInt(result.getString(0))==1)
                return true;
        }
        catch (SQLiteException ex)
        {
            System.out.println(ex);
        }
        return false;
    }

    private void findViews() {
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        btnLogIn=findViewById(R.id.btn_log_in);
        AgroPol = new DBHelper(ClientSignIn.this);
    }
}