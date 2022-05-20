package ru.altqi.physx.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;

import ru.altqi.physx.R;

@Database(entities = {FormulaEntity.class}, version = 1)
public abstract class FormulaDatabase extends RoomDatabase {
    private static final String DB_NAME = "app";
    private static FormulaDatabase INSTANCE;


    public static FormulaDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public static FormulaDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                FormulaDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            getDatabase(context).formulaDao()
                                    .insertAllFormulas();
                        });
                    }
                })
                .build();
    }

    public abstract FormulaDao formulaDao();
}
