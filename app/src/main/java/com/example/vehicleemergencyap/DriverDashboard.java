package com.example.vehicleemergencyap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;

public class DriverDashboard extends AppCompatActivity {
    // Find the MaterialCardViews and TextViews
    MaterialCardView tricksAndTipsCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        tricksAndTipsCard = findViewById(R.id.gps_tracker_card);
        tricksAndTipsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashboard.this,TipsAndTricks.class);
                startActivity(intent);
            }
        });
    }
}