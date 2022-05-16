package ru.altqi.physx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaDatabase;
import ru.altqi.physx.data.FormulaEntity;


public class FormulaListFragment extends Fragment {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    RecyclerView recyclerView;

    FormulaDatabase db;
    FormulaDao formulaDao;
    FormulaAdapter adapter;

    FormulaListFragment(FormulaDatabase db) {
        this.db = db;
        formulaDao = db.formulaDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_formula_list, container, false);

        List<FormulaEntity> formulaList = formulaDao.getAllFormulas();
        adapter = new FormulaAdapter(inflater.getContext(), formulaList, formulaDao);

        recyclerView = view.findViewById(R.id.formulas_list);
        recyclerView.setAdapter(adapter);

        return view;

//        resetDBButton.setOnClickListener(new ResetTablesButtonOnClickListener());
//        createFormulaButton.setOnClickListener(new CreateFormulaButtonOnClickListener());
    }
}