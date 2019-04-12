package edu.vcu.cmsc355.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

// calculates depletion rate every day at midnight
public class DepletionReciever extends BroadcastReceiver {

    private static final String KEY_COUNTER = "counter";
    private static final String KEY_DEPLETION = "depletion";

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("task: ", "successful");
        FirebaseApp.initializeApp(context);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
      final   CollectionReference foods = db.collection("foodItems");

        foods.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // if it didn't crash connecting to firebase
                if (task.isSuccessful()) {

                    // result of the search
                    QuerySnapshot q = task.getResult();


                    // if the result of the search is not empty
                    if (!q.isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            int counter = Integer.parseInt(document.getData().get(KEY_COUNTER).toString());

                            foods.document(document.getId()).update(KEY_DEPLETION, (counter+""));
                            foods.document(document.getId()).update(KEY_COUNTER, (0+""));
                            Log.d("depletion recevier", "update code ran");

                        }
                    } else {

                    }
                } else {

                }

            }
        });
    }
}
