/**
 *
 * Page to display the volunteer info
 * to the manager.
 *
 * Created by: Justin Nelson
 *
 * Edited by: Justin Nelson, Cove Soyars
 */


package edu.vcu.cmsc355.starter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class ManagerEditVolunteer extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView userName;
    private TextView dob;
    private ImageView pfp;
    private Volunteer thisGuy;
    private CheckBox verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit_volunteer);

        // unpack food from Inventory activity:
        Bundle foodBundle = (Bundle) getIntent().getBundleExtra("bundle");
        thisGuy = (Volunteer) foodBundle.getSerializable("thisGuy");

        verified = (CheckBox) findViewById(R.id.checkBox2);
        name = (TextView) findViewById(R.id.textView17);
        name.setText(thisGuy.getFirstName() + " " + thisGuy.getLastName());
        email = (TextView) findViewById(R.id.textView24);
        email.setText(thisGuy.getEmailAddress());
        userName = (TextView) findViewById(R.id.textView19);
        userName.setText(thisGuy.getUserName());

        dob = (TextView) findViewById(R.id.textView26);
        dob.setText(String.valueOf(thisGuy.getDob()));

        pfp = (ImageView) findViewById(R.id.pfp);
        Uri newPic = thisGuy.getProfilePicture();
        pfp.setImageURI(newPic);

        if (thisGuy.isVerified()) {
            verified.setChecked(true);
        }
        verified.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thisGuy.setVerification(!thisGuy.isVerified());
            }
        });
    }

    // on click for remove button
    public void remove(View v){
        removeVolunteer(thisGuy);
    }
    // removes volunteer from the database
    private void removeVolunteer(Volunteer thisGuy){
        // remove volunteer from database with information that matches thisGuy
    }
}