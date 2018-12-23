package com.example.ibrahim.ex3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQL_DataBase {

    SQLiteDatabase db;
    Context context;

    List<Contacts> contactsList;
    ListView listView;



    public SQL_DataBase(Context context, String str,ListView listView) {
        this.context = context;

        try {
            db = context.openOrCreateDatabase(str, Context.MODE_PRIVATE, null);

            String sql = "CREATE TABLE IF NOT EXISTS contacts (id integer primary key, name VARCHAR, phone VARCHAR);";
            db.execSQL(sql);
        }catch (Exception e){
            Log.d("debug", "Error while Creating Database");
        }
        contactsList=new ArrayList<>();
        this.listView=listView;


    }


    public void add(String name, String phone){

        String CheckIfThere="SELECT name FROM contacts " +
                "WHERE name = \""+name+"\"";
        Cursor cursor =db.rawQuery(CheckIfThere,null);
        cursor.moveToFirst();

        if(cursor.getCount()==0) {
            String sql = "INSERT INTO contacts (name, phone) VALUES ('" + name + "', '" + phone + "');";
            db.execSQL(sql);
        }
        else {
            if(phone !=null){
                ContentValues contentValues=new ContentValues();
                contentValues.put("phone",phone);
                db.update("contacts",contentValues,"NAME =\""+name+"\"",null);
            }
        }
        cursor.close();
    }


    public int getCount(){
        String sql = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public Contacts getContact(int i)
    {
        // A Cursor provides read and write access to database results
        String sql = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(sql, null);

        // Get the index for the column name provided
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int phoneColumn = cursor.getColumnIndex("phone");

        cursor.moveToPosition(i);
        Contacts contacts=null;
        if(cursor != null && (cursor.getCount() > 0)){
            // Get the results and store them in a String
            String id = cursor.getString(idColumn);
            String name = cursor.getString(nameColumn);
            String phone = cursor.getString(phoneColumn);

            contacts=new Contacts(name,phone);
        }

        return contacts;

    }

    public List<Contacts> search(String name,String phone){
        List<Contacts> contacts=new ArrayList<>();

        String sql = "SELECT * FROM contacts "+
                "WHERE name like '%"+name+"%' AND phone like '%"+phone+"%'";
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        int i=cursor.getCount()-1;
            while (i>=0) {
                Contacts contact = new Contacts(cursor.getString(1), cursor.getString(2));
                contacts.add(contact);
                cursor.moveToNext();
                i--;
            }
            cursor.close();

        return contacts;

    }
}
