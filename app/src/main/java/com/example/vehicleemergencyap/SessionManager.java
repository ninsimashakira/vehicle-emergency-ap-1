package com.example.vehicleemergencyap;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UserSession";
    private static final int PRIVATE_MODE = 0;
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public void setRole(String role) {
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    public String getRole() {
        return prefs.getString(KEY_ROLE, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
