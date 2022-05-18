package ru.altqi.physx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.altqi.physx.data.FormulaDatabase;
import ru.altqi.physx.data.FormulaEntity;

public class FormulaListViewModel extends AndroidViewModel {

    FormulaDatabase db;
    public MutableLiveData<List<FormulaEntity>> liveData = new MutableLiveData<>();

    public FormulaListViewModel(@NonNull Application application) {
        super(application);
        db = FormulaDatabase.getDatabase(application);
        updateFormulaList();
    }

    public void updateFormulaList() {
        liveData.setValue(db.formulaDao().getAllFormulas());
    }
}
