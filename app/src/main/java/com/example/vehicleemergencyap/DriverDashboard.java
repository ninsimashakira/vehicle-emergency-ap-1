package com.example.vehicleemergencyap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vehicleemergencyap.databinding.ActivityDriverDashboardBinding;
import com.google.android.material.card.MaterialCardView;

public class DriverDashboard extends AppCompatActivity  {
    // Find the MaterialCardViews and TextViews
    MaterialCardView tricksAndTipsCard;
    MaterialCardView find_mechanic_card,gps_tracker_card;

    ActivityDriverDashboardBinding activityDriverDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDriverDashboardBinding = ActivityDriverDashboardBinding.inflate(getLayoutInflater());

        
        setContentView(activityDriverDashboardBinding.getRoot());
        tricksAndTipsCard = findViewById(R.id.tricks_and_tips_card);
        find_mechanic_card = findViewById(R.id.find_mechanic_card);
        gps_tracker_card = findViewById(R.id.gps_tracker_card);

        gps_tracker_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashboard.this,NavigationDrawer.class );
                startActivity(intent);
            }
        });
        tricksAndTipsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashboard.this,TipsAndTricks.class);
                startActivity(intent);
            }
        });

//        find mechanic onclick listener
        find_mechanic_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashboard.this, FindMechanicActivity.class);
                startActivity(intent);
            }
        });
    }
}