package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class food_item_page extends AppCompatActivity {

    String food, category;
    TextView topBanner;
    ArrayList<DocumentReference> toBeRemoved;
    ArrayList<FoodItem> foodList = new ArrayList<>();
    ArrayList<FoodItem> foodListToBeRemoved = new ArrayList<>();
    String TAG = "fak";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_food_item_page);
        final LinearLayout lm = (LinearLayout) findViewById(R.id.food_ll);

        //set screen universal text view sizes:
       final int numWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);
       final int nameWidth = getResources().getDimensionPixelSize(R.dimen._115sdp);



        // unpack food from Inventory activity:
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        food =  foodBundle.getString("foodName");
        category = foodBundle.getString("category");
        topBanner = (TextView) findViewById(R.id.food_page_name);
        toBeRemoved = new ArrayList<DocumentReference>();


        topBanner.setText(food);



        FoodItem daddy = new FoodItem();
        daddy.setName(food);
        daddy.setCategory(category);
        Log.d(TAG, "name ->>>>>>> "+ daddy.getName());
        Log.d(TAG, "category ->>>>>>> "+ daddy.getCategory());


      final  ArrayList<DocumentReference> refList = new ArrayList();


        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // call to this object when making queries
        CollectionReference usersRef = db.collection("foodItems");


        // TODO  CHECK BOTH name and caregory ARE EQUAL
        usersRef.whereEqualTo("name", daddy.getName()).whereEqualTo("category", daddy.getCategory()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // if it didn't crash connecting to firebase
                if (task.isSuccessful()) {

                    // result of the search
                    QuerySnapshot q = task.getResult();


                    // if the result of the search is empty
                    if(!q.isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // cast data fields to strings


                           DocumentReference ref =  document.getReference();
                           refList.add(ref);
                           FoodItem food = new FoodItem();
                            food.setCategory(document.getData().get("category").toString());
                            food.setExprDate(document.getData().get("exprDate").toString());
                            food.setLocation(document.getData().get("location").toString());
                            food.setName(document.getData().get("name").toString());
                            food.setQuantity(Integer.parseInt(document.getData().get("quantity").toString()));
                            food.setSize(document.getData().get("size").toString());
                            foodList.add(food);
                            Log.d(food.getName(), "list size -------------------> "+ foodList.size());




                        }

                        //INSERT HERE

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        // make header:
                        LinearLayout header = findViewById(R.id.foodItemHeader);
                        TextView quantityHead = new TextView(food_item_page.this);
                        quantityHead.setText("Quantity");
                        quantityHead.setWidth(numWidth);
                        header.addView(quantityHead);

                        TextView ExprdateHead = new TextView(food_item_page.this);
                        ExprdateHead.setText("Expr. Date");
                        ExprdateHead.setWidth(nameWidth-115);
                        header.addView(ExprdateHead);

                        TextView locHead = new TextView(food_item_page.this);
                        locHead.setText("Location");
                        locHead.setWidth(numWidth);
                        header.addView(locHead);

                        for(int j = 0; j<foodList.size(); j++){

                            final FoodItem currentFood = foodList.get(j);
                            final DocumentReference ref = refList.get(j);
                            // Create LinearLayout
                            LinearLayout ll = new LinearLayout(food_item_page.this);
                            ll.setOrientation(LinearLayout.HORIZONTAL);
                            ll.setLayoutParams(params);

                            // display quantity, expiration date, and location:
                            TextView quantity = new TextView(food_item_page.this);
                            quantity.setText(String.valueOf(currentFood.getQuantity()));
                            quantity.setWidth(numWidth);
                            ll.addView(quantity);

                            TextView exprDate = new TextView(food_item_page.this);
                            exprDate.setText(currentFood.getExprDate());
                            exprDate.setWidth(nameWidth);
                            ll.addView(exprDate);

                            TextView loc = new TextView(food_item_page.this);
                            loc.setText(currentFood.getLocation());
                            exprDate.setWidth(numWidth);
                            ll.addView(loc);

                            TextView spacer = new TextView(food_item_page.this);       //  so i was playing with stuff and i  think playing with the constraints was a better way to do it
                            // lemme know what you think
                            spacer.setWidth(numWidth/2);
                            ll.addView(spacer);




                            final Switch btn = new Switch(food_item_page.this);
                            // Give button an ID
                            btn.setId(j+1);
                            btn.setText("Remove");

                            // set the layoutParams on the button
                            btn.setLayoutParams(params);

                            // Set click listener for button
                          //  final FoodItem foodToSend = currentFood;
                            btn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if(btn.isChecked()){
                                        toBeRemoved.add(ref);
                                        foodListToBeRemoved.add(currentFood);

                                        Log.d(TAG, "if FOODLIST SIZE ->>>>>>>" + foodList.size());
                                        Toast.makeText(food_item_page.this,"added remove list", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        toBeRemoved.remove(ref);
                                        Log.d(TAG, "REMOVED FROM LIST???"+ foodList.contains(currentFood));
                                        foodListToBeRemoved.remove(currentFood);
                                        Log.d(TAG, "FOODLIST SIZE ->>>>>>>" + foodList.size()) ;
                                        Toast.makeText(food_item_page.this,"removed from remove list", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                            ll.addView(btn);
                            lm.addView(ll);


                        }
                    }
                    else{
                        Log.d(TAG, "LIST WAS EMPTY");
                    }
                }
                else{

                }
            }
        });






        //@SAM: We need you to pull all the fooditems that have
        // the name stored in the string variable 'food'





    }

    public void finalize(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Finalize Changes?")
                .setMessage("Are you sure you want to remove these items?")
                .setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // push changes to database (items removed)
                    remove(toBeRemoved);
                    finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void remove(ArrayList<DocumentReference> list) {
         int quanity=0;
         final ArrayList<DocumentReference> list1 = list;
        for(FoodItem temp : foodListToBeRemoved)
        {
            Log.d(TAG, "foodList size ->>>>>>> " + foodListToBeRemoved.size());
            quanity=quanity+temp.getQuantity();
        }
        Log.d(TAG, "QUANTITY COUNT-------> " + quanity);

        final int fullQuantity = quanity;
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference users = db.collection("foodItems");
            users.whereEqualTo("name", foodListToBeRemoved.get(0).getName()).whereEqualTo("category",foodListToBeRemoved.get(0).getCategory()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    // if it didn't crash connecting to firebase
                    if (task.isSuccessful()) {

                        // result of the search
                        QuerySnapshot q = task.getResult();


                        // if the result of the search is empty
                        if (!q.isEmpty()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(list1.contains(document.getReference())) {
                                    document.getReference().delete();// delete the user

                                }

                                else{
                                    //TODO GET QUANITTY THEN ADD FULL QUANTITY
                                    int currentCounter = Integer.parseInt(document.get("counter").toString());
                                   users.document(document.getId()).update("counter", (currentCounter + fullQuantity)+"");
                                    Log.d(TAG, "ID GETTING UPDATES " + document.getId());
                                    Log.d(TAG, "UPDATE ARE CURRENT: "+ currentCounter + "ADDING QUANT" + fullQuantity );
                                }


                            }

                        }




                    }
                    else {

                    }
                }
            });

    }


}
