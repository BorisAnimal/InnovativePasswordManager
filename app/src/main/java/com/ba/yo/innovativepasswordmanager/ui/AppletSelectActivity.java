package com.ba.yo.innovativepasswordmanager.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.AppletSelectMVC;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.controllers.AppletSelectController;

import java.util.ArrayList;

public class AppletSelectActivity extends AppCompatActivity implements AppletSelectMVC.View {

    private ListView listView;
    private ImageView iconEmpty;
    private TextView messageEmpty;
    private TextView messageEmptyCopy;
    private RelativeLayout messageWrapper;

    private ArrayList<AppletEntry> appletList;
    private AppletEntryAdapter apAdapter;
    private AppletSelectMVC.Controller controller;
    private String entityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applet_select);

        //Set up action bar on top of activity: enable "back" button and set title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.select_applet));

        // Check extras and get entity ID from previous activity
        if (getIntent().hasExtra("ENTRY_ID")) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.entityId = extras.getString("ENTRY_ID");
            }
        }

        listView = (ListView) findViewById(R.id.applet_selector);

        // Add handler for applet entry clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                String appletId = appletList.get(i).getapId();
                controller.sendDataToApplet(appletId, entityId);
                showNotification("We have Entity: " + entityId + ", and Applet: " + appletId);
            }
        });

        //Get references from activity
        messageEmpty = (TextView) findViewById(R.id.applet_message_empty);
        messageEmptyCopy = (TextView) findViewById(R.id.applet_message_empty_copy);
        messageWrapper = (RelativeLayout) findViewById(R.id.applet_message_wrapper);

        // Add handlers for applet download links
        messageEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkBrowser();
            }
        });

        messageEmptyCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkClipboard();
            }
        });

        // Assign key containers of activity
        appletList = new ArrayList<>();

        iconEmpty = (ImageView) findViewById(R.id.applet_empty_img);
        messageEmpty.setText(Html.fromHtml("<big><b>No applets found</b><br><u>Click here to open site</u></big>"));
        messageEmptyCopy.setText(Html.fromHtml("<big><b>or</b><br><u>Here to copy link</u></big>"));

        //create controller
        controller = new AppletSelectController(this);
        controller.getData();
    }

    /**
     * Show or hide "No entries" message, used when entry list is empty. So user would know that there is no errors.
     *
     * @param state boolean value; True for visisble, False for invisible
     */
    private void setEmptyMessageNotificationVisibility(boolean state) {
        int value = state ? View.VISIBLE : View.GONE;
        messageWrapper.setVisibility(value);
        iconEmpty.setVisibility(value);
        messageEmpty.setVisibility(value);
        messageEmptyCopy.setVisibility(value);
    }

    /**
     * Open applet download link in default browser
     */
    private void linkBrowser() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.applet_link)));
        startActivity(browserIntent);
    }

    /**
     * Copy applet download link to clipboard
     */
    private void linkClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.applet_download_link), getString(R.string.applet_link));
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            showNotification(getString(R.string.link_copied));
        }
    }

    /**
     * Procedure that is called when activity becomes visible after being covered by some editor-activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.getData();
    }

    /**
     * Update current list in activity
     */
    private void updateList() {
        apAdapter = new AppletEntryAdapter(this, appletList);
        listView.setAdapter(apAdapter);
        setEmptyMessageNotificationVisibility(apAdapter.isEmpty());
    }

    /**
     * Add applet entry to list in activity
     *
     * @param description - description of applet
     * @param id          - hide it in element (such in 'tag' field). Use it in intent when item selected
     */
    @Override
    public void addApplet(String description, String id) {
        appletList.add(new AppletEntry(R.drawable.ic_desktop, description, id));
        updateList();
    }

    /**
     * Delete all elements from applet list in activity
     */
    @Override
    public void clearList() {
        appletList = new ArrayList<>();
        updateList();
    }


    /**
     * Close current activity and go to previous, i.e.
     * to EntitySelectActivity
     */
    @Override
    public void goToEntitySelectActivity() {
        finish();
    }

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     *
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
        //TODO: Fab intersection avoidance
    }

    /**
     * Handler for "back" button on top of activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
