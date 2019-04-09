package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class changeThreshold extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_threshold);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScroll2);
        int numWidth =getResources().getDimensionPixelSize(R.dimen._50sdp);
        int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);

        // make a list of food items to test display
        final ArrayList<FoodItem> testFoods = new ArrayList<FoodItem>();
        for(int i =0; i< 20; i++){
            FoodItem item = new FoodItem();

            if(i < 9){
                item.setCategory("Milk");
                item.setName("GreatValue Milk");
                item.setQuantity(40);
                item.setThreshold(20);
                item.setSize("1 gal");
            }
            else if(i == 10){
                item.setThreshold(12);
                item.setName("Pupusas");
                item.setCategory("Hispanic");
                item.setSize("12 oz");
                item.setQuantity(11);
            }
            else{
                item.setCategory("liquid");
                item.setName("water");
                item.setQuantity(200);
                item.setSize("12 oz");
                item.setThreshold(500);
            }
            testFoods.add(item);
        }
        selectionSort(testFoods);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


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



            ll.setLayoutParams(params);
            // Create TextView
            TextView name = new TextView(this);
            name.setText(food.getName());
            name.setWidth(nameWidth-50);
            ll.addView(name);

            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(String.valueOf(quantity+food.getQuantity()));
            if(quantity < food.getThreshold()){
                quantity2.setTextColor(Color.RED);
            }
            quantity2.setWidth(numWidth);
            ll.addView(quantity2);


            // Create TextView
            TextView thresh = new TextView(this);
            thresh.setText((String.valueOf(food.getThreshold())));
            thresh.setWidth(numWidth-50);
            ll.addView(thresh);

            // Create depletion rate
            TextView deplete = new TextView(this);
            deplete.setText("10/day");
            deplete.setWidth(numWidth);
            ll.addView(deplete);



            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Change threshold");
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
            btn.setOnClickListener(new View.OnClickListener() {
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


    private void launchSetThresholdPage(View view, FoodItem food){


        // create and launch intent
        final Intent setThresh = new Intent(changeThreshold.this, SetThreshold.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("food", food);
        setThresh.putExtra("bundle", bundle);
        startActivity(setThresh);
    }



    private void launchFoodItemPage(View view, ArrayList<FoodItem> foods, String foodName){


        // create and launch intent
        final Intent launchFood = new Intent(changeThreshold.this,food_item_page.class);

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

