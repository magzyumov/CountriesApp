package ru.magzyumov.countries.model.database;


import java.util.List;

public interface ICountriesSource {

    void loadCountries();

    int getCountCountries();

    void addCountry(Countries country);

    void addCountries(List<Countries> countries);

    void updateCountry(Countries country);

    public void removeCountry(int id);

    Countries getCountryById(int id);

    List<Countries> getCountries();
}
