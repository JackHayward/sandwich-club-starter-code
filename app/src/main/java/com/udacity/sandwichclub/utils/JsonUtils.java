package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

  public static Sandwich parseSandwichJson(String json) throws JSONException {

    Sandwich parsedSandwich = new Sandwich();
    JSONObject sandwichJson = new JSONObject(json);

    //Key JSON Items and Objects
    JSONObject name = (JSONObject) sandwichJson.getJSONObject("name");
    JSONArray alternativeSandwichNames = (JSONArray) name.getJSONArray("alsoKnownAs");
    JSONArray ingredients = (JSONArray) sandwichJson.getJSONArray("ingredients");

    //Parsed Items
    String mainName = name.get("mainName").toString();
    String placeOfOrigin = sandwichJson.get("placeOfOrigin").toString();
    String description = sandwichJson.get("description").toString();
    String image = sandwichJson.get("image").toString();
    List<String> parsedAlternativeSandwichNames =
        convertJsonArrayToStringList(alternativeSandwichNames);
    List<String> parsedIngredients = convertJsonArrayToStringList(ingredients);

    parsedSandwich.setAlsoKnownAs(parsedAlternativeSandwichNames);
    parsedSandwich.setDescription(description);
    parsedSandwich.setImage(image);
    parsedSandwich.setIngredients(parsedIngredients);
    parsedSandwich.setMainName(mainName);
    parsedSandwich.setPlaceOfOrigin(placeOfOrigin);

    return parsedSandwich;
  }

  private static List<String> convertJsonArrayToStringList(JSONArray jsonArray)
      throws JSONException {
    List<String> result = new ArrayList<>(jsonArray.length());
    for (int i = 0; i < jsonArray.length(); i++) {
      result.add(jsonArray.getString(i));
    }
    return result;
  }
}
