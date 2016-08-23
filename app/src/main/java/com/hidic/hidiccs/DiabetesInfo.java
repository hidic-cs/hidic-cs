package com.hidic.hidiccs;
//goestomainactivity3
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiabetesInfo extends AppCompatActivity {

    ListView listView;
    String[] diabetesArray = {"Diabetes Control & Prevention", "Manage/Reduce", "Treatments",
            "Advice", "Doctor", "Hospital"};
    List<String> list = new ArrayList<>(Arrays.asList(diabetesArray));
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_info);

        listView = (ListView) findViewById(R.id.diabetes_list_view);

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list_item,
                R.id.list_item_title,
                list);
        listView.setAdapter(arrayAdapter);
    }
    public void view (View v)
    {
        Intent x = new Intent(this, DiabetesMapActivity.class);
        startActivity(x);
    }
}
