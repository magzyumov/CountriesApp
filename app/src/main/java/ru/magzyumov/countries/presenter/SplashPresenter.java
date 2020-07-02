package ru.magzyumov.countries.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.magzyumov.countries.App;
import ru.magzyumov.countries.Constants;
import ru.magzyumov.countries.model.database.Countries;
import ru.magzyumov.countries.model.database.ICountriesDao;
import ru.magzyumov.countries.model.response.Country;
import ru.magzyumov.countries.view.ISplashView;

public class SplashPresenter implements Constants {
    private ISplashView splashActivity;
    private ICountriesRequest iCountriesRequest;
    private Retrofit retrofit;
    private ICountriesDao countriesDao;

    public SplashPresenter (ISplashView splashActivity){
        this.splashActivity = splashActivity;
    }

    public void init(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(COUNTRIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        this.iCountriesRequest = retrofit.create(ICountriesRequest.class);

        countriesDao = App.getInstance().getCountriesDao();

    }

    public void getAllCountries(){

        iCountriesRequest.getCountriesFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(parseResponseFromServer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Countries>>() {
                    @Override
                    public void onSuccess(List<Countries> countries) {
                        loadDatabase(countries);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR","onError: " + Thread.currentThread().getName() + " " + e.getMessage());
                        splashActivity.initFailed();
                    }
                });
    }

    private void loadDatabase (List<Countries> countries){
        countriesDao.insertCountries(countries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        splashActivity.initDone();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR","onError: " + Thread.currentThread().getName() + " " + e.getMessage());
                        splashActivity.initFailed();
                    }
                });
    }

    private Function<List<Country>, List<Countries>> parseResponseFromServer = countries -> {
        List<Countries> result = new ArrayList<>();
        for (Country country : countries) {
            Countries resultCountry = new Countries();
            resultCountry.name = country.getName();
            resultCountry.capital = country.getCapital();
            resultCountry.currency = country.getCurrencies().get(0).getName();
            resultCountry.flagUrl = country.getFlag();
            result.add(resultCountry);
        }
        return result;
    };
}
