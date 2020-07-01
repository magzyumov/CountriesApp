package ru.magzyumov.countries.presenter;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.magzyumov.countries.App;
import ru.magzyumov.countries.model.response.CountriesParsed;
import ru.magzyumov.countries.model.response.ICountriesParsed;
import ru.magzyumov.countries.model.database.Countries;
import ru.magzyumov.countries.model.database.ICountriesDao;
import ru.magzyumov.countries.view.IMainView;


public class MainPresenter {
    private IMainView mainActivity;
    private ICountriesDao countriesDao;
    private CountriesParsed countriesParsed;

    public MainPresenter(IMainView mainActivity){
        this.mainActivity = mainActivity;
    }

    public void onItemClick(int position){
        countriesDao = App.getInstance().getCountriesDao();
        countriesDao.getCountryById(position + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Countries>() {
                    @Override
                    public void onSuccess(Countries country) {
                        mainActivity.showCountry(country);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR","onError: " + Thread.currentThread().getName() + " " + e.getMessage());
                    }
                });

    }

    public ICountriesParsed getCountries() {
        return countriesParsed;
    }

    public void init() {
        countriesParsed = new CountriesParsed();
        countriesDao = App.getInstance().getCountriesDao();
        countriesDao.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Countries>>() {
                    @Override
                    public void onSuccess(List<Countries> countries) {
                        countriesParsed.setDataSource(countries);
                        mainActivity.dataReady();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", "onError: " + Thread.currentThread().getName() + " " + e.getMessage());
                    }
                });
    }
}
