package com.example.alanj.jingming_subbook_rev1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);

        xmlDecoder decoder = new xmlDecoder(this);
        decoder.setFileInputStream();
        try {
            Book book = decoder.decodeBook();
            for (Subscription subscription : book.getBook()) {
                LinearLayout mainVerticalLinearLayout = (LinearLayout)findViewById(R.id.mainVerticalLinearLayout);
                LinearLayout genericLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.generic_row_display, null);

                TextView name = (TextView) genericLinearLayout.getChildAt(0);
                TextView dateStarted = (TextView) genericLinearLayout.getChildAt(1);
                TextView monthlyCharges = (TextView) genericLinearLayout.getChildAt(2);

                name.setText(subscription.getSubscriptionNameC(), 0, subscription.getSubscriptionNameLength());
                dateStarted.setText(subscription.getDateStartedC(), 0, subscription.getDateStartedLength());
                monthlyCharges.setText(subscription.getMonthlyChargesC(), 0, subscription.getMonthlyChargesLength());

                mainVerticalLinearLayout.addView(genericLinearLayout);
            }
        } catch (InputTooLongException e) {
            e.printStackTrace();
        } catch (ExpectedNonNegativeException e) {
            e.printStackTrace();
        } catch (NullOrEmptyException e) {
            e.printStackTrace();
        }

//        TextView name = (TextView) genericLinearLayout.getChildAt(0);
//        TextView dateStarted = (TextView) genericLinearLayout.getChildAt(1);
//        TextView monthlyCharges = (TextView) genericLinearLayout.getChildAt(2);
//        name.setText("TekSavvy".toCharArray(), 0, "TekSavvy".length());
//        dateStarted.setText("2017-09-01".toCharArray(), 0, "2017-09-01".length());
//        monthlyCharges.setText("60.00".toCharArray(), 0, "60.00".length());

//        mainVerticalLinearLayout.addView(genericLinearLayout);

    }
}
