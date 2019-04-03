package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.EditText;

public class createDonor extends AppCompatActivity {
    private static final String TAG = "CreateDonorPage";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private boolean fb = true;

    private EditText first;
    private EditText last;
    private EditText email;
=======

public class createDonor extends AppCompatActivity {
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donor);
    }
<<<<<<< HEAD

    public void addDonor(View view){
        int i = 1;
    }
}
=======
}
>>>>>>> origin/master
