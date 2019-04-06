package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class ManagerInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_inventory);


    }


    public void lowStock(View v){
        Intent launchLowStock = new Intent(this,Below_Threshold_Page.class);
        startActivity(launchLowStock);
    }
    public void threshold(View v){
        Intent launchThreshold = new Intent(this,changeThreshold.class);
        startActivity(launchThreshold);
    }
}
