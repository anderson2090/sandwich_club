package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {

        JSONObject rootObject;
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String imagePath = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();

        try {
            rootObject = new JSONObject(json);
            JSONObject nameObject = rootObject.getJSONObject("name");
            mainName = nameObject.getString("mainName");
            placeOfOrigin = rootObject.getString("placeOfOrigin");
            description = rootObject.getString("description");
            imagePath = rootObject.getString("image");
            alsoKnownAs = jsonArray2List(nameObject.getJSONArray("alsoKnownAs"));
            ingredients = jsonArray2List(rootObject.getJSONArray("ingredients"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("asda", "dasdas");
        return new Sandwich(mainName, alsoKnownAs,
                placeOfOrigin, description, imagePath,
                ingredients);
    }

    public static List<String> jsonArray2List(JSONArray jsonArray) {
        List<String> myList = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    myList.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return myList;
    }
}
