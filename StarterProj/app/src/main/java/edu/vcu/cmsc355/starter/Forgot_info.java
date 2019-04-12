package edu.vcu.cmsc355.starter;
/**
 * Sends email to user if they forgot information, given the user's email
 * Currently works with test user, will need database implementation to find correct account
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class Forgot_info extends AppCompatActivity {
    private EditText email;
    private CheckBox user;
    private CheckBox pass;
    private Volunteer testUser;
    private User foundHim;
    private static final String KEY_PASS = "pass";

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile( // regex pattern to validate email addresses
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
        setContentView(R.layout.activity_forgot_info);
        email = (EditText) findViewById(R.id.editText9);
        user = (CheckBox) findViewById(R.id.checkBox);
        pass = (CheckBox) findViewById(R.id.checkBox3);
        testUser = new Volunteer(); // test user





    }


    public void sendEmail(View v){

        String subject = subject = "Forgot Canned information?"; // set subject

        if(email.getText().toString().length() == 0){ // if user didn't enter an email,
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;  // tell user they didn't enter anything and end method call
        }

        if(!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString().trim()).matches()) { // if email is not valid,
            Toast.makeText(this, "Email address is not a valid email address", Toast.LENGTH_SHORT).show();
            return; // tell user and end method call
        }

        if(!pass.isChecked() && !user.isChecked()){ // if no boxes are checked,
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return; // tell user they didn't select anything and end method call
        }

        findUser(); // search db for user
        if(foundHim == null){ // if user is not found
            Toast.makeText(Forgot_info.this, "No user with that email found.", Toast.LENGTH_SHORT).show();

            return; // break
        }

        if(user.isChecked() && pass.isChecked()){ // if both boxes are checked,
            foundHim.forgotPassword(); // reset password
            updatePW();
            // create message with new pw and username:
            String forgotPassMessage = "Hello,\n\nYour Canned information is:\n" + foundHim.getUserName() +
                   "\n" + foundHim.getPassword()  + "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, foundHim.getEmailAddress(), "postmaster@automail-canned.com", subject);
            forgotPass.send();

        }
        else if(user.isChecked()){  // if just username box is checked,
            // create message with username:
            String forgotPassMessage = "Hello,\n\nYour Canned username is:\n" + foundHim.getUserName() +
                    "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, foundHim.getEmailAddress(), "postmaster@automail-canned.com", subject);
            forgotPass.send();

        }
        else if(pass.isChecked()){ // if just password box is checked,
            foundHim.forgotPassword(); // reset user's password
            updatePW();
            // create message with new password
            String forgotPassMessage = "Hello,\n\nYour new Canned password is:\n" + foundHim.getPassword() +
                    "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, foundHim.getEmailAddress(), "postmaster@automail-canned.com", subject);
            forgotPass.send();
        }



        //Take them back to the login page
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void findUser(){
        String emailAddress = email.getText().toString();

        // initiaize fb:
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("users2");

        users.whereEqualTo("email", emailAddress).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // if it didn't crash connecting to firebase
                if (task.isSuccessful()) {

                    // result of the search
                    QuerySnapshot q = task.getResult();

                    // if the result of the search is not empty
                    if (!q.isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String p = document.getData().get("pass").toString();
                            String u = document.getData().get("user").toString();
                            String f = document.getData().get("first").toString();
                            String l = document.getData().get("last").toString();
                            int dob = Integer.parseInt(document.getData().get("date").toString());
                            String e = document.getData().get("email").toString();

                            foundHim =  new User(p,u,f,l,dob,e);
                        }
                    } else {

                    }
                } else {
                }

            }
        });

        }

        public void updatePW(){
            // updates user's password in db
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference users = db.collection("users2");

            users.whereEqualTo("user",foundHim.getUserName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    // if it didn't crash connecting to firebase
                    if (task.isSuccessful()) {

                        // result of the search
                        QuerySnapshot q = task.getResult();

                        // if the result of the search is not empty
                        if (!q.isEmpty()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference myRef = document.getReference();
                                myRef.update(KEY_PASS, foundHim.getPassword());

                            }
                        } else {

                        }
                    } else {

                    }

                }
            });

        }
}
