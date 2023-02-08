package com.example.androidbasic_step3;

import static java.lang.Boolean.TRUE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";
    private int mScore1 = 0;
    private int mScore2 = 0;
    TextView mScoreText1, mScoreText2;
    private ImageButton decrease1, decrease2, increase1, increase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreText1 = findViewById(R.id.score_1);
        mScoreText2 = findViewById(R.id.score_2);
        decrease1 = findViewById(R.id.decreaseTeam1);
        decrease2 = findViewById(R.id.decreaseTeam2);
        increase1 = findViewById(R.id.increaseTeam1);
        increase2 = findViewById(R.id.increaseTeam2);

        if (savedInstanceState != null){ // 이전 화면에서 저장된 값이 있다면
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            mScoreText1.setText(String.valueOf(mScore1));
            mScoreText2.setText(String.valueOf(mScore2));
        }

        decrease1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrease(view);
            }
        });
        decrease2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrease(view);
            }
        });
        increase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increase(view);
            }
        });
        increase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increase(view);
            }
        });
    }

    public void increase(View view){
        switch(view.getId()){
            case R.id.increaseTeam1:
                mScore1 += 1;
                mScoreText1.setText(String.valueOf(mScore1));
                break;
            case R.id.increaseTeam2:
                mScore2 += 1;
                mScoreText2.setText(String.valueOf(mScore2));
                break;
        }
    }

    public void decrease(View view){
        switch(view.getId()){
            case R.id.decreaseTeam1:
                if (mScore1 == 0)
                    break;
                mScore1 -= 1;
                mScoreText1.setText(String.valueOf(mScore1));
                break;
            case R.id.decreaseTeam2:
                if (mScore2 == 0)
                    break;
                mScore2 -= 1;
                mScoreText2.setText(String.valueOf(mScore2));
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_NO){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
        }else{ // item.getItemId() == R.id.day_mode
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }
        return true;
    }
}