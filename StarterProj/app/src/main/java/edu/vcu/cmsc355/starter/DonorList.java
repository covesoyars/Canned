package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DonorList extends AppCompatActivity {
    private static final String TAG = "DonorList";
    private ArrayList<Donor> dons = new ArrayList<Donor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("donors");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();
                    /*
                    at some point we need to sort this query so that all unverified users get put in first
                    then everything should be sorted alphabeically
                    -Javier
                     */


                    if(!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String f = document.getData().get("first").toString();
                            String l = document.getData().get("last").toString();
                            String e = document.getData().get("email").toString();
                            String p = document.getData().get("phone").toString();
                            String a = document.getData().get("address").toString();

                            Donor d = new Donor(f,l,e,p,a);
                            dons.add(d);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Log.d(TAG, "user is " + (d.getFirstName() + " " + d.getLastName()));
                        }
                        //createButtons();
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
    }

    public void createDonor(View view){
        Intent rec = new Intent(this, createDonor.class);
        startActivity(rec);
    }
}
