package com.ba.yo.innovativepasswordmanager.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.Transition;
import com.ba.yo.innovativepasswordmanager.controllers.LoginController;
import com.ba.yo.innovativepasswordmanager.LoginMVC;
import com.ba.yo.innovativepasswordmanager.R;

import org.apache.commons.lang3.ObjectUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginMVC.View {
    @BindView(R.id.master_button)
    FloatingActionButton masterButton;
    @BindView(R.id.master_login)
    EditText loginEdit;
    @BindView(R.id.master_password)
    EditText passwordEdit;
    private TextView mainLabel;
    private TextView registerLabel;
    private LoginMVC.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Bind all elements that annotated above
        ButterKnife.bind(this);

        controller = new LoginController(this);

        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                controller.check(login, password);
            }
        });

        /*
         * Setup text on page
         */
        mainLabel = (TextView) findViewById(R.id.app_label);
        registerLabel = (TextView) findViewById(R.id.btn_create_account);
        mainLabel.setText(Html.fromHtml(getString(R.string.welcome_label)));
        registerLabel.setText(Html.fromHtml(getString(R.string.sign_up_message)));

        registerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity();
            }
        });
    }
    /**
     * Proceed to main activity, i.e. EntitySelect
     */
    public void goToEntitySelectActivity() {
        Intent intent = new Intent(LoginActivity.this, EntitySelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Open registration form, that is needed for new users
     */
    private void goToRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterAccountActivity.class);
        startActivityForResult(intent, 0);
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
     * Handle return values from register activity
     * @param data intent that was constructed in register activity, contains parameters
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            try{
                String returnLogin = data.getStringExtra("LOGIN");
                String returnPass = data.getStringExtra("PASSWORD");
                setExisting(returnLogin, returnPass);
            }catch (NullPointerException e){
                Log.d("ACTIVITY_TRANSITION","Register Activity return null data.");
            }
        }else if(resultCode==RESULT_CANCELED){
            Log.d("ACTIVITY_TRANSITION", "Registration cancelled");
        }
    }

    /**
     * Set existing values in activity
     * @param login login value
     * @param pasword password value
     */
    @Override
    public void setExisting(String login, String pasword) {
        loginEdit.setText(login);
        passwordEdit.setText(pasword);
    }
}
