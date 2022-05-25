package ru.altqi.exp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.altqi.exp.data.FormulaDatabase;
import ru.altqi.exp.data.FormulaEntity;

public class FormulaListViewModel extends AndroidViewModel {
    public FormulaDatabase db;
    public LiveData<List<FormulaEntity>> liveData;

    public FormulaListViewModel(@NonNull Application application) {
        super(application);
        db = FormulaDatabase.getDatabase(application);
        liveData = db.formulaDao().getFormulasLiveData();
    }
}
