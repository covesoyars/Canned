package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class Sign_Up_Page extends AppCompatActivity {
    private static final String TAG = "SignUpPage";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATE = "date";
    private static final String KEY_VERIFY = "verify";
    private boolean fb = true;

    private EditText user;
    private EditText pass;
    private EditText passConfirm;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText date;
    private Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile( // regex pattern to validate email addresses
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up__page);
        user = (EditText) findViewById(R.id.editText3);
        pass = (EditText) findViewById(R.id.editText4);
        passConfirm = (EditText) findViewById(R.id.editText5);
        first = (EditText) findViewById(R.id.editText6);
        last = (EditText) findViewById(R.id.editText7);
        email = (EditText) findViewById(R.id.editText24);
        date = (EditText) findViewById(R.id.editText8);
        FirebaseApp.initializeApp(this);
    }
/*

SETS UP BACK BUTTON TO LOGIN PAGE
 */
    public void back(View View)
    {
       finish();
    }

    public void addUser(View view){

        if(verifyInputs()){ // if all fields are valid, create a volunteer with the information
            if(pass.getText().toString().equals(passConfirm.getText().toString())){

            }
            Volunteer newVol = new Volunteer(pass.getText().toString().trim(),
                    user.getText().toString().trim(),
                    first.getText().toString().trim(),
                    last.getText().toString().trim(),
                    Integer.parseInt(date.getText().toString()),
                    email.getText().toString().trim());

            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference users = db.collection("users");

            String u = user.getText().toString().trim();
            String p = pass.getText().toString().trim();
            String f = first.getText().toString().trim();
            String l = last.getText().toString().trim();
            String d = date.getText().toString();
            String e = email.getText().toString().trim();

            Map<String, Object> note = new HashMap<>();
            note.put(KEY_USER, u);
            note.put(KEY_PASS, p);
            note.put(KEY_FIRST, f);
            note.put(KEY_LAST, l);
            note.put(KEY_DATE, d);
            note.put(KEY_EMAIL, e);
            note.put(KEY_VERIFY, false);

            users.document(u).set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Sign_Up_Page.this, "User successfully created", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_Up_Page.this, "User could not be created", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

            //Need to add to database data structure when available.
            Intent verifyPage = new Intent(Sign_Up_Page.this, verify_Page.class );
            // Intent mainActivity = new Intent(this, MainActivity.class);       COMMENTED OUT BY JAVIER
            startActivity(verifyPage);
        }

        else{
            // do nothing (removing this else block makes the app crash!)
        }


    }

    /**
     * Verifies fields in sign up form
     * @return false if a field is invalid and displays a toast with the error
     */
    private boolean verifyInputs(){
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getText().toString().trim());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        fb = true;
                    } else {
                        Log.d(TAG, "No such document");
                        fb = false;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    fb = false;
                }
            }
        });

        if(fb){
            Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(pass.getText().toString().isEmpty()){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getText().toString().isEmpty()){ // WILL NEED TO VERIFY IF USERNAME IS ALREADY IN DB!
            Toast.makeText(this, "Username field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(first.getText().toString().isEmpty()){
            Toast.makeText(this, "First name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(last.getText().toString().isEmpty()){
            Toast.makeText(this, "Last name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        // test if email is a valid email address:
        if(!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString().trim()).matches()){
            Toast.makeText(this, "Email address is not a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(date.getText().toString().trim().length() != 6){ // DOB is invalid if it's not of length 6
            Toast.makeText(this, "DOB is not in correct format: MMDDYY", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
