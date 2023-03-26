package com.example.vehicleemergencyap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBusiness extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> mapActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    LatLng selectedLocation = data.getParcelableExtra("selected_location");
                    // do something with the selected location
                }
            });
    private TextInputEditText email;
    private TextInputEditText businessName;
    private TextInputEditText phone;
    private TextInputEditText available;
    private TextInputEditText openHours;
    private TextInputEditText closingHours;
    private Button pickLocation;
    private GoogleMap googleMap;
    private LatLng selectedLocation;
    private MaterialButton registerButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.bEmailEditText);
        businessName = findViewById(R.id.bNameEditText);
        phone = findViewById(R.id.bphoneEditText);
        available = findViewById(R.id.bAvailableEditText);
        openHours = findViewById(R.id.bOpeningHours);
        closingHours = findViewById(R.id.closingHours);
        pickLocation = findViewById(R.id.pickLocationButton);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBusiness();
            }
        });
        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBusiness.this, MapActivity.class);
                mapActivityResultLauncher.launch(intent);

            }
        });
    }

    public void saveBusiness() {
        // Show progress bar
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering Business");
        progressDialog.setMessage("Please wait while we register your business...");
        progressDialog.show();

        String currentUserUid = mAuth.getCurrentUser().getUid();

        String emailString = email.getText().toString();
        String businessNameString = businessName.getText().toString();
        String phoneString = phone.getText().toString();
        String availableString = available.getText().toString();
        String openHoursString = openHours.getText().toString();
        String closingHoursString = closingHours.getText().toString();

        Map<String, Object> businessData = new HashMap<>();
        businessData.put("email", emailString);
        businessData.put("business_name", businessNameString);
        businessData.put("phone", phoneString);
        businessData.put("available", availableString);
        businessData.put("open_hours", openHoursString);
        businessData.put("closing_hours", closingHoursString);
        businessData.put("location", selectedLocation);
        businessData.put("user_id", currentUserUid);

        db.collection("businesses").add(businessData)
                .addOnSuccessListener(documentReference -> {
                    // Business data saved successfully
                    // TODO: Add success message
                    progressDialog.dismiss();
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error saving business data
                    // TODO: Add error message
                    progressDialog.dismiss();
                });
    }
}
