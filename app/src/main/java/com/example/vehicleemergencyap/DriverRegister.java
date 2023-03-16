package com.example.vehicleemergencyap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DriverRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText fullNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText passwordEditTextConfirm;
    private TextInputEditText carMakeModelEditText;
    // private TextInputEditText mCarPlateNumberEditText;
    private TextInputLayout mFullNameTextInputLayout;
    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPhoneTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;
    private TextInputLayout mConfirmPasswordTextInputLayout;
    private TextInputLayout carMakeModelTextInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        mAuth = FirebaseAuth.getInstance();

        mFullNameTextInputLayout = findViewById(R.id.mFullNameTextInputLayout);
        mEmailTextInputLayout = findViewById(R.id.mEmailTextInputLayout);
        mPhoneTextInputLayout = findViewById(R.id.mPhoneTextInputLayout);
        mPasswordTextInputLayout = findViewById(R.id.mPasswordTextInputLayout);
        mConfirmPasswordTextInputLayout = findViewById(R.id.mConfirmPasswordTextInputLayout);
        carMakeModelTextInputLayout = findViewById(R.id.carMakeModelTextInputLayout);
//        mCarPlateNumberLayout = findViewById(R.id.carPlateNumberTextInputLayout);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditTextConfirm = findViewById(R.id.passwordEditTextConfirm);
        carMakeModelEditText = findViewById(R.id.carMakeModelEditText);
//           mCarPlateNumberEditText = findViewById(R.id.carPlateNumberEditText);

        MaterialButton mRegisterButton = findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }

            //            function for user registration
            private void registerUser() {
                String fullName = Objects.requireNonNull(fullNameEditText.getText()).toString().trim();
                String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
                String phone = Objects.requireNonNull(phoneEditText.getText()).toString().trim();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
                String confirmPassword = Objects.requireNonNull(passwordEditTextConfirm.getText()).toString().trim();
                String carMakeModel = Objects.requireNonNull(carMakeModelEditText.getText()).toString().trim();


//                validating user input
                if (fullName.isEmpty()) {
                    mFullNameTextInputLayout.setError("Please enter your full name");
                    mFullNameTextInputLayout.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    mEmailTextInputLayout.setError("Please enter your email");
                    mEmailTextInputLayout.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    mPhoneTextInputLayout.setError("Please enter your phone number");
                    mPhoneTextInputLayout.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    mPasswordTextInputLayout.setError("Please enter a password");
                    mPasswordTextInputLayout.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()) {
                    mConfirmPasswordTextInputLayout.setError("Please confirm your password");
                    mConfirmPasswordTextInputLayout.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    mConfirmPasswordTextInputLayout.setError("Passwords do not match");
                    mConfirmPasswordTextInputLayout.requestFocus();
                    return;
                }

                if (carMakeModel.isEmpty()) {
                    carMakeModelTextInputLayout.setError("Please enter your car make and model");
                    carMakeModelTextInputLayout.requestFocus();
                    return;
                }

                // Show a progress dialog while registering the user
                ProgressDialog progressDialog = new ProgressDialog(DriverRegister.this);
                progressDialog.setMessage("Registering...");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(DriverRegister.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName)
                                        .build();
                                assert user != null;
                                user.updateProfile(profileUpdates);

                                // Add additional user data to Firestore
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("fullName", fullName);
                                userData.put("email", email);
                                userData.put("phone", phone);
                                userData.put("carMakeModel", carMakeModel);
                                db.collection("users").document(user.getUid()).set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                             progressDialog.dismiss();
                                            Toast.makeText(DriverRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            progressDialog.dismiss();
                                            Toast.makeText(DriverRegister.this, "Failed to register: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        });
                            } else {
                                 progressDialog.dismiss();
                                Toast.makeText(DriverRegister.this, "Failed to register: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }


        });
    }
}