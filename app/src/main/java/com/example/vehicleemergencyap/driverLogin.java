package com.example.vehicleemergencyap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class driverLogin extends AppCompatActivity {


    private EditText emailField, passwordField;
    private Button loginButton;
    private FirebaseAuth mAuth;

    private MaterialTextView createAcc;

    private MaterialTextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.login_button);
        createAcc = findViewById(R.id.txtSignUp);
        forgotPassword = findViewById(R.id.forgotPwd);

        // login action
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(driverLogin.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(driverLogin.this, "Authentication succeeded.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(driverLogin.this,DriverDashboard.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(driverLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
            }
        });

//        create account action
        createAcc.setOnClickListener(view -> {
            Intent intent = new Intent(driverLogin.this,DriverRegister.class);
            startActivity(intent);
        });

//      forgot password
        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(driverLogin.this,DriverDashboard.class);
            startActivity(intent);
        });
    }
}