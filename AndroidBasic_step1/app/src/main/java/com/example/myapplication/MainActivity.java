package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private Button mZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = findViewById(R.id.show_count);
        mZero = findViewById(R.id.button_zero);

    }

    public void showToast(View view) {
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    public void countUp(View view) {
        mCount++;
        if (mShowCount != null){
            mShowCount.setText(Integer.toString(mCount));
        }

        // count가 짝수이면 색상 변경
        if (mCount != 0 && mCount % 2 == 0)
            view.setBackgroundColor(Color.parseColor("#6cf542"));
        else
            view.setBackgroundColor(R.color.purple_200);

        // 제로 버튼 활성화
        if (mCount == 1)
            mZero.setBackgroundColor(Color.parseColor("#fa66df"));
    }

    @SuppressLint("ResourceAsColor")
    public void makeZero(View view) {
        if (mShowCount != null){
            mCount = 0;
            mShowCount.setText("0");
        }
        view.setBackgroundColor(R.color.purple_500);
    }
}