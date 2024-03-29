package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class changeThreshold extends AppCompatActivity {

    ArrayList<FoodItem> testFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_threshold);

        testFoods = new ArrayList<>();

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScroll2);
        final int numWidth =getResources().getDimensionPixelSize(R.dimen._50sdp);
        final int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("foodItems");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();

                    if(!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            FoodItem item = new FoodItem();

                            item.setCategory(document.getData().get("category").toString());
                            item.setName(document.getData().get("name").toString());
                            item.setQuantity(Integer.parseInt(document.getData().get("quantity").toString()));
                            item.setThreshold(Integer.parseInt(document.getData().get("threshold").toString()));
                            item.setSize(document.getData().get("size").toString());
                            testFoods.add(item);


                        }
                        selectionSort(testFoods);
                        createButtons(testFoods, lm, numWidth, nameWidth);
                    }
                    else{
                    }
                }
                else{
                }
            }
        });
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

    public void createButtons(ArrayList<FoodItem> testFoods, LinearLayout lm, int numWidth, int nameWidth){
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
                j=j+1;
                quantity=quantity+food.getQuantity();
                food = testFoods.get(j);
                Log.d("quantity", food.getName() +" value is :"+ quantity);
            }



            ll.setLayoutParams(params);
            // Create TextView
            TextView name = new TextView(this);
            name.setText(food.getName());
            name.setWidth(nameWidth);
            ll.addView(name);

            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(String.valueOf(quantity+food.getQuantity()));
            if(food.getQuantity() < food.getThreshold()){
                quantity2.setTextColor(Color.RED);
            }
            quantity2.setWidth(numWidth);
            ll.addView(quantity2);

            // Create TextView
            TextView size = new TextView(this);
            size.setText((food.getSize()));
            size.setWidth(numWidth);
            ll.addView(size);

            // Create TextView
            TextView thresh = new TextView(this);
            thresh.setText((String.valueOf(food.getThreshold())));
            thresh.setWidth(numWidth);
            ll.addView(thresh);



            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Edit");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            //btn.setRight(0);

            // btn.setWidth(BUTTON_SIZE);


//            RelativeLayout.LayoutParams btnlocation = (RelativeLayout.LayoutParams) btn.getLayoutParams();
//            btnlocation.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            btn.setLayoutParams(btnlocation);



            final int index = j;
            final FoodItem foodToSend = food;
            final ArrayList<FoodItem> t = testFoods;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // launch set thrshold page here:
                    launchSetThresholdPage(btn, foodToSend);
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
            quantity=0;
        }
    }
}

