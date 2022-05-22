package ru.altqi.exp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import ru.altqi.exp.FormulaListViewModel;
import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDao;
import ru.altqi.exp.data.FormulaDatabase;
import ru.altqi.exp.data.FormulaEntity;
import ru.noties.jlatexmath.JLatexMathView;

public class FormulaCreationDialog extends DialogFragment {

    final FormulaDao formulaDao;
    JLatexMathView formulaExpressionPreview;
    TextInputEditText formulaNameInput, formulaExpressionInput;

    Handler handler = new Handler();
    long showPreviewDelay = 400; // время, которое нужно будет подождать после завершения ввода,
                                 // чтобы отобразился предпросмотр формулы

    public FormulaCreationDialog() {
        this.formulaDao = FormulaDatabase.getDatabase(getActivity()).formulaDao();
    }

    final private Runnable expressionInputFinishChecker = () ->
            formulaExpressionPreview.setLatex(formulaExpressionInput.getText().toString());

    private final TextWatcher expressionInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            // удаляем отложенный запуск Runnable, т. к. пользователь изменил текст
            handler.removeCallbacks(expressionInputFinishChecker);
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (s.length() > 0) {
                // создаём отложенный запуск Runnable после завершения ввода текста
                handler.postDelayed(expressionInputFinishChecker, showPreviewDelay);
            }
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Создание формулы")
                .setView(R.layout.dialog_formula_creation)
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Создать", new CreateFormulaButtonOnClickListener())
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        formulaExpressionPreview = getDialog().findViewById(R.id.formula_expression_preview);
        formulaNameInput = getDialog().findViewById(R.id.formula_name_input);
        formulaExpressionInput = getDialog().findViewById(R.id.formula_expression_input);

        formulaExpressionPreview.setLatex("");
        formulaExpressionInput.addTextChangedListener(expressionInputWatcher);
    }

    class CreateFormulaButtonOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == DialogInterface.BUTTON_POSITIVE) {

                FormulaListViewModel viewModel = new ViewModelProvider(getActivity()).get(FormulaListViewModel.class);
                FormulaEntity formulaEntity = new FormulaEntity();

                formulaEntity.name = formulaNameInput.getText().toString().trim();
                formulaEntity.expression = formulaExpressionInput.getText().toString().trim();
                formulaEntity.isFavorite = false;

                formulaDao.addFormula(formulaEntity);
                viewModel.updateFormulaList();

                Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),
                        "Формула создана!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}


