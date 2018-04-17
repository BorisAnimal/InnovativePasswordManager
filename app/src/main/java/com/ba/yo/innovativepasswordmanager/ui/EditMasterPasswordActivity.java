package com.ba.yo.innovativepasswordmanager.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ba.yo.innovativepasswordmanager.R;

public class EditMasterPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_master_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.change_master_password));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
