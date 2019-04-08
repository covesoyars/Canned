package edu.vcu.cmsc355.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.Scanner;
import java.util.regex.Pattern;

public class manager_hub_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_hub_page);


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
            Toast.makeText(this,filePath,Toast.LENGTH_SHORT).show();
            Scanner scanzor = new Scanner(filePath);
            Scanner scanny = new Scanner(System.in);
            scanzor.useDelimiter(",");
            FoodItem newFood = new FoodItem();

            /*
             *Go through the file and add all the foodItems to the list.
             */
            while(scanny.hasNextLine()){
                scanzor = new Scanner(scanny.nextLine());
                FoodItem food = new FoodItem();
                food.setCategory(scanzor.next());
                food.setName(scanzor.next());
                food.setSize(scanzor.next());
                String skip = scanzor.next();
                food.setExprDate(scanzor.next());
                food.setQuantity(scanzor.nextInt());
                food.setThreshold(scanzor.nextInt());
                //Database.addFoodToFoodList()
                String TAG = "hi ";
                Log.d(TAG, "Food has these stuffs: " + food.getName() + " " + food.getQuantity() + " " + food.getExprDate());
            }
        }
    }

}
