package ru.magzyumov.countries.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"})})

public class Countries {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "capital")
    public String capital;

    @ColumnInfo(name = "currency")
    public String currency;

    @ColumnInfo(name = "flagUrl")
    public String flagUrl;
}