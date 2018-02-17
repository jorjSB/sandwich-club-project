package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        // check if data
        if(json != null && json.isEmpty())
            return null;


        JSONObject sandwichDetails = null;
        try {
            sandwichDetails = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return new object using "opt", to avoid throwing JSONException or null if converting to JsonObject failed
        return sandwichDetails == null ? null : new Sandwich(
                sandwichDetails.optJSONObject("name").optString("mainName"), // String mainName
                getStringArrayFromJsonArray(sandwichDetails.optJSONObject("name").optJSONArray("alsoKnownAs")),    //List<String> alsoKnownAs,
                sandwichDetails.optString("placeOfOrigin"), // String placeOfOrigin,
                sandwichDetails.optString("description"), // String description,
                sandwichDetails.optString("image"), // String image,
                getStringArrayFromJsonArray(sandwichDetails.optJSONArray("ingredients"))   // List<String> ingredients
        );
    }



    /**
        Helper method
        converts JsonArray to ArrayList List<String>
     */
    private static List<String> getStringArrayFromJsonArray(JSONArray jsonArray){
        List<String> list = new ArrayList<>();

        // loop through jsonArray
        for (int i=0; i<jsonArray.length(); i++)
            // add result to list only if not empty
            if (!jsonArray.optString(i).isEmpty())
                list.add(jsonArray.optString(i));

        return list;
    }
}
