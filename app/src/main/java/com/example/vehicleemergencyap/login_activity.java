package com.example.vehicleemergencyap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class login_activity extends AppCompatActivity {
    private MaterialButton btnDriver, btnMechanic;
   // private MaterialButton btnMechanic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnDriver = findViewById(R.id.driverLoginBtn);
        btnMechanic = findViewById(R.id.mechanicLoginBtn);
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this,driverLogin.class);
                startActivity(intent);
            }
        });

        btnMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, MechanicLogin.class);
                startActivity(intent);
            }
        });

    }
}