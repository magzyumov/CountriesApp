package ru.magzyumov.countries.presenter;

import ru.magzyumov.countries.App;
import ru.magzyumov.countries.model.response.CountriesParsed;
import ru.magzyumov.countries.model.response.ICountriesParsed;
import ru.magzyumov.countries.model.database.Countries;
import ru.magzyumov.countries.model.database.ICountriesDao;
import ru.magzyumov.countries.model.database.ICountriesSource;
import ru.magzyumov.countries.model.database.CountriesSource;
import ru.magzyumov.countries.view.IMainView;


public class MainPresenter {
    private IMainView mainActivity;
    private ICountriesDao countriesDao;
    private CountriesParsed countriesParsed;
    private ICountriesSource countriesSource;

    public MainPresenter(IMainView mainActivity){
        this.mainActivity = mainActivity;
    }

    public void onItemClick(int position){
        Countries country = countriesSource.getCountryById(position + 1);
        if(country != null) mainActivity.showCountry(country);
    }

    public ICountriesParsed getCountries() {
        return countriesParsed;
    }


    public void init(){
        countriesDao = App.getInstance().getCountriesDao();
        countriesSource = new CountriesSource(countriesDao);
        countriesParsed = new CountriesParsed();
        countriesParsed.setDataSource(countriesSource.getCountries());
    }
}
