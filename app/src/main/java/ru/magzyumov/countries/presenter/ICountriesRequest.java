package ru.magzyumov.countries.presenter;

import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;
import ru.magzyumov.countries.model.response.Country;


public interface ICountriesRequest {
    @GET("all")
    Single<List<Country>> getCountriesFromServer();
}
