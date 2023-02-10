package com.example.androidbasic_step4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class URLActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText urlText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlactivity);

        urlText = findViewById(R.id.url);
        resultText = findViewById(R.id.result);
    }

    public void search(View view) {
        String queryString = urlText.getText().toString();

        // 검색어 입력 후 키보드 숨기기
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if(queryString.length() != 0){
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            urlText.setText("");
            resultText.setText(R.string.loading);
        }else{
            resultText.setText(R.string.no_url);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new URLLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null)
            resultText.setText(data);
        else
            resultText.setText(R.string.no_url);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}