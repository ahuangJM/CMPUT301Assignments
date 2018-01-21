package com.example.alanj.jingming_subbook;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DisplaySubsriptionsActivity extends AppCompatActivity {
    private final String xmlFile = "subbook_data.xml";
    private ArrayList<SubBook> subBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subsriptions);
        try {
            GridLayout mainLayout = (GridLayout) this.findViewById(R.id.mainLayout);
            FileInputStream fileInputStream = getApplicationContext().openFileInput(xmlFile);
            FetchSubscriptions fetchSubscriptions = new FetchSubscriptions();
            fetchSubscriptions.setFileInputStream(fileInputStream);
//            subBooks = fetchSubscriptions.getSubs(); // -- need to run on a separate thread
//            for (SubBook subBook : subBooks) {
//                Log.i("message","we do have a row!");
//                TableRow dummyRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.subscription_row_template, null);
//                TextView name = (TextView) dummyRow.getChildAt(0);
//                name.setText(subBook.getName());
//                TextView date = (TextView) dummyRow.getChildAt(1);
//                date.setText(subBook.getDate());
//                TextView monthlyCharges = (TextView) dummyRow.getChildAt(1);
//                monthlyCharges.setText(subBook.getMonthlyCharges());
//                mainTable.addView(dummyRow);
//            }
            TextView dummyTextView1 = new TextView(this);
            dummyTextView1.setLayoutParams(new ViewGroup.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            dummyTextView1.setText("name");
            TextView dummyTextView2 = new TextView(this);
            dummyTextView2.setLayoutParams(new ViewGroup.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            dummyTextView2.setText("date");
            TextView dummyTextView3 = new TextView(this);
            dummyTextView3.setLayoutParams(new ViewGroup.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            dummyTextView3.setText("alot");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mainLayout.addView(dummyTextView1, new GridLayout.LayoutParams(
                        GridLayout.spec(0, GridLayout.CENTER, 1.0f),
                        GridLayout.spec(1, GridLayout.CENTER, 1.0f)));
                mainLayout.addView(dummyTextView2, new GridLayout.LayoutParams(
                        GridLayout.spec(0, GridLayout.CENTER),
                        GridLayout.spec(2, GridLayout.CENTER)));
                mainLayout.addView(dummyTextView3, new GridLayout.LayoutParams(
                        GridLayout.spec(0, GridLayout.CENTER),
                        GridLayout.spec(3, GridLayout.CENTER)));
            }
//            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
