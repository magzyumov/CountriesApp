package ru.magzyumov.countries.model.response;

import java.util.List;

import ru.magzyumov.countries.model.database.Countries;

public class CountriesParsed implements ICountriesParsed {
    private List<Countries> dataSource;

    public List<Countries> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<Countries> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Countries getCountry(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

}
