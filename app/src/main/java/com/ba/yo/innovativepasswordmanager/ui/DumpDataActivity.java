package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ba.yo.innovativepasswordmanager.DumpDataMVC;
import com.ba.yo.innovativepasswordmanager.R;

public class DumpDataActivity extends AppCompatActivity implements DumpDataMVC.View {

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

    }

    /**
     *  Return to
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
