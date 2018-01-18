package com.example.alanj.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        String message = getIntent().getStringExtra("com.example.alanj.myfirstapp.MESSAGE");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }
}
