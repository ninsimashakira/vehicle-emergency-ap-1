package com.example.vehicleemergencyap;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder> {

    private final Context context;
    private final List<Business> businessList;

    public BusinessListAdapter(Context context, int item_business, List<Business> businessList) {
        this.context = context;
        this.businessList = businessList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business business = businessList.get(position);
        holder.businessName.setText(business.getBusiness_name());
        holder.businessEmail.setText(business.getEmail());
        holder.businessPhone.setText(business.getPhone());
        holder.businessAvailable.setText(business.getAvailable());
        holder.businessOpenHours.setText(business.getOpen_hours());
        holder.businessClosingHours.setText(business.getClosing_hours());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, BusinessDetailsActivity.class);
            intent.putExtra("user_id", business.getUser_id());
           intent.putExtra("businessId", business.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView businessName;

        public  String user_id;
        public final TextView businessEmail;
        public final TextView businessPhone;

        public final TextView businessOpenHours;

        public final TextView businessAvailable;

        public final TextView businessClosingHours;

        public final TextView businessAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            businessName = itemView.findViewById(R.id.business_name);
            businessEmail = itemView.findViewById(R.id.business_email);
            businessPhone = itemView.findViewById(R.id.business_phone);
            businessAvailable = itemView.findViewById(R.id.business_available);
            businessOpenHours = itemView.findViewById(R.id.businessOpenHours);
            businessClosingHours = itemView.findViewById(R.id.businessClosingHours);
            businessAddress = itemView.findViewById(R.id.business_address_edittext);
            user_id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        }


    }
}


