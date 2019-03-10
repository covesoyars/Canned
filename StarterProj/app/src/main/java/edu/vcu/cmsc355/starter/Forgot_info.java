package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Forgot_info extends AppCompatActivity {
    private EditText email;
    private CheckBox user;
    private CheckBox pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_info);
        email = (EditText) findViewById(R.id.editText9);
        user = (CheckBox) findViewById(R.id.checkBox);
        pass = (CheckBox) findViewById(R.id.checkBox3);
    }

    public void sendEmail(View view){
        if(email.getText().length() == 0){
            //Maybe toast to tell them nothing entered

        }


        //isChecked() refers to true.
        if(user.isChecked() && pass.isChecked()){
            //Need to send email and pass change

        }
        else if(user.isChecked()){
            //NEed to send user their username

        }
        else if(pass.isChecked()){
            //Send  user their pass change

        }
        else{
            //Maybe a toast saying they didn't check anything

        }

        //Take them back to the login page
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
