package ru.altqi.physx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.altqi.physx.db.FormulaAdapter;
import ru.altqi.physx.db.FormulasDBWrapper;
import ru.altqi.physx.db.OpenHelper;
import ru.altqi.physx.formulas.FormulaCard;

public class MainActivity extends AppCompatActivity {

    Button resetDBButton;
    FloatingActionButton createFormulaButton;
    RecyclerView recyclerView;

    ArrayList<FormulaCard> formulaCards = new ArrayList<>();
    OpenHelper dbHelper;
    FormulasDBWrapper db;
    FormulaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.formulas_list);
        resetDBButton = findViewById(R.id.reset_db);
        createFormulaButton = findViewById(R.id.create_formula_button);

        dbHelper = new OpenHelper(this);
        db = new FormulasDBWrapper(dbHelper.getWritableDatabase());

        formulaCards = db.getFormulaCardsList();

        adapter = new FormulaAdapter(this, formulaCards, db);
        recyclerView.setAdapter(adapter);

        resetDBButton.setOnClickListener(new ResetDBButtonOnClickListener());
        createFormulaButton.setOnClickListener(new CreateFormulaButtonOnClickListener());

    }

    class CreateFormulaButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            FormulaCreationDialog dialog = new FormulaCreationDialog(adapter, db);
            dialog.show(getSupportFragmentManager(), "fc");
        }
    }

    class ResetDBButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dbHelper.onUpgrade(db.getDB(), 0, 0);
            adapter.formulas = db.getFormulaCardsList();
            adapter.notifyDataSetChanged();
        }
    }
}