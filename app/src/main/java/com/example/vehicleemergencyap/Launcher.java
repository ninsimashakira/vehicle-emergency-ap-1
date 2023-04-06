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

   private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ImageView imageView = findViewById(R.id.launcher_icon);
        imageView.setImageResource(R.mipmap.ic_launcher_final_white);


        progressBar = findViewById(R.id.launcherProgress);

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
            if (auth.getCurrentUser() != null) {
                // User is signed in
//                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(Launcher.this, DriverDashboard.class);
                startActivity(intent);
                finish();
            } else {
                // User is not signed in
//                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(Launcher.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();




    }
}