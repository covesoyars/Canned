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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class Below_Threshold_Page extends AppCompatActivity{
    private static final String TAG = "below_threspage";

    LinearLayout list;
    ArrayList<FoodItem> foods;
    ArrayList<FoodItem> lowStockFood;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.below_threshold_page);
        // set params for linear layouts
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        list = findViewById(R.id.list);
        final int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);
        final int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);


        foods = new ArrayList<FoodItem>();
        lowStockFood = new ArrayList<FoodItem>();

        //Justin started here
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("foodItems");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();



                    if(!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : q) {
                            String cat = document.getData().get("category").toString();
                            String expDate = document.getData().get("exprDate").toString();
                            String loc = document.getData().get("location").toString();
                            String name = document.getData().get("name").toString();
                            int quantity = Integer.parseInt(document.getData().get("quantity").toString());
                            String size = document.getData().get("size").toString();
                            int thresh = Integer.parseInt(document.getData().get("threshold").toString());

                            FoodItem f = new FoodItem(cat, name, size, expDate,quantity, thresh);
                            foods.add(f);

                            Log.d(TAG, "DocumentSnapshot food data: " + document.getData());

                        }
                        Toast.makeText(Below_Threshold_Page.this,String.valueOf(foods.size()),Toast.LENGTH_LONG).show();

                        for(FoodItem item : foods){
                            if(item.getQuantity() <= item.getThreshold()){
                                lowStockFood.add(item);
                            }
                            else if((item.getQuantity() - item.getDepletion() *2) < item.getThreshold() ){
                                lowStockFood.add(item);
                            }
                            Log.d(TAG, "here's ya data: "+ item.toString());
                        }


                        Toast.makeText(Below_Threshold_Page.this,String.valueOf(lowStockFood.size()),Toast.LENGTH_LONG).show();
                        createButtons(numWidth,nameWidth);
                    }

                }
            }
        });

        // add items with low inventory to list that will be displayed







    }

    public void createButtons(int numWidth, int nameWidth){
        // add header here

        // set variables for inside loop:
        String currentCategory = " ";
        int quantity=0;
        selectionSort(lowStockFood); // sort food by category

        for(int j=0;j<lowStockFood.size();j++){


            FoodItem currentFood = lowStockFood.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // if we're passing a new category, print a header:
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
            if(quantity < currentFood.getThreshold()){
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

            // Create TextView
            TextView deplet = new TextView(this);
            thresh.setText((String.valueOf(currentFood.getDepletion()))+"/Day");
            thresh.setWidth(numWidth);
            ll.addView(deplet);

            list.addView(ll);

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
