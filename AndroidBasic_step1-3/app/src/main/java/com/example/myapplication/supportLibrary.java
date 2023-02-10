package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class supportLibrary extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_library);

        textView = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            textView.setTextColor(savedInstanceState.getInt("color"));
        }
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                String colorName = mColorArray[random.nextInt(20)];
                int colorResourceName = getResources().getIdentifier(colorName, "color",getApplicationContext().getPackageName());
                int colorRes = ContextCompat.getColor(supportLibrary.this,colorResourceName);
                textView.setTextColor(colorRes);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color", textView.getCurrentTextColor());
    }
}