package ru.magzyumov.countries.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Countries.class}, version = 1, exportSchema = false)
public abstract class CountriesDatabase extends RoomDatabase {
    public abstract ICountriesDao getCountriesDao();
}