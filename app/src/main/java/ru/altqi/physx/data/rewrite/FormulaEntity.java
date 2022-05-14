package ru.altqi.physx.data.rewrite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormulaEntity {
    @PrimaryKey
    public String name;

    public String expression;

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    public boolean isFavorite;
}
