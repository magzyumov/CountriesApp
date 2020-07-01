package ru.magzyumov.countries.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import ru.magzyumov.countries.presenter.IBroadcastListener;
import ru.magzyumov.countries.presenter.NetworkReceiver;
import ru.magzyumov.countries.Constants;
import ru.magzyumov.countries.R;
import ru.magzyumov.countries.model.database.Countries;
import ru.magzyumov.countries.presenter.MainPresenter;
import ru.magzyumov.countries.presenter.SplashPresenter;

public class MainActivity extends AppCompatActivity implements IMainView, IBroadcastListener,
        ISplashView, Constants {

    private NetworkReceiver networkReceiver;
    private MainAdapter mainAdapter;
    private SplashPresenter splashPresenter;
    private MainPresenter mainPresenter;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);

        networkReceiver = new NetworkReceiver(getBaseContext());
        networkReceiver.addListener(this);

        registerReceivers();

        if(databaseInit()){ initView(); }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkReceiver.removeListener(this);
        unregisterReceivers();
    }

    private void initView(){
        mainPresenter.init();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        mainAdapter = new MainAdapter(mainPresenter.getCountries(), getBaseContext());
        recyclerView.setAdapter(mainAdapter);

        mainAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void registerReceivers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void unregisterReceivers() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataReady(){
        initRecyclerView();
    }

    @Override
    public void showCountry(Countries country){
        BottomFragmentDialog bottomFragmentDialog = BottomFragmentDialog.newInstance();
        bottomFragmentDialog.setData(country);
        bottomFragmentDialog.show(getSupportFragmentManager(), "dialog_fragment");
    }

    private MainAdapter.OnItemClickListener onItemClickListener = new MainAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            mainPresenter.onItemClick(position);
        }
    };

    private void initAttempt(){
        if(!databaseInit()){
            splashPresenter = new SplashPresenter(this);
            splashPresenter.init();
            splashPresenter.getAllCountries();
        }
    }

    @Override
    public void showAlarm(boolean networkAvailable){
        TextView alarmBox = findViewById(R.id.textViewAlarm);

        if(alarmBox != null){
            if(networkAvailable){

                alarmBox.setText(getString(R.string.okInternet));
                alarmBox.setBackgroundColor(Color.GREEN);
                alarmBox.setTextColor(Color.BLACK);

                Handler handler = new Handler();
                Runnable delay = () -> alarmBox.setVisibility(View.GONE);
                handler.postDelayed(delay, 1500);

                initAttempt();
            } else {
                alarmBox.setVisibility(View.VISIBLE);
                alarmBox.setText(getString(R.string.noInternet));
                alarmBox.setBackgroundColor(Color.RED);
                alarmBox.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public void initDone(){
        setBooleanPreference(SETTINGS, INITIALIZATION, true);
        initView();
    }

    @Override
    public void initFailed(){
        setBooleanPreference(SETTINGS, INITIALIZATION, false);
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

    private boolean databaseInit(){
        return getBooleanPreference(SETTINGS, INITIALIZATION);
    }
}


