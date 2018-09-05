package com.shivanigb.bmicalculator;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    ListView lvDisp;
    String date;
    String bmi;
    String time;
    ScrollView ScrView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lvDisp = (ListView) findViewById(R.id.lvDisp);
        ScrView=(ScrollView)findViewById(R.id.ScrView);

        DatabaseHandler db = new DatabaseHandler(this);
        final Context context;
        final String[] records = db.getRecords();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>  (this, android.R.layout.simple_list_item_1, records);

        lvDisp.setAdapter(adapter);


    }
}

