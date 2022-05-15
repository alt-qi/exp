package ru.altqi.physx.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FormulaEntity.class}, version = 1)
public abstract class FormulaDatabase extends RoomDatabase {
    public abstract FormulaDao formulaDao();
}
