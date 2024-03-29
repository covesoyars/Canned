package edu.vcu.cmsc355.starter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;

public class manageVolunteers extends AppCompatActivity {
    private static final String TAG = "manageVolunteers";
    private ArrayList<Volunteer> vols = new ArrayList<Volunteer>();
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_volunteers2);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users2");

        // make header:
        LinearLayout header = findViewById(R.id.volunteerHeader);
        // screen size universal sizes for fields:
        int nameWidth = getResources().getDimensionPixelSize(R.dimen._65sdp);

        TextView name = new TextView(this);
        name.setText("Username");
        name.setWidth(nameWidth);
        name.setTextColor(Color.WHITE);
        header.addView(name);

        TextView quantity2 = new TextView(this);
        quantity2.setText("First Name");
        quantity2.setWidth(nameWidth);
        quantity2.setTextColor(Color.WHITE);
        header.addView(quantity2);

        TextView size = new TextView(this);
        size.setText("Last Name");
        size.setWidth(nameWidth);
        size.setTextColor(Color.WHITE);
        header.addView(size);

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
                            String p = document.getData().get("pass").toString();
                            String u = document.getData().get("user").toString();
                            String f = document.getData().get("first").toString();
                            String l = document.getData().get("last").toString();
                            int dob = Integer.parseInt(document.getData().get("date").toString());
                            String e = document.getData().get("email").toString();
                            boolean ver = Boolean.parseBoolean(document.getData().get("verify").toString());

                            Volunteer v = new Volunteer(p,u,f,l,dob,e);
                            if (ver) { v.verify(); }
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

        final LinearLayout lm = (LinearLayout) findViewById(R.id.mainScrollVolenteer);

        if(((LinearLayout) lm).getChildCount() > 0)
            ((LinearLayout) lm).removeAllViews();

        //start playing around with the volunteer arraylist here
        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        /*for(int j = 0; j<vols.size();j++){
            Log.d(TAG, "CHECK USER " + vols.get(j).getUserName());
            Log.d(TAG,"IS VERIFIED? " + vols.get(j).isVerified());
        }*/

        sortByVerified(vols);
        boolean currentStatus = true;

        for(int j=0;j<vols.size();j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            final Volunteer c =vols.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            if(c.isVerified() != currentStatus){
                currentStatus = c.isVerified();
                // print banner
                TextView status = new TextView(this);
                if(currentStatus){
                    status.setText("Verified");
                }
                else{
                    status.setText("Unverified");
                }
                status.setGravity(Gravity.CENTER);
                status.setTextSize(20);
                status.setWidth(ll.getWidth());
                status.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                status.setTextColor(Color.WHITE);
                lm.addView(status);
            }

            // screen size universal sizes for fields:
            int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);
            int nameWidth = getResources().getDimensionPixelSize(R.dimen._65sdp);


            // Create TextView
            TextView name = new TextView(this);
            String n = c.getUserName();
            name.setText(n);
            name.setWidth(nameWidth);
            ll.addView(name);

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


            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j+1);
            btn.setText("View Info");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            //btn.setRight(0);
            btn.setGravity(Gravity.RIGHT);
            btn.setX(100);

            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    launchEditVolPage(v,c);
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }
    }
    private void launchEditVolPage(View view, Volunteer thisGuy){


        // create and launch intent
        final Intent launchEdit = new Intent(manageVolunteers.this,ManagerEditVolunteer.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("thisGuy", thisGuy);
        launchEdit.putExtra("bundle", bundle);
        startActivity(launchEdit);
    }

    public void searchVolunteers(View view){
        search = (EditText) findViewById(R.id.editText25);
        String s = search.getText().toString();
        vols = new ArrayList<Volunteer>();


        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // call to this object when making queries
        CollectionReference usersRef = db.collection("users2");

        // use this to write the query
        // this is where sam's searching error is
        usersRef.whereEqualTo("user", s).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // if it didn't crash connecting to firebase
                if (task.isSuccessful()) {

                    // result of the search
                    QuerySnapshot q = task.getResult();
                    /*
                    at some point we need to sort this query so that all unverified users get put in first
                    then everything should be sorted alphabeically
                    -Javier
                     */

                    // if the result of the search is empty
                    if(!q.isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // cast data fields to strings
                            String p = document.getData().get("pass").toString();
                            String u = document.getData().get("user").toString();
                            String f = document.getData().get("first").toString();
                            String l = document.getData().get("last").toString();
                            boolean verf = Boolean.parseBoolean(document.getData().get("verify").toString());
                            int dob = Integer.parseInt(document.getData().get("date").toString());
                            String e = document.getData().get("email").toString();

                            Volunteer v = new Volunteer(p,u,f,l,dob,e);
                            v.setVerification(verf);
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

    // method to sort a list of volunteers by their verification status
    private static void sortByVerified( ArrayList<Volunteer> list)
    {
        // Find the string reference that should go in each cell of
        // the array, from cell 0 to the end
        for ( int j = 0; j < list.size();j++ )
        {
            // Find min: the index of the string reference that should go into cell j.
            // Look through the unsorted strings (those at j or higher) for the one that is first in lexicographic order
            int min = j;
            for ( int k=j+1; k < list.size(); k++ ) {
                if (list.get(k).compareTo(list.get(min)) > 0) {
                    min = k;
                }

            }

            // Swap the reference at j with the reference at min
            Collections.swap(list, j, min);
        }
    }
}
