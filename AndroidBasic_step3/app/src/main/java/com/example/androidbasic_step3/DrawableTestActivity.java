package com.example.androidbasic_step3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DrawableTestActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button_plus, button_minus;
    int imageLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_test);

        imageView = findViewById(R.id.battery);
        imageView.setImageLevel(imageLevel);
        button_plus = findViewById(R.id.plus);
        button_minus = findViewById(R.id.minus);

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBatteryLevel(view);
            }
        });

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBatteryLevel(view);
            }
        });
    }

    private void changeBatteryLevel(View view) {
        switch (view.getId()){
            case(R.id.plus):
                if(imageLevel < 2){
                    imageLevel ++;
                    imageView.setImageLevel(imageLevel);
                }
                break;
            case(R.id.minus):
                if(imageLevel > 0){
                    imageLevel --;
                    imageView.setImageLevel(imageLevel);
                }
                break;
        }
    }
}