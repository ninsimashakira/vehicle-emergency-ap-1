package com.example.vehicleemergencyap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FindMechanicActivity extends AppCompatActivity {

    private static final String TAG = "findMech";
    private RecyclerView recyclerView;
    private BusinessAdapter businessAdapter;
    private List<Business> businessList;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mechanic);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.find_mechanic_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        businessList = new ArrayList<>();
        businessAdapter = new BusinessAdapter(this, businessList);
        recyclerView.setAdapter(businessAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("businesses")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Business business = document.toObject(Business.class);
                            businessList.add(business);
                        }
                        businessAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                businessAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}

