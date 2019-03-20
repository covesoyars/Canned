package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Scanner;
import java.io.InputStream;

public class CSV_Page extends AppCompatActivity {

    private EditText fileName;
    private Button addFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv__page);

        fileName = (EditText)findViewById(R.id.editText17);
        addFile = (Button)findViewById(R.id.button12);

        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = fileName.getText().toString();
                try{
                    InputStream stream = getAssets().open(file);
                    Scanner scanny = new Scanner(stream);
                    Scanner scanzor = new Scanner(stream);
                    scanzor.useDelimiter(",");
                    /*
                     *Go through the file and add all the foodItems to the list.
                     */
                    while(scanny.hasNextLine()){
                        scanzor = new Scanner(scanny.nextLine());
                        FoodItem food = new FoodItem();
                        food.setCategory(scanzor.next());
                        food.setName(scanzor.next());
                        food.setSize(scanzor.nextDouble());
                        food.setDateRecieved(scanzor.next());
                        food.setExprDate(scanzor.next());
                        food.setQuantity(scanzor.nextInt());
                        food.setThreshold(scanzor.nextInt());

                        //Database.addFoodToFoodList()
                        //When I learn how to
                    }
                    /*int size = stream.available();
                    byte[] buffer = new byte[size];
                    stream.read(buffer);
                    stream.close();*/

                    //Not finished
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
