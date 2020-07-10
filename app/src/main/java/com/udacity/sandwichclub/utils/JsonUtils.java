package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
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
    List<String> parsedAlternativeSandwichNames = parseJsonArray(alternativeSandwichNames);
    List<String> parsedIngredients = parseJsonArray(ingredients);

    parsedSandwich.setAlsoKnownAs(parsedAlternativeSandwichNames);
    parsedSandwich.setDescription(description);
    parsedSandwich.setImage(image);
    parsedSandwich.setIngredients(parsedIngredients);
    parsedSandwich.setMainName(mainName);
    parsedSandwich.setPlaceOfOrigin(placeOfOrigin);

    return parsedSandwich;
  }

  private static List<String> parseJsonArray(JSONArray jsonArray) throws JSONException {
    List<String> stringsList = null;

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject sandwichName = (JSONObject) jsonArray.get(i);
      stringsList.add(sandwichName.toString());
    }

    return stringsList;
  }
}
