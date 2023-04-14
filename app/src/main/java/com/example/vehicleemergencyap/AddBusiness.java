package com.example.vehicleemergencyap;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBusiness extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText businessName;
    private TextInputEditText phone;
    private TextInputEditText available;
    private TextInputEditText openHours;
    private TextInputEditText closingHours;
    private TextInputEditText locationField;
    private Button pickLocation;
    private GoogleMap googleMap;
    private LatLng selectedLocation;
    private MaterialButton registerButton;



    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ActivityResultLauncher<Intent> mapActivityResultLauncher;
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
        locationField = findViewById(R.id.locationField);
        mapActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        String selectedLocation = data.getStringExtra("location");
                        locationField.setText(selectedLocation);

                        pickLocation.setVisibility(View.GONE);

                        // do something with the selected location
                    }
                });
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

    // initialize the activity result launcher


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
        String locationFieldString = locationField.getText().toString();
        Log.d("location",locationFieldString);

        Map<String, Object> businessData = new HashMap<>();
        businessData.put("email", emailString);
        businessData.put("business_name", businessNameString);
        businessData.put("phone", phoneString);
        businessData.put("available", availableString);
        businessData.put("open_hours", openHoursString);
        businessData.put("closing_hours", closingHoursString);
        businessData.put("location", locationFieldString);
        businessData.put("user_id", currentUserUid);


// Create a new document with a unique auto-generated ID
        CollectionReference businessesRef = FirebaseFirestore.getInstance().collection("businesses");
        DocumentReference newBusinessRef = businessesRef.document();

// Get the document ID as a string and set it as the value for the "id" field
        String newBusinessId = newBusinessRef.getId();
        businessData.put("id", newBusinessId);

// Set the data for the new document
        newBusinessRef.set(businessData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddBusiness.this, "Business Created successfully", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Business data added with ID: " + newBusinessId);
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.w(TAG, "Error adding business data", e);
                    }
                });

    }
}
