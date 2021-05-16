package uk.ac.shef.oak.jobserviceexample;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FetchBackend extends AsyncTask<String, Void, String> {
    FetchBackend(){
    }

    @Override
    protected String doInBackground(String... strings) {
        String pingInfo = NetworkUtils.getPingInfo();
        return pingInfo;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            Log.i("onPostTAG", " result = " + s);
            JSONArray jsonArray = new JSONArray(s);
            JSONObject object = null;
            if (jsonArray.length() == 1) {
                object = jsonArray.getJSONObject(0);
            }
            try {
                String date = object.getString("date");
                String host = object.getString("host");
                int count = object.getInt("count");
                int packetSize = object.getInt("packetSize");
                int jobPeriod = object.getInt("jobPeriod");
                String jobType = object.getString("jobType");
                Log.i("MY_TAG", date + host + String.valueOf(count) + String.valueOf(packetSize) + String.valueOf(jobPeriod) + jobType);

                try {
                    String pingCmd = "ping -s " + String.valueOf(packetSize) + " -c " + String.valueOf(count) + " " + host;
                    String pingResult = "";
                    Runtime r = Runtime.getRuntime();
                    Process p = r.exec(pingCmd);
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        pingResult += inputLine;
                    }
                    Log.i("CMDRESULT", pingResult);
                    in.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
