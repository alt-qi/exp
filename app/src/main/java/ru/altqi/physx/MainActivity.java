package ru.altqi.physx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaDatabase;
import ru.altqi.physx.data.FormulaEntity;

public class MainActivity extends AppCompatActivity {

    Button resetDBButton;
    FloatingActionButton createFormulaButton;
    RecyclerView recyclerView;

    FormulaDatabase db;
    FormulaDao formulaDao;
    FormulaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.formulas_list);
        resetDBButton = findViewById(R.id.reset_db);
        createFormulaButton = findViewById(R.id.create_formula_button);

        db = Room.databaseBuilder(this, FormulaDatabase.class, "app")
                .allowMainThreadQueries()
                .build();

        formulaDao = db.formulaDao();

        List<FormulaEntity> formulaList = formulaDao.getAllFormulas();

        adapter = new FormulaAdapter(this, formulaList, formulaDao);
        recyclerView.setAdapter(adapter);

        resetDBButton.setOnClickListener(new ResetTablesButtonOnClickListener());
        createFormulaButton.setOnClickListener(new CreateFormulaButtonOnClickListener());

    }

    class CreateFormulaButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            FormulaCreationDialog dialog = new FormulaCreationDialog(adapter, formulaDao);
            dialog.show(getSupportFragmentManager(), "fc");
        }
    }

    class ResetTablesButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            formulaDao.clearAll();
            adapter.formulas = formulaDao.getAllFormulas();
            adapter.notifyDataSetChanged();
        }
    }
}