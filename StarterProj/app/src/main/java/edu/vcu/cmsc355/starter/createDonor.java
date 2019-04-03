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

import java.util.HashMap;
import java.util.Map;

public class createDonor extends AppCompatActivity {
    private static final String TAG = "CreateDonorPage";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private boolean fb = true;

    private EditText first;
    private EditText last;
    private EditText email;
    private EditText phone;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donor);
        first = (EditText) findViewById(R.id.editText3);
        last = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText7);
        address = (EditText) findViewById(R.id.editText8);
        phone = (EditText) findViewById(R.id.editText24);
        FirebaseApp.initializeApp(this);
    }

    public void addDonor(View view) {
        String f = first.getText().toString().trim();
        String l = last.getText().toString().trim();
        String e = email.getText().toString().trim();
        String p = phone.getText().toString().trim();
        String a = address.getText().toString().trim();

        //Donor d = new Donor()

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("donors");

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_FIRST, f);
        note.put(KEY_LAST, l);
        note.put(KEY_EMAIL, e);
        note.put(KEY_PHONE, p);
        note.put(KEY_ADDRESS, a);


        users.document(e).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(createDonor.this, "User successfully created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(createDonor.this, "User could not be created", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        //Need to add to database data structure when available.
        // Intent verifyPage = new Intent(Sign_Up_Page.this, verify_Page.class );
        // Intent mainActivity = new Intent(this, MainActivity.class);       COMMENTED OUT BY JAVIER
        // startActivity(verifyPage);
    }
}