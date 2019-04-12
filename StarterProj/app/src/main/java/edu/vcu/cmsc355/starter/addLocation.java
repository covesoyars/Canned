package edu.vcu.cmsc355.starter;

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

public class addLocation extends AppCompatActivity {
    private static final String TAG = "addLocation";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTENTS = "contents";

    private EditText name;
    private EditText capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        name = (EditText) findViewById(R.id.editText17);
        capacity = (EditText) findViewById(R.id.editText29);
        FirebaseApp.initializeApp(this);
    }

    public void addStuff(View view){
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("locations");

        String n = name.getText().toString().trim();
        int c = Integer.parseInt(capacity.getText().toString().trim());
        String con[] = new String[c];

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_NAME, n);
        note.put(KEY_CONTENTS, Arrays.asList(con));

        users.document(n).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addLocation.this, "Location added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addLocation.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
