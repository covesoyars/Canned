package edu.vcu.cmsc355.starter;

/***
 * Class to hold information about food items
 *
 * Not finished
 *
 * contributors: Justin Nelson,
 */

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
                /**
                 * Here Sam, I need to pull the current list of fooditems
                 * Then at the end I need to update the list with the new stuff.
                 *
                 * You can just place it in here and I will move it to where it needs to go,
                 * just comment it.
                 */
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
                        food.setSize(scanzor.next());
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
                    fileName.setText(e.getMessage());
                }

            }
        });
    }
}
