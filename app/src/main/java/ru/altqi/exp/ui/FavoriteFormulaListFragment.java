package ru.altqi.exp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.altqi.exp.FavoriteFormulaListAdapter;
import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDatabase;

public class FavoriteFormulaListFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteFormulaListAdapter adapter;

    public FavoriteFormulaListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_formula_list, container, false);

        adapter = new FavoriteFormulaListAdapter(inflater.getContext(), FormulaDatabase.getDatabase(getActivity()).formulaDao());

        recyclerView = view.findViewById(R.id.favorite_formula_list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorites, R.id.nav_add_formula, R.id.nav_settings
        ).build();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }
}