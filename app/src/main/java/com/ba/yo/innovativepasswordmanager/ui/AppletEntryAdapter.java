package com.ba.yo.innovativepasswordmanager.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.AppletEntryAdapterCallback;
import com.ba.yo.innovativepasswordmanager.R;

import java.util.ArrayList;
import java.util.List;

public class AppletEntryAdapter extends ArrayAdapter<AppletEntry> {
    private Context apContext;
    private List<AppletEntry> appletList = new ArrayList<>();

    public AppletEntryAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<AppletEntry> list) {
        super(context, 0 , list);
        apContext = context;
        appletList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(apContext).inflate(R.layout.list_applet_entry,parent,false);

        final AppletEntry currentEntry = appletList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentEntry.getapImageDrawable());


        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentEntry.getapName());

        return listItem;
    }
}
