package com.ba.yo.innovativepasswordmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements EntitySelectMVC.Login {
    private FloatingActionButton masterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        masterButton = (FloatingActionButton) findViewById(R.id.master_button);
        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFields()){
                    //HERE CALL LOGIN
                    makeTransitionToEntitySelect();
                }else{
                    showNotification("Login and Password fields can not be empty.");
                }
            }
        });
    }
    public boolean checkFields(){
        String login = ((EditText) findViewById(R.id.master_login)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.master_password)).getText().toString().trim();
        return (!login.isEmpty() && !password.isEmpty());
    }

    public void makeTransitionToEntitySelect(){
        Intent intent = new Intent(LoginActivity.this, EntitySelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}