package com.example.alanj.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                final EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();
                i.putExtra("com.example.alanj.myfirstapp.MESSAGE", message);
                startActivity(i);
            }
        });
    }
}
