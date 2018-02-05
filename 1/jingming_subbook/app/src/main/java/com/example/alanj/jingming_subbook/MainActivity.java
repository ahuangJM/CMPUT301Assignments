package com.example.alanj.jingming_subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * https://stackoverflow.com/questions/7927855/how-to-read-line-by-line-in-android
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * Created by AlanJ on 2018-02-04.
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> subscriptionArrayAdapter;
    private ListView mainListView;
    private final String book = "jingming_subbook.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.subscriptions = new ArrayList<>();
        this.subscriptionArrayAdapter = new ArrayAdapter<>(this, R.layout.generic_list_item, subscriptions);
        this.mainListView = findViewById(R.id.mainListView);
        this.mainListView.setAdapter(this.subscriptionArrayAdapter);
        this.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Subscription oldSubscription = subscriptions.get(i);
                subscriptions.remove(i);
                subscriptionArrayAdapter.notifyDataSetChanged();
                writeToFile();
                intent.putExtra("SUBSCRIPTION", oldSubscription);
                startActivityForResult(intent, 0);
            }
        });
        this.mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                subscriptions.remove(i);
                subscriptionArrayAdapter.notifyDataSetChanged();
                writeToFile();
                return false;
            }
        });
    }

    private void writeToFile() {
        try {
            FileOutputStream fos = openFileOutput(this.book, Context.MODE_PRIVATE);
            for (Subscription subscription : this.subscriptions) {
                fos.write(subscription.toStringForFile().getBytes());
            }
            fos.flush();
            fos.close();
            this.readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readFile() {
        Subscription sub;
        FileInputStream fis;
        String line = "";
        String[] lineList;
        this.subscriptions.clear();
        try {
            fis = openFileInput(this.book);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sub = new Subscription(this);
                lineList = line.split("\\|");
                sub.setName(lineList[0]);
                sub.setStartedDate(lineList[1]);
                sub.setMonthlyCharges(lineList[2]);
                sub.setComment(lineList[3]);
                this.subscriptions.add(sub);
            }
            subscriptionArrayAdapter.notifyDataSetChanged();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        this.readFile();
    }
    public void newSubscriptionButton_OnClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("SUBSCRIPTION", new Subscription(this));
        startActivityForResult(intent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    Subscription newSubscription = intent.getExtras().getParcelable("SUBSCRIPTION");
                    subscriptions.add(newSubscription);
                    subscriptionArrayAdapter.notifyDataSetChanged();
                    this.writeToFile();
                }
                break;
            }
        }
    }
}
