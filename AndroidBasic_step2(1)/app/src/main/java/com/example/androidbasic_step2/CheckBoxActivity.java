package com.example.androidbasic_step2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class CheckBoxActivity extends AppCompatActivity {

    StringBuffer toppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_boxes);

        toppings = new StringBuffer();
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Toppings: " + toppings;
                Toast.makeText(CheckBoxActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addList(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case(R.id.chocolate):
                if (checked)
                    toppings.append(getString(R.string.chocolate_syrup)+" ");
                break;
            case(R.id.sprinkles):
                if (checked)
                    toppings.append(getString(R.string.sprinkles)+" ");
                break;
            case(R.id.cherries):
                if (checked)
                    toppings.append(getString(R.string.cherries)+" ");
                break;
            case(R.id.cookie):
                if (checked)
                    toppings.append(getString(R.string.cookie_crumbles)+" ");
                break;
            default:
                break;
        }
    }
}