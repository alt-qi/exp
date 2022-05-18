package ru.altqi.physx.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.altqi.physx.FormulaListViewModel;
import ru.altqi.physx.R;
import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaDatabase;
import ru.altqi.physx.data.FormulaEntity;


public class FormulaListFragment extends Fragment {

    RecyclerView recyclerView;
    FormulaAdapter adapter;

    public FormulaListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_formula_list, container, false);
        FormulaListViewModel viewModel = new ViewModelProvider(getActivity()).get(FormulaListViewModel.class);

        adapter = new FormulaAdapter(inflater.getContext(), viewModel.liveData.getValue(),
                FormulaDatabase.getDatabase(getActivity()).formulaDao());

        recyclerView = view.findViewById(R.id.formulas_list);
        recyclerView.setAdapter(adapter);

        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<List<FormulaEntity>>() {
            @Override
            public void onChanged(List<FormulaEntity> formulas) {
                adapter.formulas = formulas;
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}