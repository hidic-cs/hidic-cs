package com.hidic.hidiccs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HivInfo extends AppCompatActivity {

    // This is a list view
    ListView listView;
    String[] listItems = {"Prevention", "Manage HIV", "Advice", "Doctor", "Hospital",
            "Treatment"};
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<>(Arrays.asList(listItems));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiv_info);

        listView = (ListView) findViewById(R.id.hiv_list_view);

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list_item,
                R.id.list_item_title,
                list);
        listView.setAdapter(arrayAdapter);

    }
}
