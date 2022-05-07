package ru.altqi.physx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class MainActivity extends AppCompatActivity {

    ArrayList<Formula> formulas = new ArrayList<>();
    Button insertFormulaButton, resetDBButton;
    EditText formulaNameInput, formulaExpressionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.formulas_list);
//
//        for (int i = 0; i < recyclerView.getChildCount(); i++) {
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
//            Button a = viewHolder.itemView.findViewById(R.id.add_to_favorites);
//        }


        resetDBButton = findViewById(R.id.reset_db);
        insertFormulaButton = findViewById(R.id.insert_formula);

        formulaExpressionInput = findViewById(R.id.formula_expression_input);
        formulaNameInput = findViewById(R.id.formula_name_input);

        OpenHelper dbHelper = new OpenHelper(this);
        FormulasDBWrapper db = new FormulasDBWrapper(dbHelper.getReadableDatabase());

        formulas = db.getFormulasList();

        FormulaAdapter adapter = new FormulaAdapter(this, formulas);
        recyclerView.setAdapter(adapter);

        insertFormulaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Formula formula = new Formula(
                        formulaNameInput.getText().toString(),
                        formulaExpressionInput.getText().toString()
                );
                db.addFormula(formula);
                adapter.formulas.add(formula);
                adapter.notifyDataSetChanged();
            }
        });

        resetDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.onUpgrade(db.getDB(), 0, 0);
                adapter.formulas = db.getFormulasList();
                adapter.notifyDataSetChanged();
            }
        });
    }
}