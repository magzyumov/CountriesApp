package ru.magzyumov.countries.model.response;

import ru.magzyumov.countries.model.database.Countries;

public interface ICountriesParsed {
    Countries getCountry(int position);
    int size();
}
