package edu.vcu.cmsc355.starter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class removeLocation extends AppCompatActivity {
    private static final String TAG = "removeLocation";
    private ArrayList<locations> loc = new ArrayList<locations>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_location);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("locations");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();
                    if(!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String n = document.getData().get("name").toString();
                            String c = document.getData().get("contents").toString();
                            c = c.substring(1,c.length() - 1);
                            String[] con = c.split(",");
                            locations l = new locations(n,con);

                            loc.add(l);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Log.d(TAG, "uhhhhhhh " + c);
                        }
                        //Javier work your magic here bro
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
}
