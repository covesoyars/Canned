package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
    }

    public void signUp(View view){
    Intent startSignUp = new Intent(this, Sign_Up_Page.class);
    startActivity(startSignUp);
    }

    public void goToTest(View view){
        Intent picTest = new Intent(this, PicTest.class);
        startActivity(picTest);
    }

    public void forgotInfo(View view){
        Intent startForgotInfo = new Intent(this, Forgot_info.class);
        startActivity(startForgotInfo);
    }

    public void login(View view){
        Intent login = new Intent(this, manager_hub_page.class);
        //Intent volLogin = new Intent(this, volunteer_hub_page.class);
        if(user.getText().toString().equals("CoolKid123") && pass.getText().toString().equals("CoolPass")) {

            File profilePic = new File("/StarterProj/app/src/main/res/drawable/doug.png");
            User loggedInUser = new Volunteer("CoolPass", // create user object (will pull
                    // user with matching pw and username when database is working
                    "CoolKid123",
                    "Doug",
                    "Dimmadome",
                    123456,
                    "doug.dimmadome@dimmsdaledimmadome.com",
                    profilePic);
            UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
            appState.setLoggedIn(loggedInUser);
            startActivity(login);
        }
        else{
            //Tell user they are stupid
        }
    }
}
