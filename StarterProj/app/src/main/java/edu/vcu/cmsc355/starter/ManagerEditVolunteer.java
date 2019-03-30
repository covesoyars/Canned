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
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ManagerEditVolunteer extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView userName;
    private TextView startDate;
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
        email = (TextView) findViewById(R.id.textView19);
        email.setText(thisGuy.getEmailAddress());
        userName = (TextView) findViewById(R.id.textView18);
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
}