package edu.vcu.cmsc355.starter;
import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Below_Threshold_Page extends AppCompatActivity{

    LinearLayout list;
    ArrayList<FoodItem> foods;
    ArrayList<FoodItem> lowStockFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.below_threshold_page);
        // set params for linear layouts
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        list = findViewById(R.id.list);

        // set text view sizes (universal for all screens)
        int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);
        int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);


        // add header here

        // set variables for inside loop:
        String currentCategory = " ";
        int quantity=0;

        for(int j=0;j<lowStockFood.size();j++){

            FoodItem currentFood = lowStockFood.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // if we're passing a new category, print a header:
            //ad catigory soace
            if(!currentFood.getCategory().equals(currentCategory))
            {
                currentCategory=currentFood.getCategory();
                TextView catigory = new TextView(this);
                catigory.setText( currentCategory );
                catigory.setGravity(Gravity.CENTER);
                catigory.setTextSize(20);
                catigory.setWidth(ll.getWidth());
                catigory.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                catigory.setTextColor(Color.WHITE);
                list.addView(catigory);
            }

            // stack items with the same name:
            while(j<lowStockFood.size()-1 && currentFood.getName().equals(lowStockFood.get(j+1).getName()))
            {
                j++;
                quantity=quantity+currentFood.getQuantity();
            }

            // display food item:
            ll.setLayoutParams(params);

            TextView name = new TextView(this);
            name.setText(currentFood.getName());
            name.setWidth(nameWidth);
            ll.addView(name);

            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(String.valueOf(quantity+currentFood.getQuantity()));
            if(currentFood.getQuantity() < currentFood.getThreshold()){
                quantity2.setTextColor(Color.RED);
            }
            quantity2.setWidth(numWidth);
            ll.addView(quantity2);

            // Create TextView
            TextView size = new TextView(this);
            size.setText((currentFood.getSize()));
            size.setWidth(numWidth);
            ll.addView(size);

            // Create TextView
            TextView thresh = new TextView(this);
            thresh.setText((String.valueOf(currentFood.getThreshold())));
            thresh.setWidth(numWidth);
            ll.addView(thresh);

        }


        // SAM : pull all items from the food database that have quantities below their threshold level:


    }

}
