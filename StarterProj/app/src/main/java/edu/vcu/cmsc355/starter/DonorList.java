package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        //ONCE THINGS START WORKING DELETE FROM HERE
        final ArrayList<Donor> testDons = new ArrayList<Donor>();
        for(int i =0; i< 20; i++){
            if(i < 9){

                Donor item = new Donor("Javier", "Moreira","imxavi", "911","your mums bum");
                testDons.add(item);
            }
            else if(i == 10){
                Donor item = new Donor("Nick", "asGAQAg","idk", "123456","nowhere to be seen");
                testDons.add(item);
            }
            else{
                Donor item = new Donor("Cove", "Soyars","coveyy", "1234567","shipment");
                testDons.add(item);
            }

        }

    //  DELETE TO HERE
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

                        dons=testDons; //  REMOVE THIS ONCE THE DONOR LIST WORKS
                        //JAVI: USE YOUR MAGIC TO UNLOCK THIS METHOD
                        createButtons();
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

    public void createButtons(){

        final LinearLayout lm = (LinearLayout) findViewById(R.id.donorLinearLayout);

        if(((LinearLayout) lm).getChildCount() > 0)
            ((LinearLayout) lm).removeAllViews();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        for(int j = 0; j<dons.size();j++){
            Log.d(TAG, "CHECK DONS " + dons.get(j).getFirstName());
        }

        for(int j=0;j<dons.size();j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            final Donor c =dons.get(j);



            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // screen size universal sizes for fields:
            int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);
            int nameWidth = getResources().getDimensionPixelSize(R.dimen._65sdp);


            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(c.getFirstName());
            quantity2.setWidth(nameWidth);
            ll.addView(quantity2);

            // Create TextView
            TextView size = new TextView(this);
            size.setText(c.getLastName());
            size.setWidth(nameWidth);
            ll.addView(size);

            // Create TextView
            TextView name = new TextView(this);
            String n = c.getPhoneNum();
            name.setText(n);
            name.setWidth(nameWidth);
            ll.addView(name);

            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("PICK");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            //btn.setRight(0);
            btn.setGravity(Gravity.CENTER);
            btn.setX(100);

            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  //STALLING FO RHOW WERE ACTUALLY GONNA DO THIS
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }
    }
}
