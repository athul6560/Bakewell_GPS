package com.acb.bakewellgps.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.acb.bakewellgps.ui.Activities.DashboardPage.Dashboard;

public class SharedData {
    private static final String LOGINCHECKKEY = "LoginKeyCheck";
    private static final String USERNAME = "userName";

    public static void LogedStatusUpdate(Context context, boolean loginStatus) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean statusLocked = prefs.edit().putBoolean(LOGINCHECKKEY, loginStatus).commit();
    }

    public static boolean LoginCheck(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGINCHECKKEY, false);

    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(USERNAME, "");
    }
    public static void setUserName(Context context,String username) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean statusLocked = prefs.edit().putString(USERNAME, username).commit();
    }
}
