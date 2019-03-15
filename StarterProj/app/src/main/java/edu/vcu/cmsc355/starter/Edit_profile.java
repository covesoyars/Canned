package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_profile extends AppCompatActivity implements View.OnClickListener{

    private EditText lastName;
    private EditText firstName;
    private EditText dob;
    private EditText email;
    private EditText password;
    private CircleImageView profilePic;
    private static final int PICK_IMAGE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // grab logged in user's information:
        UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
        User loggedIn = appState.getLoggedIn();
        // declare buttons and set their text to the user's information
        lastName = (EditText) findViewById(R.id.editText16);
        lastName.setText(loggedIn.getLastName());
        firstName = (EditText) findViewById(R.id.editText15);
        firstName.setText(loggedIn.getFirstName());
        dob = (EditText) findViewById(R.id.editText10);
        dob.setText(Integer.toString(loggedIn.getDob()));
        email = (EditText) findViewById(R.id.editText12);
        email.setText(loggedIn.getEmailAddress());
        password = (EditText) findViewById(R.id.editText14);
        password.setText(loggedIn.getPassword());
        profilePic = (CircleImageView) findViewById(R.id.circleImageView);
        Uri profileUri = loggedIn.getProfilePicture();
        profilePic.setImageURI(profileUri);
        /*try {
            Bitmap imageMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profileUri);
            imageMap = Bitmap.createScaledBitmap(imageMap,  600 ,600, true);
            profilePic.setImageBitmap(imageMap);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();

        }*/

        profilePic.setOnClickListener(this);





    }

    @Override
    public void onClick(View v){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE && data != null){
            Uri imageGrab = data.getData();
            profilePic.setImageURI(imageGrab);
            profilePic.setRotation(90);
        }
    }

    public void back(View View)
    {
        finish();


    }

}
