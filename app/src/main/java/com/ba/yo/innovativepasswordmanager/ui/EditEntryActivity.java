package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
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

    private String entityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        /*
         * Activate back button on top of activity
         */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
         * Check presence of any extras
         * Their presence would mean that the entity with given ID should be edited
         * Otherwise new entity should be created
         */
        if(getIntent().hasExtra("ENTRY_ID")){
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                entityId = extras.getString("ENTRY_ID");
                setTitle(getString(R.string.edit_entry));
                
                controller = new EditEntryController(this, entityId);
            }

        }else{
            setTitle(getString(R.string.add_entry));
            controller = new EditEntryController(this);
        }

        ButterKnife.bind(this);

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

    /**
     * Fill field in current activity fields
     * @param username - value to fill in username field
     * @param password - value to fill in password field
     * @param description - value to fill in description field
     */
    public void setExisting(String username, String password, String description){
        EditText usernameField = (EditText) findViewById(R.id.loginField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        EditText descriptionField = (EditText) findViewById(R.id.descrField);

        usernameField.setText(username);
        passwordField.setText(password);
        descriptionField.setText(description);
    }

    /**
     * Handler for "back" button on top of activity
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        goToEntitySelectActivity();
        return true;
    }

    /**
     *
     */
    @Override
    public void goToEntitySelectActivity() {
        finish();
    }

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
        //TODO: improve fab button animation
    }

    /**
     * Assign value to password field
     * @param pass - target value
     */
    @Override
    public void setPassword(String pass) {
        passEd.setText(pass);
    }
}