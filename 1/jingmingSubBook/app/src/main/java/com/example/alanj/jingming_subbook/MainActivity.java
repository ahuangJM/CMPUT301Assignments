package com.example.alanj.jingming_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String xmlFile = "subbook_data.xml";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Confirmed(View view) {
        try {
            SubBook subBook = new SubBook();
            final String name = ((EditText) findViewById(R.id.name)).getText().toString();
            subBook.setName(name);
            final String date = ((EditText) findViewById(R.id.date)).getText().toString();
            subBook.setDate(date);
            final String monthlyCharges = ((EditText) findViewById(R.id.monthlyCharges)).getText().toString();
            subBook.setMonthlyCharges(monthlyCharges);
            final String comment = ((EditText) findViewById(R.id.comment)).getText().toString();
            subBook.setComment(comment);
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);

            SaveSubscription saveSubscription = new SaveSubscription();
            saveSubscription.setFileOutputStream((fileOutputStream));
            saveSubscription.setSubBook(subBook);
            saveSubscription.SaveSub();
        } catch (InputTooLongException e) {
            Context context = getApplicationContext();
            CharSequence text = "Input String Too Long :\nName-20 Characters Max\nComment-30 Characters Max";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberOutOfBoundException e) {
            Context context = getApplicationContext();
            CharSequence text = "Monthly Charges must be a non-negative decimal number";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, DisplaySubsriptionsActivity.class);
        startActivity(intent);
    }
}
