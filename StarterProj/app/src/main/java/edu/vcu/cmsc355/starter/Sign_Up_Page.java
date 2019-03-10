package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.File;

public class Sign_Up_Page extends AppCompatActivity {
    private Volunteer newV;
    private EditText user;
    private EditText pass;
    //private EditText passConfirm;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText date;
    private File defaultPicture = new File("/StarterProj/app/src/main/res/drawable/default_profile_picture.jpg");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up__page);
        user = (EditText) findViewById(R.id.editText3);
        pass = (EditText) findViewById(R.id.editText4);
        //passConfirm = (EditText) findViewById(R.id.editText3);
        first = (EditText) findViewById(R.id.editText5);
        last = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText7);
        date = (EditText) findViewById(R.id.editText8);
        newV = new Volunteer();
    }

    public void addUser(View view){
        newV.setUserName(user.getText().toString());
        newV.setDob(Integer.parseInt(date.getText().toString()));
        newV.setEmailAddress(email.getText().toString());
        newV.setFirstName(first.getText().toString());
        newV.setLastName(last.getText().toString());
        newV.setPassword(pass.getText().toString());

        //HOW
        //newV.setProfilePicture();

        //DATABASE.add(newV);
        //However that is done

        //Need to add to database data structure when available.
        Intent verifyPage = new Intent(Sign_Up_Page.this, verify_Page.class );
       // Intent mainActivity = new Intent(this, MainActivity.class);       COMMENTED OUT BY JAVIER
        startActivity(verifyPage);
    }
}
