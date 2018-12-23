package com.example.ibrahim.ex3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomListView extends ArrayAdapter<Contacts> {
    Context context;
    int resource;
    List<Contacts> ContactsList;

    public CustomListView(Context context, int resource, List<Contacts> ContactsList) {
        super(context, resource, ContactsList);
        this.context=context;
        this.resource=resource;
        this.ContactsList=ContactsList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(resource,null);

        TextView name=view.findViewById(R.id.lv_name);
        final TextView phone=view.findViewById(R.id.lv_phone);
        ImageView gray=view.findViewById(R.id.lv_gray);
        ImageView green=view.findViewById(R.id.lv_green);

        final Contacts contacts=ContactsList.get(position);

        name.setText(contacts.getName());
        phone.setText(contacts.getPhone());

        if(contacts.getImg()==1){
            gray.setVisibility(View.GONE);
            green.setVisibility(View.VISIBLE);
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+contacts.getPhone()));
                    context.startActivity(intent);
                }
            });

        }else if(contacts.getImg()==0){
            gray.setVisibility(View.VISIBLE);
            green.setVisibility(View.GONE);
        }


        return view;
    }
}
