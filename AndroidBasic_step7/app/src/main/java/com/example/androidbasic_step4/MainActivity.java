package com.example.androidbasic_step4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ProgressBar mprogressBar;
    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        mprogressBar = findViewById(R.id.progress);

        if(savedInstanceState != null){
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    public void startTask(View view) {
        mTextView.setText(R.string.napping);
        new SimpleAsyncTask(mTextView, mprogressBar).execute();
    }

    class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

        private WeakReference<TextView> mTextView;
        private WeakReference<ProgressBar> mProgressBar;
        private static final int CHUNK_SIZE = 5;

        SimpleAsyncTask(TextView tv, ProgressBar bar) {
            mTextView = new WeakReference<>(tv);
            mProgressBar = new WeakReference<>(bar);
        }

        @Override
        protected String doInBackground(Void... values) {
            Random random = new Random();
            int n = random.nextInt(11);
            int s = n * 200;
            int milli = n * 400;
            int chunkSize = milli / CHUNK_SIZE;
            for (int i = 0; i < CHUNK_SIZE; i++){
                try {
                    Thread.sleep(chunkSize);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(((i + 1) * 100) / CHUNK_SIZE);
            }

            return "Awake at last after sleeping for " + milli + " milliseconds!";
        }

        @Override
        protected void onProgressUpdate(Integer[] values) {
            mProgressBar.get().setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }
    }

}
