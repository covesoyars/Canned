package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

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

    public void forgotInfo(View view){
        Intent startforgotInfo = new Intent(this, Forgot_info.class);
        startActivity(startforgotInfo);
    }

    public void login(View view){
        Intent login = new Intent(this, manager_hub_page.class);
        //Intent volLogin = new Intent(this, volunteer_hub_page.class);

        if(user.equals("CoolKid123") && pass.equals("CoolPass")) {
            startActivity(login);
        }
    }
}
