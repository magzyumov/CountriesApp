package ru.magzyumov.countries;

import android.app.Application;
import android.os.SystemClock;

import androidx.room.Room;

import java.util.concurrent.TimeUnit;

import ru.magzyumov.countries.model.database.ICountriesDao;
import ru.magzyumov.countries.model.database.CountriesDatabase;

public class App extends Application {
    private static App instance;
    private CountriesDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));

        instance = this;

        database = Room.databaseBuilder(
                getApplicationContext(),
                CountriesDatabase.class,
                "countries")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public ICountriesDao getCountriesDao() { return database.getCountriesDao(); }
}
