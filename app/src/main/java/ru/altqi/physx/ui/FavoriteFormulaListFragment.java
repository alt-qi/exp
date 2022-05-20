package ru.altqi.physx.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.altqi.physx.FavoriteFormulaAdapter;
import ru.altqi.physx.R;
import ru.altqi.physx.data.FormulaDatabase;

public class FavoriteFormulaListFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteFormulaAdapter adapter;

    public FavoriteFormulaListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_formula_list, container, false);

        adapter = new FavoriteFormulaAdapter(inflater.getContext(), FormulaDatabase.getDatabase(getActivity()).formulaDao());

        recyclerView = view.findViewById(R.id.favorite_formula_list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}