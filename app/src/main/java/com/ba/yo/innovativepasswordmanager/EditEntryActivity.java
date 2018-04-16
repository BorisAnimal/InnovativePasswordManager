package com.ba.yo.innovativepasswordmanager;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.EditText;
>>>>>>> refs/remotes/origin/master

public class EditEntryActivity extends AppCompatActivity implements EditEntryMVC.View {
    private EditEntryMVC.Controller controller;
    private Button goButton;
    private EditText loginEd;
    private EditText passEd;
    private EditText descEd;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        if(getIntent().hasExtra("ENTRY_ID")){
            setTitle(getString(R.string.edit_entry));
            id = getIntent().getStringExtra("ENTRY_ID");
            Button saveButton = (Button) findViewById(R.id.addButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNotification("Current entry ID: "+id);
                }
            });
        }else{
            setTitle(getString(R.string.add_entry));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
<<<<<<< HEAD

=======
        setTitle(getString(R.string.add_entry));

        goButton = findViewById(R.id.addButton);
        loginEd = findViewById(R.id.loginField);
        passEd = findViewById(R.id.passwordField);
        descEd = findViewById(R.id.descrField);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.commitEntry(loginEd.getText() + "", passEd.getText() + "", descEd.getText() + "");
            }
        });

        controller = new EditEntryController(this);
>>>>>>> refs/remotes/origin/master
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

<<<<<<< HEAD
    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
=======
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }

    @Override
    public void setPassword(String pass) {
        passEd.setText(pass);
    }
>>>>>>> refs/remotes/origin/master
}
