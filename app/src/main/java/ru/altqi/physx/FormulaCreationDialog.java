package ru.altqi.physx;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.altqi.physx.db.FormulaAdapter;
import ru.altqi.physx.db.FormulasDBWrapper;
import ru.altqi.physx.formulas.FormulaCard;

public class FormulaCreationDialog extends DialogFragment {

    final FormulaAdapter adapter;
    final FormulasDBWrapper db;

    FormulaCreationDialog (FormulaAdapter adapter, FormulasDBWrapper db) {
        super();
        this.adapter = adapter;
        this.db = db;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Создание формулы")
                .setView(R.layout.formula_creation_dialog)
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Создать", new CreateFormulaButtonOnClickListener())
                .create();
    }

    class CreateFormulaButtonOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == DialogInterface.BUTTON_POSITIVE) {

                TextInputEditText formulaNameInput, formulaExpressionInput;
                formulaNameInput = getDialog().findViewById(R.id.formula_name_input);
                formulaExpressionInput = getDialog().findViewById(R.id.formula_expression_input);

                FormulaCard formula = new FormulaCard(
                        formulaNameInput.getText().toString(),
                        formulaExpressionInput.getText().toString(),
                        false, getResources().getColor(R.color.primaryLightColor)
                );

                db.addFormulaCard(formula);
                adapter.formulas.add(formula);
                adapter.notifyItemInserted(adapter.getItemCount()-1);

                Toast.makeText(getContext(), "Формула создана!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

