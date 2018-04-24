package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ba.yo.innovativepasswordmanager.DumpDataMVC;
import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.controllers.DumpDataController;

public class DumpDataActivity extends AppCompatActivity implements DumpDataMVC.View {

    private DumpDataMVC.Controller controller;
    private EditText passwordField;
    private Button dumpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump_data);

        /*
         * Change title on top of activity
         */
        setTitle(getString(R.string.manage_data));
        /*
         * Activate back button on top of activity
         */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*
         * Assign elements from activity to variables
         */
        passwordField = (EditText) findViewById(R.id.password_dump);
        dumpButton = (Button) findViewById(R.id.btn_dump);

        /*
         * Assign handler for dump button
         */
        dumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.makeDump(passwordField.getText().toString());
            }
        });

        //create controller
        controller = new DumpDataController(this);

    }

    /**
     *  Return to previous activity, i.e. EntitySelect
     */
    @Override
    public void goToEntitySelectActivity() {
        finish();
    }

    /**
     *
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }

    /**
     * Handler for "back" button on top of activity
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
