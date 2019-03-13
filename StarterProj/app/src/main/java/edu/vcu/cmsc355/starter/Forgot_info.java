package edu.vcu.cmsc355.starter;
/**
 * Sends email to user if they forgot information, given the user's email
 * Currently works with test user, will need database implementation to find correct account
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Forgot_info extends AppCompatActivity {
    private EditText email;
    private CheckBox user;
    private CheckBox pass;
    private Volunteer testUser;
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile( // regex pattern to validate email addresses
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_info);
        email = (EditText) findViewById(R.id.editText9);
        user = (CheckBox) findViewById(R.id.checkBox);
        pass = (CheckBox) findViewById(R.id.checkBox3);
        testUser = new Volunteer(); // test user


    }


    /*

    SETS UP BACK BUTTON TO LOGIN PAGE
     */
    public void back(View View)
    {

        finish();
    }

    public void sendEmail(View view){
        String address = email.getText().toString();
        String subject = subject = "Forgot Canned information?"; // set subject

        if(email.getText().toString().length() == 0){ // if user didn't enter an email,
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;  // tell user they didn't enter anything and end method call
        }

        if(!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString().trim()).matches()) { // if email is not valid,
            Toast.makeText(this, "Email address is not a valid email address", Toast.LENGTH_SHORT).show();
            return; // tell user and end method call
        }

        if(!pass.isChecked() && !user.isChecked()){ // if no boxes are checked,
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return; // tell user they didn't select anything and end method call
        }

        if(user.isChecked() && pass.isChecked()){ // if both boxes are checked,
            testUser.forgotPassword(); // reset password
            // create message with new pw and username:
            String forgotPassMessage = "Hello,\n\nYour Canned information is:\n" + testUser.getUserName() +
                   "\n" + testUser.getPassword()  + "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, address, "postmaster@automail-canned.com", subject);
            forgotPass.send();

        }
        else if(user.isChecked()){  // if just username box is checked,
            // create message with username:
            String forgotPassMessage = "Hello,\n\nYour Canned username is:\n" + testUser.getUserName() +
                    "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, address, "postmaster@automail-canned.com", subject);
            forgotPass.send();

        }
        else if(pass.isChecked()){ // if just password box is checked,
            testUser.forgotPassword(); // reset user's password
            // create message with new password
            String forgotPassMessage = "Hello,\n\nYour new Canned password is:\n" + testUser.getPassword() +
                    "\n\nThanks,\nThe Canned Team";

            EmailSender forgotPass = new EmailSender(forgotPassMessage, address, "postmaster@automail-canned.com", subject);
            forgotPass.send();
        }


        //Take them back to the login page
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
