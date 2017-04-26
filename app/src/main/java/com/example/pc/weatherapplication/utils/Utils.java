package com.example.pc.weatherapplication.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Krisjanis on 04/04/2017.
 */

public class Utils {

    public static String readStringFromAssets(Context context, String fileName)   {

        String fileContents = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            fileContents = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

}
