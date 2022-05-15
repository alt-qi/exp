package ru.altqi.physx;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaEntity;

public class FormulaCreationDialog extends DialogFragment {

    final FormulaAdapter adapter;
    final FormulaDao formulaDao;

    FormulaCreationDialog (FormulaAdapter adapter, FormulaDao formulaDao) {
        super();
        this.adapter = adapter;
        this.formulaDao = formulaDao;
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

//                FormulaCard formula = new FormulaCard(
//                        formulaNameInput.getText().toString(),
//                        formulaExpressionInput.getText().toString(),
//                        false, getResources().getColor(R.color.primaryLightColor)
//                );

                FormulaEntity formulaEntity = new FormulaEntity();

                formulaEntity.name = formulaNameInput.getText().toString();
                formulaEntity.expression = formulaExpressionInput.getText().toString();
                formulaEntity.isFavorite = false;

                formulaDao.addFormula(formulaEntity);

                adapter.formulas.add(formulaEntity);
                adapter.notifyItemInserted(adapter.getItemCount()-1);

                Toast.makeText(getContext(), "Формула создана!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
