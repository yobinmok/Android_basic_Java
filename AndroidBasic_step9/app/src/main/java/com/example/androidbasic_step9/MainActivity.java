package com.example.androidbasic_step9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    // Current background color
    private int mColor;
    private Drawable mDColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;

    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";
    private SharedPreferences mPreference;
    private String sharedPrefFile = "com.example.androidBasic_step9";
    Button blackBtn, redBtn, blueBtn, greenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview);
        mDColor = getDrawable(R.color.default_background);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        // Restore preference
        mCount = mPreference.getInt(COUNT_KEY,0);
        mShowCountTextView.setText(String.format("%s", mCount));

        blackBtn = findViewById(R.id.black_background_button);
        redBtn = findViewById(R.id.red_background_button);
        blueBtn = findViewById(R.id.blue_background_button);
        greenBtn = findViewById(R.id.green_background_button);

        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(view);
            }
        });
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(view);
            }
        });
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(view);
            }
        });
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(view);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferenceEditor = mPreference.edit();
        preferenceEditor.putInt(COUNT_KEY,mCount);
        // mColor는 drawable - int 캐스트 에러로 일단 제외
        preferenceEditor.apply();
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        Drawable colorDrawable = (Drawable) view.getBackground();
//        int color = colorDrawable.getColor();
        mShowCountTextView.setBackground(colorDrawable);
        //mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {

        // Reset count
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mDColor = getDrawable(R.color.default_background);
        //mColor = ContextCompat.getColor(this, R.color.default_background);
        mShowCountTextView.setBackground(mDColor);

        SharedPreferences.Editor editor = mPreference.edit();
        editor.clear();
        editor.apply();
    }
}