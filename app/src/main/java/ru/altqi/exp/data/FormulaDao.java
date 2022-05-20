package ru.altqi.exp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FormulaDao {
    @Query("SELECT * FROM formulas;")
    List<FormulaEntity> getAllFormulas();

    @Insert
    void insertAllFormulas(FormulaEntity... dataEntities);

    @Insert
    void addFormula(FormulaEntity formula);

    @Delete
    void deleteFormula(FormulaEntity formula);

    @Query("DELETE FROM formulas WHERE name = :formulaName;")
    void deleteFormulaByName(String formulaName);

    @Query("DELETE FROM formulas;")
    void deleteAllFormulas();

    @Query("SELECT * FROM formulas WHERE is_favorite = 1;")
    List<FormulaEntity> getFavoriteFormulas();

    @Query("UPDATE formulas SET is_favorite = 1 WHERE name = :formulaName;")
    void addFormulaToFavorites(String formulaName);

    @Query("UPDATE formulas SET is_favorite = 0 WHERE name = :formulaName;")
    void deleteFormulaFromFavorites(String formulaName);
}