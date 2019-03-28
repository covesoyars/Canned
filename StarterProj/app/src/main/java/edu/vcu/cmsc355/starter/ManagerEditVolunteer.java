/**
 *
 * Page to display the volunteer info
 * to the manager.
 *
 * Created by: Justin Nelson
 *
 * Edited by: Justin Nelson,
 */


package edu.vcu.cmsc355.starter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ManagerEditVolunteer extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView userName;
    private TextView birthDate;
    private ImageView pfp;
    private Volunteer thisGuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_volunteer);

        // unpack food from Inventory activity:
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        thisGuy = (Volunteer) foodBundle.getSerializable("thisGuy");
        



        //Call to database for the list of volunteers
        //Fill in line below or make a new line for it, thanks Sam.
        //ArrayList<Volunteer> list = database.get();
        /*
        for(Volunteer item: list){
        if(name == item.getUsername()){
        thisGuy = item;
        break;
        }
        }

         */
        name = (TextView) findViewById(R.id.textView17);
        name.setText(thisGuy.getFirstName() + " " + thisGuy.getLastName());
        pfp = (ImageView) findViewById(R.id.pfp);
        Uri newPic = thisGuy.getProfilePicture();
        pfp.setImageURI(newPic);
    }
}
