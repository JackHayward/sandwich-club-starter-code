package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

import static android.view.View.GONE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView descriptionTv;
    private TextView alsoKnownAsTv;
    private TextView alsoKnownAsLabel;
    private TextView ingredientsTv;
    private TextView placeOfOriginTv;
    private TextView placeOfOriginLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        descriptionTv = findViewById(R.id.description_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsLabel = findViewById(R.id.also_known_label);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOriginLabel = findViewById(R.id.origin_label);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        if (sandwich.getAlsoKnownAs().size() == 0) {
            alsoKnownAsTv.setVisibility(GONE);
            alsoKnownAsLabel.setVisibility(GONE);
        } else {
            populateTextViewWithArrayList(alsoKnownAsTv, sandwich.getAlsoKnownAs());
        }

        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOriginTv.setVisibility(GONE);
            placeOfOriginLabel.setVisibility(GONE);
        } else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        descriptionTv.setText(sandwich.getDescription());
        populateTextViewWithArrayList(ingredientsTv, sandwich.getIngredients());
    }

    private void populateTextViewWithArrayList(TextView textView, List<String> list) {
        String listString = "";

        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == list.get(list.size() -1)){
                listString += "- " + list.get(i);
            } else {
                listString += "- " + list.get(i) + "\n";
            }
        }
        textView.setText(listString);
    }
}
