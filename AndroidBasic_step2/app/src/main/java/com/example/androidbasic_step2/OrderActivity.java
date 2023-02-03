package com.example.androidbasic_step2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        textView = findViewById(R.id.order_textView);

        Intent intent = getIntent();
        String msg = "Order: " + intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        textView.setText(msg);
    }
}