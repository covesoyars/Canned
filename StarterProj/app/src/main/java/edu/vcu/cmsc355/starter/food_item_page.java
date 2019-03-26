package edu.vcu.cmsc355.starter;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class food_item_page extends AppCompatActivity {

    String food;
    TextView topBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_food_item_page);

        // unpack food from Inventory activity:
        Bundle foodBundle = this.getIntent().getExtras();
        food = (String) foodBundle.getString("foodName");
        topBanner = (TextView) findViewById(R.id.food_page_name);
        if(food!= null){
            topBanner.setText(food);

        }



    }

}
