package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
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

public class removeLocation extends AppCompatActivity {
    private static final String TAG = "locations";
    private static final ArrayList<locations> locationsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final LinearLayout lm = findViewById(R.id.locationLiniarLayout);
        final int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);
        final int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);

        // make a list of food items to test display

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference locations = db.collection("locations");

        locations.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();

                    if (!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //WORK YOU MAGIC SAM

                        }
                        createButtons(locationsArrayList, lm, numWidth, nameWidth);
                    } else {
                        Log.d(TAG, "food not found");
                    }
                } else {
                    Log.d(TAG, "something went wrong");
                }
            }
        });
    }


    public void createButtons(ArrayList<locations> locations, LinearLayout lm, int numWidth, int nameWidth) {



        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


        String currentCatigory = "";
        int quantity=0;
        //Create four
        for(int j=0;j<locations.size();j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            locations loc =locations.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);




            ll.setLayoutParams(params);
            // Create TextView
            TextView name = new TextView(this);
            name.setText(loc.getShelf());
            name.setWidth(nameWidth);
            ll.addView(name);

            // Create TextView
            TextView size = new TextView(this);
            size.setText(loc.getSize());
            size.setWidth(numWidth);
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
            final ArrayList<FoodItem> t = testFoods;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    launchFoodItemPage(v,t,foodToSend.getName(), foodToSend.getCategory());

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
            quantity=0;
        }
        testFoods.clear();
    }



    }


}

