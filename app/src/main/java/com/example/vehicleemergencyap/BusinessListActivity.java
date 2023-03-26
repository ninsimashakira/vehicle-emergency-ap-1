package com.example.vehicleemergencyap;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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
        db.collection("businesses")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Business business = document.toObject(Business.class);
                            businessList.add(business);
                        }
                        Log.d("BusinessListActivity", businessList.toString());
                        adapter = new BusinessListAdapter(BusinessListActivity.this, R.layout.item_business, businessList);
                        recyclerView.setAdapter(adapter);
                        Log.d(TAG, "ADAPTER IS HERE", task.getException());
                    } else {
                        Log.d(TAG, "Error getting businesses: ", task.getException());
                    }
                });
    }
}
