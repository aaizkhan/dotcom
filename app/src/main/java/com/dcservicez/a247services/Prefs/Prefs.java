package com.dcservicez.a247services.Prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs {
    private Context context;
    SharedPreferences sharedPref;

    public Prefs(Context context) {
        this.context = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

    }

    public void sverc_type(String s){
        sharedPref.edit().putString("sverc_type",s).apply();
    }
    public String sverc_type(){
        return  sharedPref.getString("sverc_type","");
    }

    public void email(String s){
        sharedPref.edit().putString("email",s).apply();
    }
    public String email(){
        return  sharedPref.getString("email","");
    }
}
