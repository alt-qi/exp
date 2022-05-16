package ru.altqi.physx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaDatabase;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_bar);

        db = Room.databaseBuilder(this, FormulaDatabase.class, "app")
                .allowMainThreadQueries()
                .build();

        formulaDao = db.formulaDao();

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new FormulaListFragment(db);
                        break;

                    case R.id.nav_add_formula:
                        FormulaCreationDialog dialog = new FormulaCreationDialog(formulaDao);
                        dialog.show(getSupportFragmentManager(), "fc");
                        break;

                    case R.id.nav_favorites:
                    case R.id.nav_settings:
                        fragment = new UncompletedFragment();
                        break;
                }

                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                return true;

            }
        });

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new FormulaListFragment(db)).commit();

//        recyclerView = findViewById(R.id.formulas_list);
//        resetDBButton = findViewById(R.id.reset_db);
//        createFormulaButton = findViewById(R.id.create_formula_button);
//
//        db = Room.databaseBuilder(this, FormulaDatabase.class, "app")
//                .allowMainThreadQueries()
//                .build();
//
//        formulaDao = db.formulaDao();
//
//        List<FormulaEntity> formulaList = formulaDao.getAllFormulas();
//
//        adapter = new FormulaAdapter(this, formulaList, formulaDao);
//        recyclerView.setAdapter(adapter);
//
//        resetDBButton.setOnClickListener(new ResetTablesButtonOnClickListener());
//        createFormulaButton.setOnClickListener(new CreateFormulaButtonOnClickListener());

    }

//    class CreateFormulaButtonOnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            FormulaCreationDialog dialog = new FormulaCreationDialog(adapter, formulaDao);
//            dialog.show(getSupportFragmentManager(), "fc");
//        }
//    }
//
//    class ResetTablesButtonOnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            formulaDao.clearAll();
//            adapter.formulas = formulaDao.getAllFormulas();
//            adapter.notifyDataSetChanged();
//        }
//    }
}