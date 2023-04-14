package com.example.vehicleemergencyap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BusinessListActivity extends AppCompatActivity {

    private static final String TAG = "BusinessListActivity" ;
    private RecyclerView recyclerView;
    private BusinessListAdapter adapter;
    private List<Business> businessList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);

        recyclerView = findViewById(R.id.business_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(BusinessListActivity.this));

        db = FirebaseFirestore.getInstance();

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        businessList = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(BusinessListActivity.this);
        progressDialog.setTitle("Retrieving Business ");
        progressDialog.setMessage("Please wait while we get your business...");
        progressDialog.show();
        db.collection("businesses")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Business business = document.toObject(Business.class);
                            Log.d("business",business.toString());
                            businessList.add(business);
                        }
                        Log.d("BusinessListActivity", businessList.toString());
                        if (!businessList.isEmpty()) {
                            progressDialog.dismiss();
                            adapter = new BusinessListAdapter(BusinessListActivity.this, R.layout.item_business, businessList);
                            recyclerView.setAdapter(adapter);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(this, "You do not have a running business at the moment", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BusinessListActivity.this,AddBusiness.class);
                            startActivity(intent);
                        }
                    } else {
                        Log.d(TAG, "Error getting businesses: ", task.getException());
                    }
                });
    }
}
