package ru.altqi.exp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.altqi.exp.FormulaListAdapter;
import ru.altqi.exp.FormulaListViewModel;
import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDatabase;
import ru.altqi.exp.data.FormulaEntity;


public class FormulaListFragment extends Fragment {

    RecyclerView recyclerView;
    SearchView searchView;
    FormulaListAdapter adapter;
    FormulaDatabase db;

    public FormulaListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_formula_list, container, false);
        FormulaListViewModel viewModel = new ViewModelProvider(getActivity()).get(FormulaListViewModel.class);

        recyclerView = view.findViewById(R.id.formulas_list);
        searchView = view.findViewById(R.id.search_query_input);

        db = FormulaDatabase.getDatabase(getActivity());
        adapter = new FormulaListAdapter(inflater.getContext(),
                db.formulaDao());

        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFormulaList(newText);
                return true;
            }
        });

        viewModel.liveData.observe(getViewLifecycleOwner(), formulas -> {
            int old_size = adapter.formulaList.size();
            adapter.formulaList = formulas;
            searchView.setQuery("", false);

            if (formulas.size() - old_size == 1)
                // тут мы проверяем, что в списке стало на ОДИН элемент больше, а т. к.
                // появление в списке одного нового элемента может вызвать только создание формулы,
                // а формулы всегда добавляются в конец, то мы можем сообщить адаптеру о появлении
                // нового элемента в конце списка. Костыль, но ведь работает :)
                adapter.notifyItemInserted(adapter.formulaList.size() - 1);
            
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

    private void filterFormulaList(String query) {
        List<FormulaEntity> filteredList = new ArrayList<>();
        for (FormulaEntity formula: db.formulaDao().getFormulaList()) {
            if (formula.name.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(formula);
            }
        }
        adapter.formulaList = filteredList;
        adapter.notifyDataSetChanged();
    }
}
