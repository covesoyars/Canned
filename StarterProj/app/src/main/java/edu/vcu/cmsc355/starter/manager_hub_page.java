package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class manager_hub_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_hub_page);


    }
    public void settings(View View)
    {
        Intent settings = new Intent(this, Edit_profile.class);
        startActivity(settings);
    }
    public void back(View View)
    {
       //TURN THIS INTO A LOGOUT METHOD THINGY -Javier
    }
}