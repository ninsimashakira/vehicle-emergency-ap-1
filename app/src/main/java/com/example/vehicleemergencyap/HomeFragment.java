package com.example.vehicleemergencyap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private MaterialCardView tricksAndTipsCard;
    private MaterialCardView find_mechanic_card;
    private MaterialCardView gps_tracker_card;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tricksAndTipsCard = view.findViewById(R.id.tricks_and_tips_card);
        find_mechanic_card = view.findViewById(R.id.find_mechanic_card);
        gps_tracker_card = view.findViewById(R.id.gps_tracker_card);

        gps_tracker_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NavigationDrawer.class);
                startActivity(intent);
            }
        });

        tricksAndTipsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TipsAndTricks.class);
                startActivity(intent);
            }
        });

        find_mechanic_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindMechanicActivity.class);
                startActivity(intent);
            }
        });
    }
}
