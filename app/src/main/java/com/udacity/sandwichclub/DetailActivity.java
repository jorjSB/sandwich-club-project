package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static TextView originTv;
    private static TextView originLabelTv;
    private static TextView descriptionTv;
    private static TextView descriptionLabelTv;
    private static TextView ingredientsTv;
    private static TextView ingredientsLabelTv;
    private static TextView alsoKnownTv;
    private static TextView alsoKnownLabelTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        originLabelTv = findViewById(R.id.origin_lb_tv);

        descriptionTv = findViewById(R.id.description_tv);
        descriptionLabelTv = findViewById(R.id.description_lb_tv);

        ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_lb_tv);

        alsoKnownTv = findViewById(R.id.also_known_tv);
        alsoKnownLabelTv = findViewById(R.id.also_known_lb_tv);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param sandwich
     * Populates the UI
     */
    private void populateUI(Sandwich sandwich) {
        if(sandwich.getPlaceOfOrigin().isEmpty()) originLabelTv.setVisibility(View.INVISIBLE);
        originTv.setText(sandwich.getPlaceOfOrigin());

        if(sandwich.getDescription().isEmpty()) descriptionLabelTv.setVisibility(View.INVISIBLE);
        descriptionTv.setText(sandwich.getDescription());

        String ingredients = convertToPrettyString(sandwich.getIngredients());
        if(ingredients.isEmpty()) ingredientsLabelTv.setVisibility(View.INVISIBLE);
        ingredientsTv.setText(ingredients);

        String alsoKnownAs = convertToPrettyString(sandwich.getAlsoKnownAs());
        if(alsoKnownAs.isEmpty()) alsoKnownLabelTv.setVisibility(View.INVISIBLE);
        alsoKnownTv.setText(alsoKnownAs);
    }

    /**
     * @param list
     * @return
     *
     * Convert my list to a readable string for displaiyng in the textView
     */
    private String convertToPrettyString(List<String> list){
        String stringList = new String();

        for (String item : list)
            stringList += item + "\n";

        return stringList.trim();
    }

}
