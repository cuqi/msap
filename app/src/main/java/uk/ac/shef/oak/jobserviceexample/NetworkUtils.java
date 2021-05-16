package uk.ac.shef.oak.jobserviceexample;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    //private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BACKEND_BASE_URL = "http://10.0.2.2:5000/getjobs";

    static String getPingInfo() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String pingJSONString = null;

        try {
            // URL
            URL requestURL = new URL(BACKEND_BASE_URL);
            // Open network connection
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Input stream
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // StringBuilder for the incoming response
            StringBuilder builder = new StringBuilder();

            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length() == 0) {
                //Stream was empty. Exit without parsing
                return null;
            }

            pingJSONString = builder.toString();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.d(LOG_TAG, pingJSONString);

        return pingJSONString;
    }
}
