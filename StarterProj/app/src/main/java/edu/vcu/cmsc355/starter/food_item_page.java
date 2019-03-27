package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class food_item_page extends AppCompatActivity {

    String food;
    TextView topBanner;
    ArrayList<FoodItem> toBeRemoved;
    final int NUM_SIZE = 400;
    final int NAME_SIZE = 750;
    final int BUTTON_SIZE = 200;
    float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_food_item_page);
        final LinearLayout lm = (LinearLayout) findViewById(R.id.food_ll);

        // unpack food from Inventory activity:
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        food = (String) foodBundle.getString("foodName");
        topBanner = (TextView) findViewById(R.id.food_page_name);
        toBeRemoved = new ArrayList<FoodItem>();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;

        int relativeNameSize = (int) Math.ceil(NAME_SIZE /density);
        int relativeNumSize = (int) Math.ceil(NUM_SIZE /density);


        topBanner.setText(food);

        // create dummy food list for testing:
        ArrayList<FoodItem> testFoods = new ArrayList<FoodItem>();
        for(int i =0; i< 20; i++){
            FoodItem item = new FoodItem();

            if(i < 9){
                item.setCategory("Fruit");
                item.setName("apple");
                item.setQuantity(1999);
                item.setThreshold(100000);
                item.setSize("69 g");
                item.setLocation("C4");
                item.setExprDate("11/02/1963");
            }
            else if(i == 10){
                item.setThreshold(12);
                item.setName("Dog Food lol");
                item.setCategory("Dog");
                item.setQuantity(11);
                item.setLocation("A4");
                item.setExprDate("12/07/1941");
            }
            else{
                item.setCategory("Soup");
                item.setName("Tomato");
                item.setQuantity(69);
                item.setSize("12 oz");
                item.setLocation("B4");
                item.setExprDate("03/27/1994");
            }
            testFoods.add(item);
        }
        // remove food items that don't have the correct name:
        ArrayList<FoodItem> selectedFood = new ArrayList<FoodItem>();
        for(FoodItem currentFood : testFoods){
            if(currentFood.getName().equals(food)){
                selectedFood.add(currentFood);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        for(int j = 0; j<selectedFood.size(); j++){

            final FoodItem currentFood = selectedFood.get(j);
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(params);

            // display quantity, expiration date, and location:
            TextView quantity = new TextView(this);
            quantity.setText(String.valueOf(currentFood.getQuantity()));
            quantity.setWidth(relativeNumSize);
            ll.addView(quantity);

            TextView exprDate = new TextView(this);
            exprDate.setText(currentFood.getExprDate());
            exprDate.setWidth(relativeNumSize);
            ll.addView(exprDate);

            TextView loc = new TextView(this);
            loc.setText(currentFood.getLocation());
            exprDate.setWidth(relativeNumSize);
            ll.addView(loc);




            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Remove Item");
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            // Set click listener for button
            final FoodItem foodToSend = currentFood;
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    toBeRemoved.add(currentFood);

                }
            });
            ll.addView(btn);
            lm.addView(ll);


        }

        //@SAM: We need you to pull all the fooditems that have
        // the name stored in the string variable 'food'





    }

}
