package com.ba.yo.innovativepasswordmanager.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.RegisterAccountMVC;
import com.ba.yo.innovativepasswordmanager.controllers.RegisterAccountController;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterAccountActivity extends AppCompatActivity implements RegisterAccountMVC.View {

    // UI references.
    private TextView mLoginView;
    private EditText mPasswordView;
    private RegisterAccountMVC.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        /*
         * Activate back button on top of activity
         */
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.register_account));

        // Set up the login form.
        mLoginView = (TextView) findViewById(R.id.login_register);
        mPasswordView = (EditText) findViewById(R.id.pass_register);

        /*
         * Setup handler for button
         */
        Button mRegisterButton = (Button) findViewById(R.id.sign_up_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.registerAccount();
            }
        });

        /*
         * Create controller
         */
        controller = new RegisterAccountController(this);
    }

    /**
     * Get value from login field
     * @return String value of login
     */
    public String getLogin(){
        return mLoginView.getText().toString();
    }

    /**
     * Get value from password field
     * @return String value of password
     */
    public String getPassword(){
        return mPasswordView.getText().toString();
    }

    /**
     * Return to previous activity, i.e. LoginActivity
     */
    public void returnToLoginScreen(String login, String password) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("LOGIN", login);
        returnIntent.putExtra("PASSWORD", password);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     * @param notificationText - string that user should read
     */
    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }

    /**
     * Handler for "back" button on top of activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}

