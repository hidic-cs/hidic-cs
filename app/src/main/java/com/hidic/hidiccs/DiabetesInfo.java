package com.hidic.hidiccs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DiabetesInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_info);
    }

   public void view(View v) {

        Intent x = new Intent(this, Main3Activity.class);
        startActivity(x);
    }
}
