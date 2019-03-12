package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;


public class Sign_Up_Page extends AppCompatActivity {
    private Volunteer newV;
    private EditText user;
    private EditText pass;
    //private EditText passConfirm;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText date;
    private Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile( // regex pattern to validate email addresses
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
        setContentView(R.layout.activity_sign__up__page);
        user = (EditText) findViewById(R.id.editText3);
        pass = (EditText) findViewById(R.id.editText4);
        //passConfirm = (EditText) findViewById(R.id.editText3);
        first = (EditText) findViewById(R.id.editText5);
        last = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText7);
        date = (EditText) findViewById(R.id.editText8);
    }
/*

SETS UP BACK BUTTON TO LOGIN PAGE
 */
    public void back(View View)
    {

        Intent back = new Intent(this, MainActivity.class);

        startActivity(back);
    }

    public void addUser(View view){

        if(verifyInputs()){ // if all fields are valid, create a volunteer with the information

            Volunteer newVol = new Volunteer(pass.getText().toString().trim(),
                    user.getText().toString().trim(),
                    first.getText().toString().trim(),
                    last.getText().toString().trim(),
                    Integer.parseInt(date.getText().toString()),
                    email.getText().toString().trim());

            //Need to add to database data structure when available.
            Intent verifyPage = new Intent(Sign_Up_Page.this, verify_Page.class );
            // Intent mainActivity = new Intent(this, MainActivity.class);       COMMENTED OUT BY JAVIER
            startActivity(verifyPage);
        }

        else{
            // do nothing (removing this else block makes the app crash!)
        }
    }

    /**
     * Verifies fields in sign up form
     * @return false if a field is invalid and displays a toast with the error
     */
    private boolean verifyInputs(){

        if(pass.getText().toString().isEmpty()){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getText().toString().isEmpty()){ // WILL NEED TO VERIFY IF USERNAME IS ALREADY IN DB!
            Toast.makeText(this, "Username field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(first.getText().toString().isEmpty()){
            Toast.makeText(this, "First name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(last.getText().toString().isEmpty()){
            Toast.makeText(this, "Last name field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        // test if email is a valid email address:
        if(!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString().trim()).matches()){
            Toast.makeText(this, "Email address is not a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(date.getText().toString().trim().length() != 6){ // DOB is invalid if it's not of length 6
            Toast.makeText(this, "DOB is not in correct format: MMDDYY", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
}
