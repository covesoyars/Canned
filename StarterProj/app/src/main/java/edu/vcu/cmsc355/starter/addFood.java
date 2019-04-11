package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class addFood extends AppCompatActivity {

    private static final String TAG = "addFoodPage";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_EXPR = "exprDate";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_THRESHOLD = "threshold";
    private static final String KEY_COUNTER = "counter";
    private static final String KEY_DEPLETION = "depletion";
    private ArrayList<FoodItem> foods = new ArrayList<FoodItem>();

    private EditText name;
    private EditText size;
    private EditText expr;
    private EditText quan;
    private EditText cat;
    private EditText loc;
    public EditText donorGuyPick;
    private static final FoodItem simliar = new FoodItem();
    private Donor pickDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        name = (EditText) findViewById(R.id.editText3);
        size = (EditText) findViewById(R.id.editText6);
        expr = (EditText) findViewById(R.id.editText7);
        quan = (EditText) findViewById(R.id.editText24);
        cat = (EditText) findViewById(R.id.editText8);
        loc = (EditText) findViewById(R.id.editText26);
        donorGuyPick = (EditText) findViewById(R.id.editText27);
        FirebaseApp.initializeApp(this);
    }

    public void add(View view){
       // if(donorGuyPick.getText().toString().contains("@")) {
            String n = name.getText().toString().trim();
            String s = size.getText().toString().trim();
            String e = expr.getText().toString().trim();
            String q = quan.getText().toString().trim();
            String c = cat.getText().toString().trim();
            String l = loc.getText().toString().trim();
            getSimliar();
            Log.d(TAG, " FINAL SIMLIAR " + simliar.getDepletion() + " " + simliar.getCounter());

            String t = simliar.getThreshold() + "";
            String counter = simliar.getCounter() + "";
            String delpetion = simliar.getDepletion() + "";
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference users = db.collection("foodItems");

            Map<String, Object> note = new HashMap<>();
            note.put(KEY_NAME, n);
            note.put(KEY_SIZE, s);
            note.put(KEY_EXPR, e);
            note.put(KEY_QUANTITY, q);
            note.put(KEY_CATEGORY, c);
            note.put(KEY_LOCATION, l);
            note.put(KEY_THRESHOLD, t);
            note.put(KEY_DEPLETION, delpetion);
            note.put(KEY_COUNTER, counter);

            users.document().set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(addFood.this, "Food added", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addFood.this, "Food could not be added", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        //}
       // else { Toast.makeText(addFood.this, "Donor was not selected, must select one before adding.", Toast.LENGTH_SHORT).show(); }
    }

    public void selectDonor(View view){
        Intent sel = new Intent(this, DonorList.class);
        startActivity(sel);
    }

    //SEARCG THROUGH DATABASE AND IF SOMETHING HAS THE SAME NAME GET ITS THRESHOLD AND SET CURRENT
    //FOOD OBJECT THRESHOLD TO THE ONES IN THE DATA BASE
    public void getSimliar() {
            FoodItem none = new FoodItem();
            name = (EditText) findViewById(R.id.editText3);
            String s = name.getText().toString();
            foods = new ArrayList<FoodItem>();


            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // call to this object when making queries
            CollectionReference usersRef = db.collection("foodItems");


        // TODO  CHECK BOTH name and caregory ARE EQUAL
            usersRef.whereEqualTo("name", name.getText().toString()).whereEqualTo("category", cat.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    // if it didn't crash connecting to firebase
                    if (task.isSuccessful()) {

                        // result of the search
                        QuerySnapshot q = task.getResult();
                    /*
                    at some point we need to sort this query so that all unverified users get put in first
                    then everything should be sorted alphabeically
                    -Javier
                     */

                        // if the result of the search is empty
                        if(!q.isEmpty()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // cast data fields to strings
                                String depletion = document.getData().get("depletion").toString();
                                Log.d(TAG, "data ----->" + document.getData());
                                String counter = document.getData().get("counter").toString();
                                String thres = document.getData().get("threshold").toString();

                                FoodItem f = new FoodItem();
                                f.setDepletion(Integer.parseInt(depletion));
                                f.setCounter(Integer.parseInt(counter));
                                f.setThreshold(Integer.parseInt(thres));
                                foods.add(f);

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, "depetion rate " + f.getDepletion());
                            }
                            FoodItem dummy = new FoodItem();
                            dummy = getFood(foods);
                            simliar.setDepletion(dummy.getDepletion());
                            simliar.setThreshold(dummy.getThreshold());
                            simliar.setCounter(dummy.getCounter());
                            Log.d(TAG, "depleteion: " + simliar.getDepletion());

                        }
                        else{
                            Log.d(TAG, "User not found");
                        }
                    }
                    else{
                        Log.d(TAG, "something went wrong");
                    }
                }
            });

        Log.d(TAG, "array size ---<<< " + foods.size());


    }

    private FoodItem getFood(ArrayList<FoodItem> foods){
        Log.d(TAG,"Size of foods; " + foods.size());
        for(FoodItem f : foods)
        {
            Log.d(TAG, "FOR LOOP" + f.getDepletion() + " " + f.getCounter());
            if(f.getDepletion()!=0 || f.getCounter()!=0)
            {
                Log.d(TAG, "retuned" + f.getDepletion() + " " + f.getCounter());
                return f;
            }
        }
        return new FoodItem();
    }

    //This method is to get the donor that is passed back.
    /*@Override
    public void onResume(){
        super.onResume();
        // put your code here...
     //   Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
     //   pickDonor = (Donor) foodBundle.getSerializable("thisDonor");

    }*/



}