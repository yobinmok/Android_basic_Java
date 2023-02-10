package com.example.androidbasic_step4;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class URLLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    public URLLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return URL_NetworkUtils.getPageSource(mQueryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        // forceLoad() 호출 전까지 loadInBackground 실행 X
    }
}

/*https://stackoverflow.com/questions/6503574/how-to-get-html-source-code-from-url-in-android
* URL google = null;
    try {
        google = new URL("https://www.google.com");
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    BufferedReader in = null;
    try {
        in = new BufferedReader(new InputStreamReader(google.openStream()));
    } catch (IOException e) {
        e.printStackTrace();
    }
    String input = null;
    StringBuffer stringBuffer = new StringBuffer();
    while (true)
    {
        try {
            if (!((input = in.readLine()) != null)) break;
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuffer.append(input);
    }
    try {
        in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    String htmlData = stringBuffer.toString();
* */