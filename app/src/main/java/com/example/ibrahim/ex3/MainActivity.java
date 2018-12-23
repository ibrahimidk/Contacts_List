package com.example.ibrahim.ex3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView name,phone;
    Button insert,search;
    String phoneNumber,userName;
    int visibility;
    List<Contacts> contacts;
    ListView listView;
    SQL_DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name=(TextView) findViewById(R.id.name);
        phone=(TextView) findViewById(R.id.phone);
        insert=(Button) findViewById(R.id.btnInsert);
        search=(Button) findViewById(R.id.btnSearch);

        contacts=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        db=new SQL_DataBase(this,"contacts",listView);

        for(int i=0;i<db.getCount();i++){
            contacts.add(db.getContact(i));

            if(TextUtils.isEmpty(contacts.get(i).getPhone())){
                contacts.get(i).setImg(0);
            }
            else {
                contacts.get(i).setImg(1);
            }

        }

        final CustomListView adapter=new CustomListView(this,R.layout.data_item,contacts);
        listView.setAdapter(adapter);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber=phone.getText().toString();
                userName=name.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(MainActivity.this, "Enter the name pls", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.add(userName,phoneNumber);
                    adapter.clear();
                    for(int i=0;i<db.getCount();i++){
                        contacts.add(db.getContact(i));
                        if(TextUtils.isEmpty(contacts.get(i).getPhone())){
                            contacts.get(i).setImg(0);
                        }
                        else {
                            contacts.get(i).setImg(1);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    phone.setText("");
                    name.setText("");
                }
            }


        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber=phone.getText().toString();
                userName=name.getText().toString();
                adapter.clear();
                if(db.search(userName,phoneNumber)!=null) {
                    contacts.addAll(db.search(userName, phoneNumber));
                }
                for(int i=0;i<contacts.size();i++){
                    if(TextUtils.isEmpty(contacts.get(i).getPhone())){
                        contacts.get(i).setImg(0);
                    }
                    else {
                        contacts.get(i).setImg(1);
                    }
                }

                phone.setText("");
                name.setText("");
            }
        });
    }

}
