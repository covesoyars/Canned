package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class Edit_profile extends AppCompatActivity {

    private EditText lastName;
    private EditText firstName;
    private EditText dob;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // grab logged in user's information:
        UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
        User loggedIn = appState.getLoggedIn();
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
        password.setText(loggedIn.getPassword());



    }
    public void back(View View)
    {
        finish();


    }

}
