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
        Intent rec = new Intent(this, recordDonations.class);
        startActivity(rec);
    }
}
