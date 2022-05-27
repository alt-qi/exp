package ru.altqi.exp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.snackbar.Snackbar;

import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDatabase;

public class PreferencesScreenFragment extends PreferenceFragmentCompat {

    Preference clearDBButton, clearFavorites;
    FormulaDatabase db;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        db = FormulaDatabase.getDatabase(getLayoutInflater().getContext());
        clearDBButton = findPreference("clear_db");
        clearFavorites = findPreference("clear_favorites");

        clearDBButton.setOnPreferenceClickListener(preference -> {
            db.formulaDao().deleteAllFormulas();
            Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),
                    "Все формулы удалены.", Snackbar.LENGTH_SHORT).show();
            return true;
        });

        clearFavorites.setOnPreferenceClickListener(preference -> {
            db.formulaDao().deleteAllFromFavorites();
            Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),
                    "Избранное очищено.", Snackbar.LENGTH_SHORT).show();
            return true;
        });
    }
}
