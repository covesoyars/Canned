package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class addFood extends AppCompatActivity {

    private static final String TAG = "addFoodPage";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_EXPR = "exprDate";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_THRESHOLD = "threshold";
    private static final String KEY_LOCATION = "location";

    private EditText name;
    private EditText size;
    private EditText expr;
    private EditText quan;
    private EditText thresh;
    private EditText loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        name = (EditText) findViewById(R.id.editText3);
        size = (EditText) findViewById(R.id.editText6);
        expr = (EditText) findViewById(R.id.editText7);
        quan = (EditText) findViewById(R.id.editText24);
        thresh = (EditText) findViewById(R.id.editText8);
        loc = (EditText) findViewById(R.id.editText26);
        FirebaseApp.initializeApp(this);
    }

    public void add(View view){
        String n = name.getText().toString().trim();
        String s = size.getText().toString().trim();
        String e = expr.getText().toString().trim();
        String q = quan.getText().toString().trim();
        String t = thresh.getText().toString().trim();
        String l = loc.getText().toString().trim();

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("foodItems");

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_NAME, n);
        note.put(KEY_SIZE, s);
        note.put(KEY_EXPR, e);
        note.put(KEY_QUANTITY, q);
        note.put(KEY_THRESHOLD, t);
        note.put(KEY_LOCATION, l);

        users.document(n).set(note)
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
}
