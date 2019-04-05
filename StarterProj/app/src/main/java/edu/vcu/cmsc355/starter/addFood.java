package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addFood extends AppCompatActivity {

    private static final String TAG = "addFoodPage";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_EXPR = "exprDate";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_THRESHOLD = "threshold";
    private static final String KEY_LOCATION = "location";

    private EditText name;
    private EditText size;
    private EditText expr;
    private EditText quan;
    private EditText thresh;
    private EditText loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
    }

    public void add(View view){

    }
}
