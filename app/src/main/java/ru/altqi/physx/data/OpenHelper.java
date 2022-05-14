package ru.altqi.physx.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Scanner;

import ru.altqi.physx.R;
import ru.altqi.physx.formulas.FormulaCard;

public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "formulas";
    private final Context context;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static ArrayList<FormulaCard> getFormulasFromFile(Context context) {
        Scanner s = new Scanner(context.getResources().openRawResource(R.raw.formula_cards)).useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.fromJson(json, new TypeToken<ArrayList<FormulaCard>>(){}.getType());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "name TEXT PRIMARY KEY," +
                "expression TEXT," +
                "is_favorite INTEGER DEFAULT 0," +
                "bg_color INTEGER DEFAULT " + Color.GRAY + ");";
        db.execSQL(query);

        ArrayList<FormulaCard> formulaCards = getFormulasFromFile(this.context);

        for (FormulaCard formulaCard: formulaCards) {
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_NAME + " (name, expression, " +
                            "bg_color) VALUES (?, ?, ?);",
                    new String[] {formulaCard.name, formulaCard.expression,
                            Integer.toString(formulaCard.bgColor)});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }
}