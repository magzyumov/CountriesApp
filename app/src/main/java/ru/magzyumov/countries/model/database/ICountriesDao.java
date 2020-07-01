package ru.magzyumov.countries.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ICountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(Countries country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountries(List<Countries> countries);

    @Update
    void updateCountry(Countries country);

    @Delete
    void deleteCountry(Countries country);

    @Query("SELECT * FROM Countries")
    List<Countries> getAllCountries();

    @Query("DELETE FROM Countries WHERE id = :id")
    void deteleCountryById(int id);

    @Query("SELECT * FROM Countries WHERE id = :id")
    Countries getCountryById(int id);

    @Query("SELECT COUNT() FROM Countries")
    int getCountCountries();
}
