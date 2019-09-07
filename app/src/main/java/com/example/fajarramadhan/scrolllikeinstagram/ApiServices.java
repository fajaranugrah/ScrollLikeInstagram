package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Response;

public class ApiServices {

    private static int responseCode;
    public static boolean authenticationEnable = false;
    static String DEBUG_TAG;

    private static int totalPage = 0;

    public static int totalPage(){
        return totalPage;
    }

    //used to get response code
    public static int getResponseCode() {
        return responseCode;
    }

    //used to set debug tag for api service
    public static void setDebugTag(String tag){
        DEBUG_TAG = tag;
    }

    //used to show error message respond from api server
    public static String ErrorMessage(JSONObject response){
        try{
            String str =  response.getString("message");
            String noBackSlash = str.replace("\\","");
            return noBackSlash;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String ErrorCode(JSONObject response){
        try{
            String str =  response.getString("code");
            String noBackSlash = str.replace("\\","");
            return noBackSlash;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String ErrorStatus(JSONObject response){
        try{
            String str =  response.getString("status");
            String noBackSlash = str.replace("\\","");
            return noBackSlash;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //get authentication header string
    public static String getAuthenticationString(){
        //Authentication
        /**FORMAT
         * RA User_email:Signature
         *  Signature = Base64( HMAC-SHA1( YourToken, UTF-8-Encoding-Of( CurrentDateTime ) ) );*/
        try {
            return "RA 737844c5-de02-11e6-83b5-42010af00aa4:b5b1f991089866f5d36e90a6407fa2a7ae9bfafd";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //get authentication header string
    public static String getAuthenticationStringRequest(){
        //Authentication
        /**FORMAT
         * RA User_email:Signature
         *  Signature = Base64( HMAC-SHA1( YourToken, UTF-8-Encoding-Of( CurrentDateTime ) ) );*/
        try {
            return "RA c2efe3ab-de06-11e6-83b5-42010af00aa4:0ab499d774861bdf5226705fcd810651206ab009";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSON(Response response){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }

            // Prints the correct String representation of body.
            JSONObject res = new JSONObject(out.toString());
            return res;
            //Log.e(TAG, "Response: \nCODE:"+response.getStatus() + "\n"+res.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringResponse(Response response){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }

            // Prints the correct String representation of body.
            String res = out.toString();
            return res;
            //Log.e(TAG, "Response: \nCODE:"+response.getStatus() + "\n"+res.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void kickUser(int code, Context ctx){
    }

    public static int getTotalPage(Response s){

        HashMap<String, String> map = new HashMap<>();
        List<Header> headerList = s.getHeaders();
        for(retrofit.client.Header header : headerList) {
            map.put(header.getName(), header.getValue());
            // Log.e("Headers", header.getName() + " " + header.getValue());
        }

        String value = map.get("X-Pagination-Page-Count");
        String value2 = map.get("x-pagination-page-count");
        if (value != null) {
            return Integer.parseInt(value);
        } else if (value2 != null){
            return Integer.parseInt(value2);
        } else {
            // Key might be present...
            if (map.containsKey("X-Pagination-Page-Count")) {
                // Okay, there's a key but the value is null
                return 0;
            } else if (map.containsKey("x-pagination-page-count")){
                // Okay, there's a key but the value is null
                return 0;
            } else {
                // Definitely no such key
                return 0;
            }
        }
    }

    public static int getTotalCount(Response s){

        HashMap<String, String> map = new HashMap<>();
        List<Header> headerList = s.getHeaders();
        for(retrofit.client.Header header : headerList) {
            map.put(header.getName(), header.getValue());
            // Log.e("Headers", header.getName() + " " + header.getValue());
        }

        String value = map.get("X-Pagination-Total-Count");
        String value2 = map.get("x-pagination-total-count");
        if (value != null) {
            return Integer.parseInt(value);
        } else if (value2 != null){
            return Integer.parseInt(value2);
        } else {
            // Key might be present...
            if (map.containsKey("X-Pagination-Total-Count")) {
                // Okay, there's a key but the value is null
                return 0;
            } else if (map.containsKey("x-pagination-total-count")){
                // Okay, there's a key but the value is null
                return 0;
            } else {
                // Definitely no such key
                return 0;
            }
        }
    }
}
