package com.example.vehicleemergencyap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class Launcher extends AppCompatActivity {
    private SessionManager sessionManager;
   private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ImageView imageView = findViewById(R.id.launcher_icon);
        imageView.setImageResource(R.mipmap.ic_launcher_final_white);


        progressBar = findViewById(R.id.launcherProgress);
        HomeFragment homeFragment = new HomeFragment();

        // Set up any other initialization here, such as loading data or initializing variables
        progressBar.setVisibility(View.VISIBLE);
        // Simulate app launch by sleeping for a few seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Launch the main activity
//            progressBar.setVisibility(View.GONE);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            sessionManager = new SessionManager(getApplicationContext());
            if (auth.getCurrentUser() != null && sessionManager.getEmail() != null && sessionManager.getRole() != null) {
                // User is signed in
//                progressBar.setVisibility(View.GONE);
//                check the user role and send them to their dashboard
                if(sessionManager.getRole().equals("driver")) {
                    Intent intent = new Intent(Launcher.this, NavigationDrawer.class);
                    startActivity(intent);
                    finish();
                } else if (sessionManager.getRole().equals("mechanic")) {
                    Intent intent = new Intent(Launcher.this, MechanicDashboard.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                // User is not signed in
//                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(Launcher.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        }).start();




    }
}