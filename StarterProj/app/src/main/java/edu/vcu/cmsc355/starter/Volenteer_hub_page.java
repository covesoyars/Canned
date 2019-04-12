package edu.vcu.cmsc355.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Volenteer_hub_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volenteer_hub_page);
    }

    public void recordDonations(View View)
    {
        Intent rec = new Intent(this, addFood.class);
        startActivity(rec);
    }

    public void inventory(View View)
    {
        Intent inventory = new Intent(this, Inventory.class);
        startActivity(inventory);
    }

    public void back(View View)
    {
        //TURN THIS INTO A LOGOUT METHOD THINGY -Javier
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // remove jimbo from volunteer database
                        //Log.d(TAG, "REEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                        //userReset.setText("");
                        //passReset.setText("");
                        //log = true;
                        finish();
                        //Log.d(TAG, "REEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + log);
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
        /*if(log) {
            userReset.setText("");
            passReset.setText("");
            startActivity(logout);
        }*/
        //Toast.makeText(this,log + " ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // disables back button
    }
    public void settings(View View)
    {
        Intent settings = new Intent(this, Edit_profile.class);
        startActivity(settings);
    }
}
