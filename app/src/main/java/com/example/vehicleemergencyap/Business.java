package com.example.vehicleemergencyap;

import com.google.android.gms.maps.model.LatLng;

public class Business {
    private String id;
    private String email;
    private String business_name;
    private String phone;
    private String available;
    private String open_hours;
    private String closing_hours;
    private LatLng location;

    private String user_id;
    public Business() {
        // Default constructor required for Firestore
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Business(String id, String email, String business_name, String phone, String available, String open_hours, String closing_hours, LatLng location, String user_id) {
        this.id = id;
        this.email = email;
        this.business_name = business_name;
        this.phone = phone;
        this.available = available;
        this.open_hours = open_hours;
        this.closing_hours = closing_hours;
        this.location = location;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getClosing_hours() {
        return closing_hours;
    }

    public void setClosing_hours(String closing_hours) {
        this.closing_hours = closing_hours;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
