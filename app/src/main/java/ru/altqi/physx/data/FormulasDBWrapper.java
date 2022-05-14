package ru.altqi.physx.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ru.altqi.physx.formulas.FormulaCard;

public class FormulasDBWrapper {

    private final SQLiteDatabase db;

    public FormulasDBWrapper(SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    public void addFormulaCard(FormulaCard formulaCard) {
        db.execSQL("INSERT INTO " + OpenHelper.TABLE_NAME + " VALUES (?, ?, ?, ?);",
                new String[] {formulaCard.name, formulaCard.expression,
                        formulaCard.isFavorite ? "1" : "0",
                        Integer.toString(formulaCard.bgColor)});
    }

    public void deleteFormulaCard(String formulaName) {
        db.execSQL("DELETE FROM " + OpenHelper.TABLE_NAME + " WHERE name = ?;",
                new String[] {formulaName});
    }

    public void addFormulaCardToFavorites(String formulaName) {
        db.execSQL("UPDATE " + OpenHelper.TABLE_NAME + " SET is_favorite = 1 WHERE name = ?;",
                new String[] {formulaName});
    }

    public void deleteFormulaCardFromFavorites(String formulaName) {
        db.execSQL("UPDATE " + OpenHelper.TABLE_NAME + " SET is_favorite = 0 WHERE name = ?;",
                new String[] {formulaName});
    }


    public ArrayList<FormulaCard> getFormulaCardsList() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + OpenHelper.TABLE_NAME + ";", null);
        ArrayList<FormulaCard> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FormulaCard formulaCard = new FormulaCard(
                        cursor.getString(0), cursor.getString(1),
                        cursor.getInt(2) == 1, cursor.getInt(3));
                list.add(formulaCard);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }
}
