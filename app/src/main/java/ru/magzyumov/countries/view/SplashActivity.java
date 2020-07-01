package ru.magzyumov.countries.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.magzyumov.countries.Constants;
import ru.magzyumov.countries.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity implements Constants, ISplashView {
    private SplashPresenter splashPresenter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!getBooleanPreference(SETTINGS, INITIALIZATION)){
            splashPresenter = new SplashPresenter(this);
            splashPresenter.init();
            splashPresenter.getAllCountries();
        } else {
            closeSplash();
        }
    }

    private void closeSplash(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initDone(){
        setBooleanPreference(SETTINGS, INITIALIZATION, true);
        closeSplash();
    }

    @Override
    public void initFailed(){
        setBooleanPreference(SETTINGS, INITIALIZATION, false);
        closeSplash();
    }

    private boolean getBooleanPreference(String preference, String parameter ){
        sharedPref = getSharedPreferences(preference, MODE_PRIVATE);
        return sharedPref.getBoolean(parameter, false);
    }

    private void setBooleanPreference(String preference, String parameter, boolean value) {
        sharedPref = getSharedPreferences(preference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(parameter, value);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter = null;
        sharedPref = null;
    }
}
