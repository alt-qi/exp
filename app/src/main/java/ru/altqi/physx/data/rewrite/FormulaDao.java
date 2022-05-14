package ru.altqi.physx.data.rewrite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FormulaDao {
    @Query("SELECT * FROM formulas")
    List<FormulaEntity> getAllFormulas();

    @Insert
    void addFormula(FormulaEntity formula);

    @Delete
    void deleteFormula(FormulaEntity formula);
}
