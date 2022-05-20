package ru.altqi.exp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import ru.altqi.exp.FormulaListViewModel;
import ru.altqi.exp.MainActivity;
import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDao;
import ru.altqi.exp.data.FormulaDatabase;
import ru.altqi.exp.data.FormulaEntity;

public class FormulaCreationDialog extends DialogFragment {

//    final FormulaAdapter adapter;
    final FormulaDao formulaDao;

//    FormulaCreationDialog(FormulaAdapter adapter, FormulaDao formulaDao) {
//        super();
//        this.adapter = adapter;
//        this.formulaDao = formulaDao;
//    }

    public FormulaCreationDialog() {
        super();
        this.formulaDao = FormulaDatabase.getDatabase(getActivity()).formulaDao();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setTitle("Создание формулы")
                .setView(R.layout.dialog_formula_creation)
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Создать", new CreateFormulaButtonOnClickListener(
                        new ViewModelProvider(getActivity()).get(FormulaListViewModel.class)
                ))
                .create();
    }

    class CreateFormulaButtonOnClickListener implements DialogInterface.OnClickListener {
        FormulaListViewModel viewModel;

        CreateFormulaButtonOnClickListener(FormulaListViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == DialogInterface.BUTTON_POSITIVE) {

                TextInputEditText formulaNameInput, formulaExpressionInput;
                formulaNameInput = getDialog().findViewById(R.id.formula_name_input);
                formulaExpressionInput = getDialog().findViewById(R.id.formula_expression_input);

                FormulaEntity formulaEntity = new FormulaEntity();

                formulaEntity.name = formulaNameInput.getText().toString();
                formulaEntity.expression = formulaExpressionInput.getText().toString();
                formulaEntity.isFavorite = false;

                formulaDao.addFormula(formulaEntity);
                viewModel.updateFormulaList();

                Snackbar.make(((MainActivity) getActivity()).findViewById(R.id.nav_host_fragment),
                        "Формула создана!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}


