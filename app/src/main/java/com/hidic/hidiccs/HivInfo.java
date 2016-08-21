package com.hidic.hidiccs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HivInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiv_info);
    }
    public void view(View v) {
//goes to hivaids map2
        Intent x = new Intent(this, Main2Activity.class);
        startActivity(x);
    }
}
