package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class chooseDonor extends AppCompatActivity {
    private static final String TAG = "chooseDonor";
    private ArrayList<Donor> dons = new ArrayList<Donor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_donor);

    }
    public void createDonor(View view){
        Intent makeDonar = new Intent(this, createDonor.class);
        startActivity(makeDonar);
    }
}
