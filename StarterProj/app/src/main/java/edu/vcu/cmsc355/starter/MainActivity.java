package edu.vcu.cmsc355.starter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import java.util.Calendar;

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
    private Volunteer v;

    // for below threshold notifications:
    private PendingIntent belowThreshPendingIntent;
    private AlarmManager manager;
    private PendingIntent depletionPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, BelowThreshReciever.class);
        Intent depletionIntent = new Intent(this,DepletionReciever.class);
        belowThreshPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        depletionPendingIntent = PendingIntent.getBroadcast(this, 1, depletionIntent, 0);


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
        final Intent login = new Intent(this, manager_hub_page.class);
        final Intent volunteerLogin = new Intent(this, Volenteer_hub_page.class);

        //Intent volLogin = new Intent(this, volunteer_hub_page.class);
        if(user.getText().toString().trim().equals("") && pass.getText().toString().trim().equals("")) {

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
            user.setText("");
            pass.setText("");
            startActivity(login);
            startDepletionAlarm();
            startBelowThreshAlarm();
        }
        else{
            fb = false;
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("users2");
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
                                String p = document.getData().get("pass").toString();
                                String u = document.getData().get("user").toString();
                                String f = document.getData().get("first").toString();
                                String l = document.getData().get("last").toString();
                                int dob = Integer.parseInt(document.getData().get("date").toString());
                                String e = document.getData().get("email").toString();
                                boolean ver = Boolean.parseBoolean(document.getData().get("verify").toString());

                                v = new Volunteer(p,u,f,l,dob,e);
                                if(ver)
                                    v.verify();
                                UserLoggedIn appState = ((UserLoggedIn) getApplicationContext());
                                appState.setLoggedIn(v);
                                // startActivity(login);
                                user.setText("");
                                pass.setText("");

                            }
                            if(v.isVerified()){ startActivity(volunteerLogin); }
                            else{
                                Toast.makeText(getApplicationContext(),"User is not verified",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Log.d(TAG, "User not found");
                            Toast.makeText(getApplicationContext(),"User information is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Log.d(TAG, "something went wrong");
                        Toast.makeText(getApplicationContext(),"User information is incorrect",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void startBelowThreshAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, calendar.getTimeInMillis(), belowThreshPendingIntent);
    }

    public void startDepletionAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, calendar.getTimeInMillis(), depletionPendingIntent);
    }
}
