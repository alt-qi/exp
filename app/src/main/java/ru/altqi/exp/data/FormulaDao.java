package ru.altqi.exp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FormulaDao {
    @Query("SELECT * FROM formulas;")
    List<FormulaEntity> getFormulaList();

    @Query("SELECT * FROM formulas;")
    LiveData<List<FormulaEntity>> getFormulasLiveData();

    @Insert
    void insertAllFormulas(FormulaEntity... dataEntities);

    @Insert
    void addFormula(FormulaEntity formula);

    @Query("DELETE FROM formulas WHERE name = :formulaName;")
    void deleteFormula(String formulaName);

    @Query("DELETE FROM formulas;")
    void deleteAllFormulas();

    @Query("SELECT * FROM formulas WHERE is_favorite = 1;")
    List<FormulaEntity> getFavoriteFormulas();

    @Query("UPDATE formulas SET is_favorite = 1 WHERE name = :formulaName;")
    void addFormulaToFavorites(String formulaName);

    @Query("UPDATE formulas SET is_favorite = 0 WHERE name = :formulaName;")
    void deleteFormulaFromFavorites(String formulaName);

    @Query("UPDATE formulas SET is_favorite = 0 WHERE is_favorite = 1;")
    void deleteAllFromFavorites();
}
