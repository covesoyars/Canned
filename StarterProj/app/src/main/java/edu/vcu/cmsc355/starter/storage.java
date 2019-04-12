package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class storage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
    }

    public void add(View view){
        Intent add = new Intent(this, addLocation.class);
        startActivity(add);
    }

    public void remove(View view){
        Intent remove = new Intent(this, removeLocation.class);
        startActivity(remove);
    }
}
