package ru.altqi.exp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.scilab.forge.jlatexmath.ParseException;

import java.util.ArrayList;

import ru.altqi.exp.R;
import ru.altqi.exp.data.FormulaDao;
import ru.altqi.exp.data.FormulaDatabase;
import ru.altqi.exp.data.FormulaEntity;
import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class FormulaCreationDialog extends DialogFragment {

    FormulaDao formulaDao;
    JLatexMathView formulaExpressionPreview;
    TextInputEditText formulaNameInput, formulaExpressionInput;
    ArrayList<String> existingNames = new ArrayList<>();
    Button positiveButton;

    Handler handler = new Handler();
    long showPreviewDelay = 600; // время, которое нужно будет подождать после завершения ввода,
                                 // чтобы отобразился предпросмотр формулы

    final private Runnable expressionPreviewTask = () -> {
        try {
            formulaExpressionPreview.setLatex(formulaExpressionInput.getText().toString());
        } catch (ParseException e) {
            // если произошла ошибка при обработке TeX-выражения, то тогда
            // просто игнорируем её и не обновляем предпросмотр
        }
    };

    private final TextWatcher formulaPreviewWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            // удаляем отложенный запуск Runnable, т. к. пользователь изменил текст
            handler.removeCallbacks(expressionPreviewTask);
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (s.length() > 0) {
                // создаём отложенный запуск Runnable после завершения ввода текста
                handler.postDelayed(expressionPreviewTask, showPreviewDelay);
            }
        }
    };

    private final TextWatcher formulaValidityWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // включаем кнопку, если:
            // 1. поля формулы и имени не пустые;
            // 2. указанное в данный момент имя формулы ещё не занято;
            // 3. TeX-выражение не содержит ошибок.
            positiveButton.setEnabled(formulaNameInput.getText().length() > 0 &&
                    formulaExpressionInput.getText().length() > 0 &&
                    !existingNames.contains(charSequence.toString()) &&
                    checkIfTexExpressionIsValid(formulaExpressionInput.getText().toString()));
        }
    };

    private final DialogInterface.OnClickListener createFormulaButtonOnClickListener = (dialogInterface, i) -> {
        if (i != DialogInterface.BUTTON_POSITIVE) return;
        FormulaEntity formulaEntity = new FormulaEntity(
                formulaNameInput.getText().toString().trim(),
                formulaExpressionInput.getText().toString().trim(),
                false);
        formulaDao.addFormula(formulaEntity);
        Snackbar.make(getActivity().findViewById(R.id.nav_host_fragment),
                "Формула создана!", Snackbar.LENGTH_SHORT).show();
    };

    public static boolean checkIfTexExpressionIsValid(String expression) {
        boolean isExpressionValid = true;
        try {
            JLatexMathDrawable.builder(expression).build();
        } catch (ParseException e) {
            isExpressionValid = false;
        }
        return isExpressionValid;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        formulaDao = FormulaDatabase.getDatabase(getContext()).formulaDao();
        for (FormulaEntity formula: formulaDao.getFormulaList()) existingNames.add(formula.name);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Создание формулы")
                .setView(R.layout.dialog_formula_creation)
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Создать", createFormulaButtonOnClickListener)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        positiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setEnabled(false);

        formulaExpressionPreview = getDialog().findViewById(R.id.formula_expression_preview);
        formulaNameInput = getDialog().findViewById(R.id.formula_name_input);
        formulaExpressionInput = getDialog().findViewById(R.id.formula_expression_input);

        formulaExpressionPreview.setLatex("");
        formulaExpressionInput.addTextChangedListener(formulaPreviewWatcher);

        formulaExpressionInput.addTextChangedListener(formulaValidityWatcher);
        formulaNameInput.addTextChangedListener(formulaValidityWatcher);
    }
}


