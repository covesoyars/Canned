package edu.vcu.cmsc355.starter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private static final String TAG = "MainPage";
    private boolean fb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
    }

    public void signUp(View view){
    Intent startSignUp = new Intent(this, Sign_Up_Page.class);
    startActivity(startSignUp);
    }

    public void forgotInfo(View view){
        Intent startForgotInfo = new Intent(this, Forgot_info.class);
        startActivity(startForgotInfo);
    }

    public void login(View view){
        Intent login = new Intent(this, manager_hub_page.class);
        final Intent volunteerLogin = new Intent(this, Volenteer_hub_page.class);

        //Intent volLogin = new Intent(this, volunteer_hub_page.class);
        if(user.getText().toString().equals("CoolKid123") && pass.getText().toString().equals("CoolPass")) {

            File profilePic = new File("/StarterProj/app/src/main/res/drawable/doug.png");
            User loggedInUser = new Volunteer("CoolPass", // create user object (will pull
                    // user with matching pw and username when database is working
                    "CoolKid123",
                    "Doug",
                    "Dimmadome",
                    123456,
                    "doug.dimmadome@dimmsdaledimmadome.com",
                    profilePic);
            UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
            appState.setLoggedIn(loggedInUser);
            startActivity(login);
        }
        else{
            fb = false;
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("users");
            String u = user.getText().toString().trim();
            String p = pass.getText().toString().trim();

            usersRef.whereEqualTo("user", u).whereEqualTo("pass", p)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot q = task.getResult();
                        if(!q.isEmpty()) {
                            fb = true;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            startActivity(volunteerLogin);
                        }
                        else{
                            Log.d(TAG, "User not found");
                        }
                    }
                    else{
                        Log.d(TAG, "something went wrong");
                    }
                }
            });
        }
    }
}
