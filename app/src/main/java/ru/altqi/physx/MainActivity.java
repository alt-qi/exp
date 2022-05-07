package ru.altqi.physx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ArrayList<Formula> formulas = new ArrayList<>();

    private ArrayList<Formula> getFormulasFromFile() {
        Scanner s = new Scanner(this.getResources().openRawResource(R.raw.formulas)).useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";

        Gson gson = new Gson();

        return gson.fromJson(json.toString(), new TypeToken<ArrayList<Formula>>(){}.getType());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formulas = getFormulasFromFile();

        RecyclerView recyclerView = findViewById(R.id.formulasList);
        FormulaAdapter adapter = new FormulaAdapter(this, formulas);
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        formulas.add(new Formula("Нагревание тела", "Q = c * m * Δt"));
        formulas.add(new Formula("Сгорание топлива", "Q = q * m"));
    }
}