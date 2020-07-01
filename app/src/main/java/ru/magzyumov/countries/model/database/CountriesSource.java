package ru.magzyumov.countries.model.database;

import java.util.List;

public class CountriesSource implements ICountriesSource {

    private final ICountriesDao countriesDao;
    private List<Countries> countries;

    public CountriesSource(ICountriesDao countriesDao){
        this.countriesDao = countriesDao;
        getCountries();
    }

    public List<Countries> getCountries(){
        if (countries == null){
            loadCountries();
        }
        return countries;
    }

    public void loadCountries(){
        countries = countriesDao.getAllCountries();
    }

    public int getCountCountries(){
        return countriesDao.getCountCountries();
    }

    public void addCountry(Countries country){
        countriesDao.insertCountry(country);
        loadCountries();
    }

    public void addCountries(List<Countries> countries){
        countriesDao.insertCountries(countries);
        loadCountries();
    }

    public void updateCountry(Countries country){
        countriesDao.updateCountry(country);
        loadCountries();
    }

    public void removeCountry(int id){
        countriesDao.deteleCountryById(id);
        loadCountries();
    }

    public Countries getCountryById(int id){
        return countriesDao.getCountryById(id);
    }
}