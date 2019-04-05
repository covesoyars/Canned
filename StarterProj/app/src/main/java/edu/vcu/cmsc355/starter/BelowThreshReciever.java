package edu.vcu.cmsc355.starter;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

// reciever of alarm in main activity that sends manager an email and notification with the food that
// is below the threshold level
// main activity will need to only call startAlarm() if the person logging in is a manager
public class BelowThreshReciever extends BroadcastReceiver {

    ArrayList<FoodItem> foods;
    ArrayList<FoodItem> exprFoods;
    ArrayList<FoodItem> lowStockFoods;
    String message;


    private static final String TAG = "below_threspage";

    @Override
    public void onReceive(Context context, Intent intent) {
       message = "Hello,\n\nThe following foods are expiring soon:\n\n";

       foods = new ArrayList<FoodItem>();

        FirebaseApp.initializeApp(context);
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

                            // this should be the actual line to create fooditems
                            // FoodItem f = new FoodItem(cat, name, size, expDate,quantity, thresh);

                            // this line is to test our stuff
                            FoodItem f = new FoodItem("a", "a", "a", "a", 5, 5);
                            foods.add(f);


                        }
                        // grab foods that are expired or below threshold level
                        for(FoodItem item :foods){
                            if(item.isExpired()){
                                exprFoods.add(item);
                            }

                            if(item.getThreshold() > item.getQuantity()){
                                lowStockFoods.add(item);
                            }
                        }
                        for(FoodItem item :exprFoods){ // add expired foods to message
                            message = message + item.getName() + " " + item.getSize() + " " + item.getLocation() + "\n";
                        }

                        message = message + "\n\n";
                        message = message + "The following foods are below their threshold level:\n\n";

                        for(FoodItem item :lowStockFoods){ // add expired foods to message
                            message = message + item.getName() + " " + item.getSize() + " Quantity: " + item.getQuantity()
                                    + "Threshold: "+ item.getThreshold() + "\n";
                        }

                        EmailSender sender = new EmailSender(message,"soyarsc@vcu.edu","postmaster@automail-canned.com","Expring/Low Stock foods");
                        sender.send();



                    }

                }
            }
        });
    }




}
