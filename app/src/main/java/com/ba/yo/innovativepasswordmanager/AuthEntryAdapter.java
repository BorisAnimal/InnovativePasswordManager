package com.ba.yo.innovativepasswordmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AuthEntryAdapter extends ArrayAdapter<AuthEntry> {
    private Context aContext;
    private List<AuthEntry> authList = new ArrayList<>();

    public AuthEntryAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<AuthEntry> list) {
        super(context, 0 , list);
        aContext = context;
        authList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(aContext).inflate(R.layout.list_entry,parent,false);

        AuthEntry currentEntry = authList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentEntry.getaImageDrawable());


        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentEntry.getaName());

        Button prop_button = (Button) listItem.findViewById(R.id.prop_button);
        prop_button.set


        return listItem;
    }
}
