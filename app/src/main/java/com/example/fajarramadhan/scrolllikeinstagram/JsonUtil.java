package com.example.fajarramadhan.scrolllikeinstagram;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static final String TAG = JsonUtil.class.getName();

    private JsonUtil(){}
    /**
     * This Method Convert Object in JaksonJSON String
     * @param object
     * @return Json String or null if some error
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            Log.e(TAG, "Error in Converting Model to Json", e);
        }
        return null;
    }

    /**
     * This method convert json to model
     * @param json
     * @param object
     * @return Model or null if some error
     */
    public static <T> Object toModel(String json,	Class<T> object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            return mapper.readValue(json, object);
        } catch (Exception e) {
            Log.e(TAG, "Error in Converting Json to Model", e);
        }
        return null;
    }

    public static boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
