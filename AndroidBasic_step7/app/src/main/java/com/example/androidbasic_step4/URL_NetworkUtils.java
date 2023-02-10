package com.example.androidbasic_step4;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URL_NetworkUtils {

    static public String getPageSource(String url){
        URL google = null;
        try {
            google = new URL(url);
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

        Log.d("URL_NetworkUtils",htmlData);
        return htmlData;
    }
}
