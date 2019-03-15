package edu.vcu.cmsc355.starter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class verify_Page extends AppCompatActivity {


    //@author Javier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awaiting_verfification);

        configureBackButton();
    }


private void configureBackButton()          //back button sends user back to the login page on this special exception
{
    ImageButton backButton =  findViewById(R.id.backButton2);
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainActivity = new Intent(verify_Page.this, MainActivity.class);
            startActivity(mainActivity);       //throws page back to login screen

        }
    });

}

}
