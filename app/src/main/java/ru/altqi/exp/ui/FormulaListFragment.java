package ru.altqi.exp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import ru.altqi.exp.FormulaAdapter;
import ru.altqi.exp.FormulaListViewModel;
import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDatabase;


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

        adapter = new FormulaAdapter(inflater.getContext(),
                FormulaDatabase.getDatabase(getActivity()).formulaDao());

        recyclerView = view.findViewById(R.id.formulas_list);
        recyclerView.setAdapter(adapter);

        viewModel.updateFormulaList();

        viewModel.liveData.observe(getViewLifecycleOwner(), formulas -> {
            int old_size = adapter.formulas.size();
            adapter.formulas = formulas;

            if (formulas.size() - old_size == 1) // костыль
                adapter.notifyItemInserted(adapter.formulas.size() - 1);
            else
                adapter.notifyDataSetChanged();
        });

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