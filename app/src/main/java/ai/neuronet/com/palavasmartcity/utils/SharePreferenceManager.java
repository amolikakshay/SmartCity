package ai.neuronet.com.palavasmartcity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ai.neuronet.com.palavasmartcity.R;

public class SharePreferenceManager {


    private String PREF_NAME="SMART_CITY_PREF";
    private static SharePreferenceManager SHARED_PREFERENCE=null;



    public static SharePreferenceManager getInstance() {
        if (SHARED_PREFERENCE == null) {
            synchronized(SharePreferenceManager.class) {
                SHARED_PREFERENCE = new SharePreferenceManager();
            }
        }
        return SHARED_PREFERENCE;
    }


    public void saveLoginDetails(String mobileNumber,Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.mobile), mobileNumber);
        editor.apply();
    }

    public String getMobile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.mobile), "");
    }

    public void setLoginTrue(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.isLogeIn), true);
        editor.apply();
    }

    public boolean isLogin(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.isLogeIn), false);
    }

    public void logOut(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setSysnonsStrings(Context context, ArrayList<String> strings) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> stringSet = new HashSet<String>(strings);
        editor.putStringSet(context.getString(R.string.synonms), stringSet);
        editor.apply();
    }

    public Set<String> getSysnonsStrings(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(context.getString(R.string.synonms),null);
    }
}
