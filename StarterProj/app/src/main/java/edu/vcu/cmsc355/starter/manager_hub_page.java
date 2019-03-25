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
    public void inventory(View View)
    {
        Intent inventory = new Intent(this, Inventory.class);
        startActivity(inventory);
    }
    public void manageVolunteers(View View)
    {
        Intent mv = new Intent(this, manageVolunteers.class);
        startActivity(mv);
    }
    public void addCSV(View view){
        Intent csvPage = new Intent(this, CSV_Page.class);
        startActivity(csvPage);
    }

    public void belowThreshold(View view){
        Intent belowThresholdPage = new Intent(this, Below_Threshold_Page.class);
        startActivity(belowThresholdPage);
    }

    public void back(View View)
    {
       //TURN THIS INTO A LOGOUT METHOD THINGY -Javier
    }
}
