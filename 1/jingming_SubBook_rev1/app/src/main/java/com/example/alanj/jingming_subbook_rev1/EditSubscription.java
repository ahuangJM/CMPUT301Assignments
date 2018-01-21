package com.example.alanj.jingming_subbook_rev1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditSubscription extends AppCompatActivity {

    private Book book;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent currentIntent = getIntent();
        book = (Book) currentIntent.getParcelableExtra("CURRENT_BOOK");
        subscription = (Subscription) currentIntent.getParcelableExtra("EDIT_SUBSCRIPTION");
        EditText editName = (EditText) findViewById(R.id.editName);
        DatePicker editDateStarted = (DatePicker) findViewById(R.id.editDateStarted);
        EditText editMonthlyCharges = (EditText) findViewById(R.id.editMonthlyCharges);
        EditText editComments = (EditText) findViewById(R.id.editComments);

        editName.setText(subscription.getSubscriptionName());
        try {
            editDateStarted.updateDate(subscription.getYear(), subscription.getMonth(), subscription.getDayOfMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        editMonthlyCharges.setText(subscription.getMonthlyCharges());
        editComments.setText(subscription.getComments());
    }

    public void confirmEditButton(View view) throws InputTooLongException, ExpectedNonNegativeException, NullOrEmptyException {
        EditText editName = (EditText) findViewById(R.id.editName);
        DatePicker editDateStarted = (DatePicker) findViewById(R.id.editDateStarted);
        EditText editMonthlyCharges = (EditText) findViewById(R.id.editMonthlyCharges);
        EditText editComments = (EditText) findViewById(R.id.editComments);
        Double monthlyCharges = Double.parseDouble(String.valueOf(editMonthlyCharges.getText()));
        if (monthlyCharges < 0) {
            editMonthlyCharges.setBackgroundColor(getResources().getColor(R.color.colorRed));
            Toast.makeText(this, "Monthly Charges Must Be Non-Negative", Toast.LENGTH_LONG).show();
        }
        else {
            if (this.subscription.getUuid() == null || this.subscription.getUuid().isEmpty()) {
                this.subscription.setUuid();
            }
            String year = String.valueOf(editDateStarted.getYear());
            String month = String.valueOf(editDateStarted.getMonth());
            String day_of_month = String.valueOf(editDateStarted.getDayOfMonth());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedCurrentDate = null;
            try {
                convertedCurrentDate = simpleDateFormat.parse(year+"-"+month+"-"+day_of_month);
            } catch (ParseException e) {
                Log.e("Error: ", "Date Parse Failed");
                e.printStackTrace();
            }
            this.subscription.setSubscriptionName(String.valueOf(editName.getText()));
            this.subscription.setDateStarted(simpleDateFormat.format(convertedCurrentDate));
            this.subscription.setMonthlyCharges(String.valueOf(editMonthlyCharges.getText()));
            this.subscription.setComments(String.valueOf(editComments.getText()));
            this.book.addSubscription(this.subscription);
            this.finish();
        }
    }
}
