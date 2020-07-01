package ru.magzyumov.countries.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ICountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCountry(Countries country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCountries(List<Countries> countries);

    @Query("SELECT * FROM Countries")
    Single<List<Countries>> getAllCountries();

    @Query("SELECT * FROM Countries WHERE id = :id")
    Single<Countries> getCountryById(int id);
}
