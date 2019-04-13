package edu.vcu.cmsc355.starter;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class removeLocation extends AppCompatActivity {

    private static final String TAG = "removeLocation";
    private ArrayList<locations> loc = new ArrayList<>();
    private ArrayList<DocumentSnapshot> docID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remove_location);

       final LinearLayout lm = findViewById(R.id.locationLiniarLayout);
        final int numWidth = getResources().getDimensionPixelSize(R.dimen._50sdp);
        final int nameWidth = getResources().getDimensionPixelSize(R.dimen._75sdp);

        // make a list of food items to test display

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final  CollectionReference locations = db.collection("locations");

        locations.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();

                    if (!q.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String n = document.getData().get("name").toString();
                            String c = document.getData().get("contents").toString();
                            c = c.substring(1, c.length() - 1);
                            String[] con = c.split(",");
                            locations l = new locations(n, con);
                            docID.add(document);
                            loc.add(l);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Log.d(TAG, "uhhhhhhh " + c);
                        }
                        //Javier work your magic here bro
                        createButtons(loc ,lm, numWidth , nameWidth, docID);
                    } else {
                        Log.d(TAG, "User not found");
                    }
                } else {

                    Log.d(TAG, "something went wrong");
                }
            }
        });

    }


    public void createButtons(ArrayList<locations> locations, LinearLayout lm, int numWidth, int nameWidth , final ArrayList<DocumentSnapshot> docIDList) {


        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);




        //Create four
        for (int j = 0; j < locations.size(); j++)  //J is to equal the size of the Foodarray(or whatever it is)
        {
            locations loc = locations.get(j);

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);


            ll.setLayoutParams(params);
            // Create TextView
            TextView name = new TextView(this);
            name.setText(loc.getShelf());
            name.setWidth(nameWidth);
            ll.addView(name);

            // Create TextView
            TextView size = new TextView(this);
            size.setText(loc.getSize()+"");
            size.setWidth(numWidth);
            ll.addView(size);

            // Create TextView
            TextView size2 = new TextView(this);
            size2.setText((loc.getRoomLeft())+"");
            size2.setWidth(numWidth);
            ll.addView(size2);


            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j + 1);
            btn.setText("Remove Item");
            // set the layoutParams on the button
            btn.setLayoutParams(params);
            final int  x = j;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    throwWarning(docIDList.get(x));
                }
            });

            if(lm==null)
            {
                Log.d(TAG, "LM IS NULL");
            }
            else {
                //Add button to LinearLayout
                ll.addView(btn);
                //Add button to LinearLayout defined in XML
                lm.addView(ll);
            }
        }
        loc.clear();
    }


                                                //TODO FIGURE OUT REMOVE AND THEN UPDATE DATABASE
    public void throwWarning(final DocumentSnapshot docID){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Remove location?")
                .setMessage("Are you sure you want to remove location \"" + docID.get("name") + "\" ?")
                .setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // remove jimbo from volunteer database
                        removeLocations(docID);
                        finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void removeLocations(DocumentSnapshot docID)
    {
       docID.getReference().delete();
    }


}




