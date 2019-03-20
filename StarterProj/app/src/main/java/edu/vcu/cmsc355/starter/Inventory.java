package edu.vcu.cmsc355.starter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Collections;


public class Inventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScroll);

        // make a list of food items to test display
        ArrayList<FoodItem> testFoods = new ArrayList<FoodItem>();
        for(int i =0; i< 20; i++){
            FoodItem item = new FoodItem();
            if(i < 9){
                item.setCategory("Fruit");
                item.setName("apple");
                item.setQuantity(1999);
                item.setThreshold(100000);
                item.setSize("69 g");
            }
            if(i == 10){
                item.setThreshold(12);
                item.setName("Dog Food lol");
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

        //Create four
        for(int j=0;j<20;j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView name = new TextView(this);
            name.setText(testFoods.get(j).getName() + "     ");
            ll.addView(name);

            // Create TextView
            TextView quantity = new TextView(this);
            quantity.setText(String.valueOf(testFoods.get(j).getQuantity()+ "    "));
            if(testFoods.get(j).getQuantity() < testFoods.get(j).getThreshold()){
                quantity.setTextColor(Color.RED);
            }
            ll.addView(quantity);

            // Create TextView
            TextView size = new TextView(this);
            size.setText((testFoods.get(j).getSize() + "    "));
            ll.addView(size);

            // Create TextView
            TextView thresh = new TextView(this);
            thresh.setText((String.valueOf(testFoods.get(j).getThreshold()) + "    "));
            ll.addView(thresh);




            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Edit Item");
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "index :" + index);

                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Index :" + index,
                            Toast.LENGTH_LONG).show();

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);

        }

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
