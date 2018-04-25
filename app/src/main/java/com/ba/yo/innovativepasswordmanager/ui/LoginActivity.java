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
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.Transition;
import com.ba.yo.innovativepasswordmanager.controllers.LoginController;
import com.ba.yo.innovativepasswordmanager.LoginMVC;
import com.ba.yo.innovativepasswordmanager.R;

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
     * Show notification in the bottom of activity as a "Snackbar"
     * @param notificationText - string that user should read
     */
    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
