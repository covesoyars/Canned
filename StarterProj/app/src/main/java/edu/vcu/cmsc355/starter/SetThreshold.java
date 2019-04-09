package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SetThreshold extends AppCompatActivity {

    private FoodItem food;
    private TextView name;
    private EditText thresh;
    private Button inc;
    private Button dec;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_threshold);

        //Get food from previous activity
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        food = (FoodItem) foodBundle.getSerializable("food");

        name = (TextView)findViewById(R.id.textView23);
        name.setText(food.getName());

        thresh = (EditText)findViewById(R.id.editText28);
        thresh.setText(food.getThreshold());

        inc = (Button) findViewById(R.id.button11);


        dec = (Button) findViewById(R.id.button17);


        save = (Button) findViewById(R.id.button18);

    }

    public void increment(){
        int newThresh = Integer.parseInt(thresh.getText().toString())+1;
        thresh.setText(newThresh);
    }

    public void decrement(){
        int newThresh = Integer.parseInt(thresh.getText().toString())-1;
        thresh.setText(newThresh);
    }

    public void save(){
    //Push new thresh to the database, how tho

        finish();
    }
}
