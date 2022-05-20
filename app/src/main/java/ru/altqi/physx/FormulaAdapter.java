package ru.altqi.physx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaEntity;
import ru.noties.jlatexmath.JLatexMathView;


public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final FormulaDao formulaDao;
    public List<FormulaEntity> formulas;

    public FormulaAdapter(Context context, FormulaDao formulaDao) {
        this.inflater = LayoutInflater.from(context);
        this.formulaDao = formulaDao;
        this.formulas = formulaDao.getAllFormulas();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView formulaNameView;
        final JLatexMathView formulaExpressionView;
        final ImageButton addToFavoritesButton;

        ViewHolder(View view) {
            super(view);

            formulaNameView = view.findViewById(R.id.formula_name_view);
            formulaExpressionView = view.findViewById(R.id.formula_expression_view);
            addToFavoritesButton = view.findViewById(R.id.add_to_favorites_btn);

            View navHostFragmentView = ((MainActivity) inflater.getContext()).findViewById(R.id.nav_host_fragment);

            addToFavoritesButton.setOnClickListener(view1 -> {
                if (!view1.isActivated()) {
                    formulaDao.addFormulaToFavorites(formulaNameView.getText().toString());
                    Snackbar.make(navHostFragmentView,
                            "Формула добавлена в избранное.", Snackbar.LENGTH_SHORT).show();
                } else {
                    formulaDao.deleteFormulaFromFavorites(formulaNameView.getText().toString());
                    Snackbar.make(navHostFragmentView,
                            "Формула удалена из избранного.", Snackbar.LENGTH_SHORT).show();
                }
                view1.setActivated(!view1.isActivated());
            });

            view.setOnLongClickListener(view1 -> {
                deleteFormulaByName(formulaNameView.getText().toString());
                Snackbar.make(navHostFragmentView, "Формула удалёна.", Snackbar.LENGTH_SHORT).show();
                return false;
            });
        }

        void deleteFormulaByName(String formulaName) {
            formulaDao.deleteFormulaByName(formulaName);
            for (int i = 0; i < formulas.size(); i++) {
                if (formulas.get(i).name.equals(formulaName)) {
                    formulas.remove(i);
                    notifyItemRemoved(i);
                    return;
                }
            }
        }

    }

    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_formula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        FormulaEntity formulaEntity = formulas.get(position);

        holder.formulaExpressionView.setLatex(formulaEntity.expression);
        holder.formulaNameView.setText(formulaEntity.name);
        holder.addToFavoritesButton.setActivated(formulaEntity.isFavorite);
    }

    @Override
    public int getItemCount() {
        return formulas.size();
    }
}
