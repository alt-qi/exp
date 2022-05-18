package ru.altqi.physx.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FormulaEntity.class}, version = 1)
public abstract class FormulaDatabase extends RoomDatabase {
    public abstract FormulaDao formulaDao();

    private static FormulaDatabase INSTANCE;
    private static final String DB_NAME = "app";

    public static FormulaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FormulaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            FormulaDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // TODO: Populate database
        }
    };
}
