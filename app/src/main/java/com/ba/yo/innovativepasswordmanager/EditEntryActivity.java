package com.ba.yo.innovativepasswordmanager;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EditEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        if(getIntent().hasExtra("ENTRY_ID")){
            setTitle(getString(R.string.edit_entry));
            int id = getIntent().getIntExtra("ENTRY_ID", -1);
        }else{
            setTitle(getString(R.string.add_entry));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
