package ru.altqi.physx;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Scanner;

public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "formulas";
    private final Context context;

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static ArrayList<Formula> getFormulasFromFile(Context context) {
        Scanner s = new Scanner(context.getResources().openRawResource(R.raw.formulas)).useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";

        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Formula>>(){}.getType());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "expression TEXT," +
                "is_favorite INTEGER DEFAULT 0);";
        db.execSQL(query);

        ArrayList<Formula> formulas = getFormulasFromFile(this.context);
        for (Formula formula: formulas) {
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_NAME + " (name, expression) VALUES (?, ?);",
                    new String[] {formula.name, formula.expression});
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }
}