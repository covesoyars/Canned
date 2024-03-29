package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;


import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class    Edit_profile extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_PASS = "pass";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATE = "date";

    private File newPicture;
    private EditText lastName;
    private EditText firstName;
    private EditText dob;
    private EditText email;
    private EditText password;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private CircleImageView profilePic;
    private static final int PICK_IMAGE = 1;
    private User loggedIn;
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
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // grab logged in user's information:
        UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
        loggedIn = appState.getLoggedIn();
        newPicture = null;
        // declare buttons and set their text to the user's information
        lastName = (EditText) findViewById(R.id.editText16);
        lastName.setText(loggedIn.getLastName());
        firstName = (EditText) findViewById(R.id.editText15);
        firstName.setText(loggedIn.getFirstName());
        dob = (EditText) findViewById(R.id.editText10);
        dob.setText(Integer.toString(loggedIn.getDob()));
        email = (EditText) findViewById(R.id.editText12);
        email.setText(loggedIn.getEmailAddress());
        password = (EditText) findViewById(R.id.editText14);
        newPassword = (EditText) findViewById(R.id.editText11);
        confirmNewPassword = (EditText) findViewById(R.id.editText13);

      //  profilePic = (CircleImageView) findViewById(R.id.circleImageView);
       // Uri profileUri = loggedIn.getProfilePicture();
       // profilePic.setImageURI(profileUri);
        /*try {
            Bitmap imageMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profileUri);
            imageMap = Bitmap.createScaledBitmap(imageMap,  600 ,600, true);
            profilePic.setImageBitmap(imageMap);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();

        }*/

       // profilePic.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {
            Uri imageGrab = data.getData();
            profilePic.setImageURI(imageGrab);
            profilePic.setRotation(90);
            newPicture = new File(imageGrab.toString());
        }
    }

    public void save(View View) {
        if (verifyInputs(loggedIn)) {
            loggedIn.setEmailAddress(email.getText().toString());
            loggedIn.setFirstName(firstName.getText().toString());
            loggedIn.setLastName(lastName.getText().toString());
            if (newPicture != null) {
                loggedIn.setProfilePicture(newPicture);
            }
            verifyNewPassword(loggedIn);

        // push changes to database:
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference users = db.collection("users2");

            users.whereEqualTo("user", loggedIn .getUserName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                               myRef.update(KEY_DATE, loggedIn.getDob());
                               myRef.update(KEY_EMAIL, loggedIn.getEmailAddress());
                               myRef.update(KEY_FIRST, loggedIn.getFirstName());
                               myRef.update(KEY_LAST, loggedIn.getLastName());
                               myRef.update(KEY_PASS, loggedIn.getPassword());

                            }
                        } else {

                        }
                    } else {

                    }
                    // update currently logged in user and finish activity
                    UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
                    appState.setLoggedIn(loggedIn);
                    finish();
                }
            });


        }

    }

    /**
     * Verifies fields in sign up form
     *
     * @return false if a field is invalid and displays a toast with the error
     */
    private boolean verifyInputs(User loggedIn) {

        if (firstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "First name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Last name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        // test if email is a valid email address:
        if (!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Email address is not a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        // verify that new passwords match
        if (!newPassword.getText().toString().trim().equals(confirmNewPassword.getText().toString().trim())) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dob.getText().toString().trim().length() != 6) { // DOB is invalid if it's not of length 6
            Toast.makeText(this, "DOB is not in correct format: MMDDYY", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void verifyNewPassword(User loggedIn) {
        if (password.getText().toString().equals(loggedIn.getPassword())) {
            if (newPassword.getText().toString().equals(confirmNewPassword.getText().toString()) &&
                    !newPassword.getText().toString().isEmpty()) {
                loggedIn.setPassword(newPassword.getText().toString());
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
