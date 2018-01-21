package com.example.alanj.jingming_subbook_rev1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayScreen extends AppCompatActivity {
    private Book book;
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((TextView) findViewById(R.id.nameTitle)).getLayoutParams().width = (int) (getScreenWidth(this) * 0.4);
        ((TextView) findViewById(R.id.dateStartedTitle)).getLayoutParams().width = (int) (getScreenWidth(this) * 0.3);
        ((TextView) findViewById(R.id.monthlyChargesTitle)).getLayoutParams().width = (int) (getScreenWidth(this) * 0.3);
        xmlDecoder decoder = new xmlDecoder(this);
        decoder.setFileInputStream();
        LinearLayout mainVerticalLinearLayout = (LinearLayout)findViewById(R.id.mainVerticalLinearLayout);
        try {
            mainVerticalLinearLayout.removeViewsInLayout(1, mainVerticalLinearLayout.getChildCount()-1);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            this.book = decoder.decodeBook();
            for (Subscription subscription : this.book.getBook()) {
                LinearLayout genericLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.generic_row_display, null);

                TextView name = (TextView) genericLinearLayout.getChildAt(0);
                TextView dateStarted = (TextView) genericLinearLayout.getChildAt(1);
                TextView monthlyCharges = (TextView) genericLinearLayout.getChildAt(2);

                genericLinearLayout.setTag(subscription.getUuid());
                name.setText(subscription.getSubscriptionNameC(), 0, subscription.getSubscriptionNameLength());
                dateStarted.setText(subscription.getDateStartedC(), 0, subscription.getDateStartedLength());
                monthlyCharges.setText(subscription.getMonthlyChargesC(), 0, subscription.getMonthlyChargesLength());

                name.getLayoutParams().width = (int) (getScreenWidth(this) * 0.4);
                dateStarted.getLayoutParams().width = (int) (getScreenWidth(this) * 0.3);
                monthlyCharges.getLayoutParams().width = (int) (getScreenWidth(this) * 0.3);

                genericLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        editSubscription(view);
                        return true;
                    }
                });

                mainVerticalLinearLayout.addView(genericLinearLayout);
            }
        } catch (InputTooLongException e) {
            e.printStackTrace();
        } catch (ExpectedNonNegativeException e) {
            e.printStackTrace();
        } catch (NullOrEmptyException e) {
            e.printStackTrace();
        }
    }

    public void editSubscription(View view) {
        Intent newIntent = new Intent(DisplayScreen.this, EditSubscription.class);
        if (view instanceof Button) {
            newIntent.putExtra("CURRENT_BOOK", this.book);
            newIntent.putExtra("EDIT_SUBSCRIPTION", new Subscription());
        }
        else if (view instanceof LinearLayout) {
            for (Subscription subscription : this.book.getBook()) {
                if (subscription.getUuid().equals(view.getTag())) {
                    this.book.removeSubscription(subscription);
                    newIntent.putExtra("CURRENT_BOOK", this.book);
                    newIntent.putExtra("EDIT_SUBSCRIPTION", subscription);
                    break;
                }
            }
        }
        startActivity(newIntent);
    }
}
