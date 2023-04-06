package com.example.vehicleemergencyap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MechanicDashboard extends NavigationDrawer {

    private MaterialButton btnAddBusn;
    private MaterialButton btnViewBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        btnAddBusn = findViewById(R.id.btnAddBusn);
        btnViewBusiness = findViewById(R.id.viewBusn);


        btnAddBusn.setOnClickListener(view -> {
            Intent intent =  new Intent(MechanicDashboard.this,AddBusiness.class);
            startActivity(intent);
        });

        btnViewBusiness.setOnClickListener(view -> {
            Intent intent =  new Intent(MechanicDashboard.this,BusinessListActivity.class);
            startActivity(intent);
        });

    }
}