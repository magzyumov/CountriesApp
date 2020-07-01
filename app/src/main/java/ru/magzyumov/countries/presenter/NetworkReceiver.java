package ru.magzyumov.countries.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import ru.magzyumov.countries.Constants;


public class NetworkReceiver extends BroadcastReceiver implements Constants {
    private Context context;
    private List<IBroadcastListener> listeners;

    public NetworkReceiver(Context context){
        this.context = context;
        this.listeners = new ArrayList<>();
    }

    public void addListener(IBroadcastListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IBroadcastListener listener) {
        this.listeners.remove(listener);
    }

    private void showAlarmToListeners(boolean networkAvailable){
        for (IBroadcastListener listener:listeners) {
            listener.showAlarm(networkAvailable);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOnline;
        try
        {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            isOnline = (netInfo != null && netInfo.isConnected());

            showAlarmToListeners(isOnline);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

