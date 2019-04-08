/**
 *
 * Page to display the volunteer info
 * to the manager.
 *
 * Created by: Justin Nelson
 *
 * Edited by: Justin Nelson, Cove Soyars
 */


package edu.vcu.cmsc355.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class ManagerEditVolunteer extends AppCompatActivity {
    private static final String TAG = "SignUpPage";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATE = "date";
    private static final String KEY_VERIFY = "verify";

    private TextView name;
    private TextView email;
    private TextView userName;
    private TextView dob;
    private ImageView pfp;
    private Volunteer thisGuy;
    private CheckBox verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_volunteer);

        // unpack food from Inventory activity:
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        thisGuy = (Volunteer) foodBundle.getSerializable("thisGuy");

        name = (TextView) findViewById(R.id.textView17);
        name.setText(thisGuy.getFirstName() + " " + thisGuy.getLastName());
        email = (TextView) findViewById(R.id.textView24);
        email.setText(thisGuy.getEmailAddress());
        userName = (TextView) findViewById(R.id.textView19);
        userName.setText(thisGuy.getUserName());

        dob = (TextView) findViewById(R.id.textView26);
        dob.setText(String.valueOf(thisGuy.getDob()));

        pfp = (ImageView) findViewById(R.id.pfp);
        Uri newPic = thisGuy.getProfilePicture();
        pfp.setImageURI(newPic);

       
        FirebaseApp.initializeApp(this);
    }

    // on click for remove button
    public void remove(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Remove volunteer?")
                .setMessage("Are you sure you want to remove this volunteer?")
                .setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // remove jimbo from volunteer database
                        removeVolunteer(thisGuy);
                        finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void verify(View view){
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("users2");

        String u = thisGuy.getUserName();
        String p = thisGuy.getPassword();
        String f = thisGuy.getFirstName();
        String l = thisGuy.getLastName();
        String d = thisGuy.getDob() + "";
        String e = thisGuy.getEmailAddress();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_USER, u);
        note.put(KEY_PASS, p);
        note.put(KEY_FIRST, f);
        note.put(KEY_LAST, l);
        note.put(KEY_DATE, d);
        note.put(KEY_EMAIL, e);
        note.put(KEY_VERIFY, true);

        users.document(u).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManagerEditVolunteer.this, "Verified", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagerEditVolunteer.this, "Something broke", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    private void removeVolunteer(Volunteer thisGuy){
        // remove volunteer from database with information that matches thisGuy
    }
}