package ru.altqi.exp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.altqi.exp.data.FormulaDao;
import ru.altqi.exp.data.FormulaEntity;
import ru.noties.jlatexmath.JLatexMathView;


public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final FormulaDao formulaDao;
    public List<FormulaEntity> formulaList;

    public FormulaAdapter(Context context, FormulaDao formulaDao) {
        this.inflater = LayoutInflater.from(context);
        this.formulaDao = formulaDao;
        this.formulaList = formulaDao.getFormulaList();
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
            int formulaIndex = getFormulaIndexByName(formulaName);
            formulaDao.deleteFormula(formulaName);
            formulaList.remove(formulaIndex);
            notifyItemRemoved(formulaIndex);
        }

        int getFormulaIndexByName(String formulaName) {
            for (int i = 0; i < formulaList.size(); i++) {
                if (formulaList.get(i).name.equals(formulaName)) {
                    return i;
                }
            }
            return 0;
        }

    }

    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_formula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        FormulaEntity formulaEntity = formulaList.get(position);

        holder.formulaExpressionView.setLatex(formulaEntity.expression);
        holder.formulaNameView.setText(formulaEntity.name);
        holder.addToFavoritesButton.setActivated(formulaEntity.isFavorite);
    }

    @Override
    public int getItemCount() {
        return formulaList.size();
    }
}
