package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class manageVolunteers extends AppCompatActivity {
    private static final String TAG = "manageVolunteers";
    private ArrayList<Volunteer> vols = new ArrayList<Volunteer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_volunteers2);


        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();
                    if(!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String p = document.getData().get("pass").toString();
                            String u = document.getData().get("user").toString();
                            String f = document.getData().get("first").toString();
                            String l = document.getData().get("last").toString();
                            int dob = Integer.parseInt(document.getData().get("date").toString());
                            String e = document.getData().get("email").toString();

                            Volunteer v = new Volunteer(p,u,f,l,dob,e);
                            vols.add(v);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Log.d(TAG, "user is " + v.getUserName());
                        }
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
    public void createButtons(){

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScroll);

        //start playing around with the volunteer arraylist here
        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        for(int j = 0; j<vols.size();j++){
            Log.d(TAG, "CHECK USER " + vols.get(j).getUserName());
        }

        for(int j=0;j<vols.size();j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            Volunteer c =vols.get(j);



            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);


            // Create TextView
            TextView name = new TextView(this);
            String n = c.getUserName();
            name.setText(n + "     ");
            ll.addView(name);

            // Create TextView
            TextView quantity2 = new TextView(this);
            quantity2.setText(c.getFirstName() + "     ");
            ll.addView(quantity2);

            // Create TextView
            TextView size = new TextView(this);
            size.setText(c.getLastName() + "    ");
            ll.addView(size);


            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("Edit Item");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            //btn.setRight(0);
            btn.setGravity(Gravity.RIGHT);
            btn.setX(100);
//            RelativeLayout.LayoutParams btnlocation = (RelativeLayout.LayoutParams) btn.getLayoutParams();
//            btnlocation.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//            btn.setLayoutParams(btnlocation);



            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "index :" + index);

                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Index :" + index,
                            Toast.LENGTH_LONG).show();

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }
    }
}