package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Volenteer_hub_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volenteer_hub_page);
    }

    public void recordDonations(View View)
    {
        Intent rec = new Intent(this, addFood.class);
        startActivity(rec);
    }

    public void inventory(View View)
    {
        Intent inventory = new Intent(this, Inventory.class);
        startActivity(inventory);
    }
    public void settings(View View)
    {
        Intent settings = new Intent(this, Edit_profile.class);
        startActivity(settings);
    }
}
