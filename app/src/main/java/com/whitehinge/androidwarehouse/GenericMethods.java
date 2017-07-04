package com.whitehinge.androidwarehouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by leeb on 29/06/2017.
 */

public class GenericMethods {

    public void AllowBadCoding(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }


    public JSONObject getJSON(String httpTarget){

        URL _url;
        HttpURLConnection urlConnection;
        InputStream is = null;
        String output=null;
        JSONObject json=null;


        try {
            _url = new URL(httpTarget);
            urlConnection = (HttpURLConnection) _url.openConnection();
            urlConnection.setRequestMethod("POST");
        }
        catch (MalformedURLException e) {
            Log.e("JSON Parser", "Error due to a malformed URL " + e.toString());
            return null;
        }
        catch (IOException e) {
            Log.e("JSON Parser", "IO error " + e.toString());
            return null;
        }

        try {
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder(is.available());
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line).append('\n');
            }
            output = total.toString();
        }
        catch (IOException e) {
            Log.e("JSON Parser", "IO error " + e.toString());
            return null;
        }
        finally{
            urlConnection.disconnect();
        }

        try {
            json = new JSONObject(output);
        }
        catch (JSONException e) {
            try{
                json = new JSONObject("{\"data\":" + output + "}");
            }catch(JSONException e2){
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
        return json;
    }

    protected JSONObject GetBackgroundJson(String httpTarget) {
        return null;
    }

    public void GenericAlert(String AlertText, Activity hostActivity, String AlertTitle){
        new AlertDialog.Builder(hostActivity)
                .setTitle(AlertTitle)
                .setMessage(AlertText)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).show();
    }

    public void GenericAlert(String AlertText, Activity hostActivity){
       GenericAlert(AlertText, hostActivity, "Alert");
    }

}
