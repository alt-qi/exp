package ru.altqi.physx.data;

import androidx.lifecycle.MutableLiveData;
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
    void addFormula(FormulaEntity formula);

    @Delete
    void deleteFormula(FormulaEntity formula);

    @Query("DELETE FROM formulas;")
    void clearAll();

    @Query("UPDATE formulas SET is_favorite = 1 WHERE name = :formulaName;")
    void addFormulaToFavorites(String formulaName);

    @Query("UPDATE formulas SET is_favorite = 0 WHERE name = :formulaName;")
    void deleteFormulaFromFavorites(String formulaName);
}
