package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Sandwich sandwich;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .resize(250, 250)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


        TextView originTv = (TextView) findViewById(R.id.origin_tv);
        originTv.setText(sandwich.getPlaceOfOrigin());

        LinearLayout alsoKnownAsLinearLayout = (LinearLayout) findViewById(R.id.also_known_as_linear_layout);
        TextView alsoKnownAsTv = (TextView) findViewById(R.id.also_known_tv);

        // If "also known as" is empty for a particular sandwich then make that layout invisible
        if (list2String(sandwich.getAlsoKnownAs()).toString().equals("")) {
            alsoKnownAsLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            alsoKnownAsTv.setText(list2String(sandwich.getAlsoKnownAs()));
        }


        TextView ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        ingredientsTv.setText(list2String(sandwich.getIngredients()));

        TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());


    }

    public StringBuilder list2String(List<String> list) {
        StringBuilder myString = new StringBuilder();
        for (int j = 0; j < list.size(); j++) {
            myString.append(list.get(j) + "\n");
        }

        return myString;
    }

}
