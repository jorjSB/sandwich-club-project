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

        return sandwichDetails == null ? null : setSandwichProperties(sandwichDetails);
    }


    /**
     Helper method
     cooking my sandwich
     */
    private static Sandwich setSandwichProperties(JSONObject sandwichDetails){

        Sandwich sandwich = new Sandwich();

        sandwich.setMainName( sandwichDetails.optJSONObject("name").optString("mainName") );  // String mainName
        sandwich.setAlsoKnownAs( getStringArrayFromJsonArray(sandwichDetails.optJSONObject("name").optJSONArray("alsoKnownAs")));    //List<String> alsoKnownAs,
        sandwich.setPlaceOfOrigin( sandwichDetails.optString("placeOfOrigin") ); // String placeOfOrigin,
        sandwich.setDescription( sandwichDetails.optString("description")); // String description,
        sandwich.setImage( sandwichDetails.optString("image") ); // String image,
        sandwich.setIngredients( getStringArrayFromJsonArray(sandwichDetails.optJSONArray("ingredients")));   // List<String> ingredients

        return sandwich;
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
