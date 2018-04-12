package com.ba.yo.innovativepasswordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class EditMasterPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_master_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Entry");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}