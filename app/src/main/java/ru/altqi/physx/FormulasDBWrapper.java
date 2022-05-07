package ru.altqi.physx;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FormulasDBWrapper {

    private final SQLiteDatabase db;

    public FormulasDBWrapper(SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    public void addFormula(Formula formula) {
        db.execSQL("INSERT INTO " + OpenHelper.TABLE_NAME + "(name, expression) VALUES (?, ?);",
                new String[] {formula.name, formula.expression});
    }

    public void deleteFormula(int id) {
        db.execSQL("DELETE FROM " + OpenHelper.TABLE_NAME + " WHERE id = ?;",
                new String[] {Integer.toString(id)});
    }

    public ArrayList<Formula> getFormulasList() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + OpenHelper.TABLE_NAME, null);
        ArrayList<Formula> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Formula formula = new Formula(cursor.getString(1), cursor.getString(2));
                list.add(formula);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }
}
