package ru.altqi.exp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDatabase;

public class PreferencesScreenFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        FormulaDatabase db = FormulaDatabase.getDatabase(getLayoutInflater().getContext());
        Preference clearDBButton = findPreference("clear_db");
        Preference clearFavorites = findPreference("clear_favorites");

        clearDBButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                db.formulaDao().deleteAllFormulas();
                return true;
            }
        });

        clearFavorites.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                db.formulaDao().deleteAllFromFavorites();
                return true;
            }
        });
    }
}
