package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Implicit_Intent extends AppCompatActivity {

    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareTextEditText = findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {
        String url = mWebsiteEditText.getText().toString();

        //Encode and parse that string into a Uri object
        Uri webpage = Uri.parse(url);

        //Create a new Intent with Intent.ACTION_VIEW as the action and the URI as the data
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // 암시적 인텐트를 처리할 수 있는 액티비티인지 확인
        // -> 인텐트 작업 및 데이터를 기기에 설치된 앱에 대한 인텐트 필터와 일치시킴
        // 요청을 처리할 수 있는 활동이 하나 이상 있는지 확인하는 데 사용
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Log.d("ImplicitIntents", "Can't handle this!");
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void shareText(View view) {
        String txt = mShareTextEditText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this) // The Activity that launches this share Intent (this).
                .setType(mimeType) // The MIME type of the item to be shared.
                .setChooserTitle("Share this text with: ") // The title that appears on the system app chooser.
                .setText(txt) // The actual text to be shared
                .startChooser(); // Show the system app chooser and send the Intent.

    }
}