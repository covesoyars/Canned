package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import android.content.Intent;

/**
 * TO-DO create list to hold array of foodNames for each button (should have corresponding indices)
 * call the string in this list to send to activity when putton is pushed
 */

public class Inventory extends AppCompatActivity {

    final int NUM_SIZE = 400;
    final int NAME_SIZE = 750;
    final int BUTTON_SIZE = 200;
    float density;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScroll);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;

        int relativeNameSize = (int) Math.ceil(NAME_SIZE /density);
        int relativeNumSize = (int) Math.ceil(NUM_SIZE /density);

        // make a list of food items to test display
        final ArrayList<FoodItem> testFoods = new ArrayList<FoodItem>();
        for(int i =0; i< 20; i++){
            FoodItem item = new FoodItem();

            if(i < 9){
                item.setCategory("Fruit");
                item.setName("apple");
                item.setQuantity(1999);
                item.setThreshold(100000);
                item.setSize("69 g");
            }
            else if(i == 10){
                item.setThreshold(12);
                item.setName("Dog Food lol");
                item.setCategory("Dog");
                item.setQuantity(11);
            }
            else{
                item.setCategory("Soup");
                item.setName("Tomato");
                item.setQuantity(69);
                item.setSize("12 oz");
            }
            testFoods.add(item);
        }
        selectionSort(testFoods);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        String currentCatigory = "";
        int quantity=0;
        //Create four
        for(int j=0;j<testFoods.size();j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            FoodItem food=testFoods.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);


          //  FoodItem food=testFoods.get(j);

            //ad catigory soace
            if(!food.getCategory().equals(currentCatigory))
            {
                currentCatigory=food.getCategory();
                TextView catigory = new TextView(this);
                catigory.setText( currentCatigory );
                catigory.setGravity(Gravity.CENTER);
                catigory.setTextSize(20);
                catigory.setWidth(ll.getWidth());
                catigory.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                catigory.setTextColor(Color.WHITE);
                lm.addView(catigory);
            }

            while(j<testFoods.size()-1 && food.getName().equals(testFoods.get(j+1).getName()))
            {
                j++;
                quantity=quantity+food.getQuantity();
            }




            // Create TextView
            TextView name = new TextView(this);
            name.setText(food.getName());
            name.setWidth(relativeNameSize);
            ll.addView(name);

            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(String.valueOf(quantity+food.getQuantity()) + "     ");
            if(food.getQuantity() < food.getThreshold()){
                quantity2.setTextColor(Color.RED);
            }
            quantity2.setWidth(relativeNumSize);
            ll.addView(quantity2);

            // Create TextView
            TextView size = new TextView(this);
            size.setText((food.getSize()));
            size.setWidth(relativeNumSize);
            ll.addView(size);

            // Create TextView
            TextView thresh = new TextView(this);
            thresh.setText((String.valueOf(food.getThreshold())));
            thresh.setWidth(relativeNumSize);
            ll.addView(thresh);



            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Edit Item");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            //btn.setRight(0);

           // btn.setWidth(BUTTON_SIZE);


//            RelativeLayout.LayoutParams btnlocation = (RelativeLayout.LayoutParams) btn.getLayoutParams();
//            btnlocation.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            btn.setLayoutParams(btnlocation);



            final int index = j;
            final FoodItem foodToSend = food;
            // Set click listener for button
            btn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    launchFoodItemPage(v,testFoods,foodToSend.getName());

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
            quantity=0;
        }

    }

    private void launchFoodItemPage(View view, ArrayList<FoodItem> foods, String foodName){


        // create and launch intent
        final Intent launchFood = new Intent(Inventory.this,food_item_page.class);

        Bundle bundle = new Bundle();
        bundle.putString("foodName", foodName);
        launchFood.putExtra("bundle", bundle);
        startActivity(launchFood);
    }

    private void selectionSort( ArrayList<FoodItem> list)
    {

        // Find the string reference that should go in each cell of
        // the array, from cell 0 to the end
        for ( int j = 0; j < list.size();j++ )
        {
            // Find min: the index of the string reference that should go into cell j.
            // Look through the unsorted strings (those at j or higher) for the one that is first in lexicographic order
            int min = j;
            for ( int k=j+1; k < list.size(); k++ )
                if ( list.get(k).compareTo( list.get(min) ) < 0 ) min = k;

            // Swap the reference at j with the reference at min
            Collections.swap(list, j, min);
        }

    }

}
