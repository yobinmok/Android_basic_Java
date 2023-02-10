package com.example.androidbasic_step4;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        // API 쿼리 문자열 저장하기 위해
        mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            return NetworkUtils.getBookInfo(mQueryString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        // forceLoad() 호출 전까지 loadInBackground 실행 X
    }
}
