package ru.altqi.exp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "formulas")
public class FormulaEntity {

    @PrimaryKey
    @NonNull
    public String name;

    public String expression;

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    public boolean isFavorite;

    public FormulaEntity(@NonNull String name, String expression, boolean isFavorite) {
        this.name = name;
        this.expression = expression;
        this.isFavorite = isFavorite;
    }
}
