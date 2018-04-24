package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ba.yo.innovativepasswordmanager.controllers.EditEntryController;
import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditEntryActivity extends AppCompatActivity implements EditEntryMVC.View {
    EditEntryMVC.Controller controller;
    @BindView(R.id.generatePasswordButton)
    Button generateRandomPassword;
    @BindView(R.id.addButton)
    Button goButton;
    @BindView(R.id.loginField)
    EditText loginEd;
    @BindView(R.id.passwordField)
    EditText passEd;
    @BindView(R.id.descrField)
    EditText descEd;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        //TODO: check if there could be nullptr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(getIntent().hasExtra("ENTRY_ID")){
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                this.id = extras.getString("ENTRY_ID");
                setTitle(getString(R.string.edit_entry));
                //TODO: fill existing fields
            }

        }else{
            setTitle(getString(R.string.add_entry));
        }

        ButterKnife.bind(this);
        controller = new EditEntryController(this);

        //TODO: Decide using ID whether to create new or edit existing entity
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.commitEntry(loginEd.getText() + "", passEd.getText() + "", descEd.getText() + "");
            }
        });
        generateRandomPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.generateRandomPassword();
            }
        });
    }

    public void setExisting(String username, String password, String description){
        EditText usernameField = (EditText) findViewById(R.id.loginField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        EditText descriptionField = (EditText) findViewById(R.id.descrField);

        usernameField.setText(username);
        passwordField.setText(password);
        descriptionField.setText(description);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

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
}