package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    private EditText name;
    private EditText size;
    private EditText expr;
    private EditText quan;
    private EditText cat;
    private EditText loc;
    private FoodItem simliar;

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
        FirebaseApp.initializeApp(this);
    }

    public void add(View view){
        String n = name.getText().toString().trim();
        String s = size.getText().toString().trim();
        String e = expr.getText().toString().trim();
        String q = quan.getText().toString().trim();
        String c = cat.getText().toString().trim();
        String l = loc.getText().toString().trim();
        simliar=getSimliar();
        String t = getThres(simliar);
        String counter = getCounter(simliar);
        String delpetion = getDepletion(simliar);
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("foodsItems");

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_NAME, n);
        note.put(KEY_SIZE, s);
        note.put(KEY_EXPR, e);
        note.put(KEY_QUANTITY, q);
        note.put(KEY_CATEGORY, c);
        note.put(KEY_LOCATION, l);
        note.put(KEY_THRESHOLD, t);
        note.put(KEY_DEPLETION,delpetion);
        note.put(KEY_COUNTER,counter);

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
    }

    public void selectDonor(View view){
        Intent sel = new Intent(this, DonorList.class);
        startActivity(sel);
    }

    //SEARCG THROUGH DATABASE AND IF SOMETHING HAS THE SAME NAME GET ITS THRESHOLD AND SET CURRENT
    //FOOD OBJECT THRESHOLD TO THE ONES IN THE DATA BASE
    public FoodItem getSimliar() {
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("foodsItems");         //change this back to fooditems
        final FoodItem simliar = new FoodItem();
        final ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();

                    if (!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            FoodItem item = new FoodItem();

                            item.setCategory(document.getData().get("category").toString());
                            item.setName(document.getData().get("name").toString());
                            item.setThreshold(Integer.parseInt(document.getData().get("threshold").toString()));
                            item.setCounter(Integer.parseInt(document.getData().get("counter").toString()));
                            item.setDepletion(Integer.parseInt(document.getData().get("depletion").toString()));
                           foodList.add(item);




                            Log.d(TAG,  " ==============> " + document.getData() + " " + item.getDepletion());

                        }

                        for(FoodItem item : foodList) {
                            //IF FOOD ITEM NAME AND CATIGORY MATCHES

                            Log.d(TAG, "item value " + item.getName() + " "+ item.getDepletion() + " =====> " + item.getCategory() );
                            if (item.getCategory().equals(cat.getText().toString().trim()) && item.getName().equals(name.getText().toString().trim())) {

                                simliar.setCounter(item.getCounter());
                                simliar.setDepletion(item.getDepletion());
                                simliar.setThreshold(item.getThreshold());

                                Log.d(TAG, "FOUND ONE " + item.getName() + " "+ item.getDepletion() + " => " + item.getLocation());
                                break;
                            }

                        }










                    } Log.d(TAG, simliar.getDepletion() + " =====D " + simliar.getThreshold());


                }

            }


        });
        Log.d(TAG, simliar.getDepletion() + " =====> " + simliar.getThreshold());
        return simliar;
    }

    public String getCounter(FoodItem simliar){
        return simliar.getCounter()+"";
    }
    public String getThres(FoodItem simliar){
        return simliar.getThreshold()+"";
    }
    public String getDepletion(FoodItem simliar){
        return simliar.getDepletion()+"";
    }


}