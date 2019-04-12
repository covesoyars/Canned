package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class SetThreshold extends AppCompatActivity {

    private FoodItem food;
    private TextView name;
    private EditText thresh;
    private Button inc;
    private Button dec;
    private Button save;
    private String foodName;
    private String foodCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_threshold);

        //Get food from previous activity
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        food = (FoodItem) foodBundle.getSerializable("food");

        name = (TextView)findViewById(R.id.textView23);
        foodName = food.getName();
        foodCat = food.getCategory();
        name.setText(food.getName());

        thresh = (EditText)findViewById(R.id.editText28);
        thresh.setText(String.valueOf(food.getThreshold()));

        inc = (Button) findViewById(R.id.button11);
        inc.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v)
              {
                  increment();
               }
          });

        dec = (Button) findViewById(R.id.button17);
        dec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                decrement();
            }
        });


        save = (Button) findViewById(R.id.button18);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                save();
            }
        });


    }

    public void increment(){
        int newThresh = Integer.parseInt(thresh.getText().toString())+1;
        thresh.setText(String.valueOf(newThresh));
    }

    public void decrement(){
        int newThresh = Integer.parseInt(thresh.getText().toString())-1;
        thresh.setText(String.valueOf(newThresh));
    }

    public void save(){
    //Push new thresh to the database, how tho
        final String newThresh = (thresh.getText().toString());
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("foodItems");
        ref.whereEqualTo("name", foodName).whereEqualTo("category", foodCat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot q = task.getResult();

                    if(!q.isEmpty()){
                        for(QueryDocumentSnapshot  document : task.getResult()){
                            DocumentReference ref = document.getReference();
                            ref.update("threshold", newThresh);
                        }
                    }
                }
            }
        });
        finish();
    }
}
