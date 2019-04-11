package edu.vcu.cmsc355.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class manager_hub_page extends AppCompatActivity {

    // keys for food item map:
    private static final String TAG = "addFoodPage";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_EXPR = "exprDate";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_THRESHOLD = "threshold";
    private static final String KEY_DELPETION="depletion";
    private static final String KEY_COUNTER="counter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_hub_page);
        FirebaseApp.initializeApp(this);


    }
    public void settings(View View)
    {
        Intent settings = new Intent(this, Edit_profile.class);
        startActivity(settings);
    }
    public void inventory(View View)
    {
        Intent inventory = new Intent(this, Inventory.class);
        startActivity(inventory);
    }
    public void manageVolunteers(View View)
    {
        Intent mv = new Intent(this, manageVolunteers.class);
        startActivity(mv);
    }
    public void addCSV(View view){

        // ask for external sotra
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                                             PackageManager.PERMISSION_GRANTED){
                                                            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        }
        // create file picker object:
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    public void manageInventory(View view){
        Intent manInventory = new Intent(this, ManagerInventory.class);
        startActivity(manInventory);
    }

    public void back(View View)
    {
       //TURN THIS INTO A LOGOUT METHOD THINGY -Javier
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ArrayList<FoodItem> csvFoods = parseCSV(filePath); // grab food objects from csv
            Toast.makeText(this,String.valueOf(csvFoods.size()),Toast.LENGTH_SHORT).show();
            // push them to the db:
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference users = db.collection("foodItems");

            for(FoodItem item : csvFoods){
                // create map with each foods info:
                Map<String, Object> note = new HashMap<>();
                note.put(KEY_NAME, item.getName());
                note.put(KEY_SIZE, item.getSize());
                note.put(KEY_EXPR, item.getExprDate());
                note.put(KEY_QUANTITY, item.getQuantity());
                note.put(KEY_CATEGORY, item.getCategory());
                note.put(KEY_LOCATION, item.getLocation());
                note.put(KEY_THRESHOLD, item.getThreshold());
                note.put(KEY_DELPETION, item.getDepletion());
                note.put(KEY_COUNTER,item.getCounter());

                // send map to db:
                users.document(item.getName()).set(note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(manager_hub_page.this, "Food added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(manager_hub_page.this, "Food could not be added", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }

        }
    }

    private ArrayList<FoodItem> parseCSV(String filepath){

        ArrayList<FoodItem> returnList = new ArrayList<FoodItem>();

        try {
            Scanner fileScan = new Scanner(new File(filepath));
            fileScan.nextLine(); // skip header row
            fileScan.nextLine();
            fileScan.nextLine();
            fileScan.nextLine();
            fileScan.nextLine();
            int count = 1;
            while(fileScan.hasNextLine()){
                count++;
                String line = fileScan.nextLine();

                String[] items = line.split(",");

                try {
                    for(int i = 0;i < items.length;i++){
                        if(items[i].equals("")){
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (IllegalArgumentException e) {
                    String message = "There was a error on line "+ count + " of your file. Please correct it and try again.";
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                    break;
                }

                FoodItem food = new FoodItem();
                food.setCategory(items[0]);
                food.setName(items[1]);
                food.setSize(items[2]);
                food.setExprDate(items[4]);
                food.setLocation("none");
                food.setDepletion(0);
                food.setThreshold(0);
                food.setCounter(0);
                try {
                    food.setQuantity(Integer.parseInt(items[5]));
                    food.setThreshold(Integer.parseInt(items[6]));
                }
                catch(InputMismatchException e){ /// if there's an error reading file, tell user and
                    // indicate it's line number
                    String message = "There was a error on line "+ count + " of your file. Please correct it and try again.";
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                }
                returnList.add(food);



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnList;
    }



}
