package com.example.vehicleemergencyap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    private final Context context;
    private static List<Business> businessList;
    private static List<Business> businessListFull; // This is a copy of the original list, used for filtering

    public BusinessAdapter(Context context, List<Business> businessList) {
        this.context = context;
        BusinessAdapter.businessList = businessList;
        businessListFull = businessList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_recycler_view, parent, false);
        Button callButton = view.findViewById(R.id.call_button);
        Button emailButton = view.findViewById(R.id.email_button);
        return new ViewHolder(view, callButton, emailButton);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business business = businessList.get(position);
        holder.bind(business);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public Filter getFilter() {
        return businessFilter;
    }

    private final Filter businessFilter = new Filter() {
        @Override
        @SuppressLint("DefaultLocale")
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("bFullList",businessListFull.toString());
            List<Business> filteredList = new ArrayList<>();
              if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(businessListFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                  Log.d("filterPattern",filterPattern);
                for (Business business : businessListFull) {
                    if (business.getBusiness_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(business);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            Log.d("results",results.values.toString());
            return results;

        }


        @SuppressWarnings("unchecked")
        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            businessList.clear();
            businessList.addAll((Collection<? extends Business>) results.values);
            Log.d("publish", String.valueOf(businessList.size()));
            notifyDataSetChanged();
        }

    };


    public class ViewHolder extends RecyclerView.ViewHolder implements com.example.vehicleemergencyap.ViewHolder {
        public final TextView businessName;

        public final TextView businessEmail;
        public final TextView businessPhone;

        public final TextView businessOpenHours;

        public final TextView businessAvailable;

        public final TextView businessClosingHours;

        public final TextView businessAddress;

        public final Button callButton;
        public  final  Button emailButton;

        public ViewHolder(View itemView, Button callButton, Button emailButton) {
            super(itemView);
            businessName = itemView.findViewById(R.id.business_name);
            businessEmail = itemView.findViewById(R.id.business_email);
            businessPhone = itemView.findViewById(R.id.business_phone);
            businessAvailable = itemView.findViewById(R.id.business_available);
            businessOpenHours = itemView.findViewById(R.id.businessOpenHours);
            businessClosingHours = itemView.findViewById(R.id.businessClosingHours);
            businessAddress = itemView.findViewById(R.id.business_address_edittext);

            this.callButton = callButton;
            this.emailButton = emailButton;
        }

        public void bind(Business business) {
            businessName.setText(business.getBusiness_name());
            businessEmail.setText(business.getEmail());
            businessPhone.setText(business.getPhone());
            businessAvailable.setText(business.getAvailable());
            businessOpenHours.setText(business.getOpen_hours());
            businessClosingHours.setText(business.getClosing_hours());

            callButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + business.getPhone()));
                itemView.getContext().startActivity(intent);
            });

            emailButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + business.getEmail()));
                itemView.getContext().startActivity(Intent.createChooser(intent, "Send email"));
            });
        }

        @Override
        public Filter getFilter() {
            return businessFilter;
        }

//        private final Filter businessFilter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                List<Business> filteredList = new ArrayList<>();
//
//                if (constraint == null || constraint.length() == 0) {
//                    filteredList.addAll(businessListFull);
//                } else {
//                    String filterPattern = constraint.toString().toLowerCase().trim();
//
//                    for (Business business : businessListFull) {
//                        if (business.getBusiness_name().toLowerCase().contains(filterPattern)) {
//                            filteredList.add(business);
//                        }
//                    }
//                }
//
//                FilterResults results = new FilterResults();
//                results.values = filteredList;
//                return results;
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                businessList.clear();
//                businessList.addAll((List<? extends Business>) results.values);
//                notifyDataSetChanged();
//            }
//        };
    }

}



