package ru.altqi.physx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.altqi.physx.data.FormulaDao;
import ru.altqi.physx.data.FormulaEntity;
import ru.noties.jlatexmath.JLatexMathView;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final FormulaDao formulaDao;
    public List<FormulaEntity> formulas;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView formulaNameView;
        final JLatexMathView formulaExpressionView;
        final ImageButton addToFavoritesButton;

        ViewHolder(View view){
            super(view);
            formulaNameView = view.findViewById(R.id.formula_name);
            formulaExpressionView = view.findViewById(R.id.formula_expression);
            addToFavoritesButton = view.findViewById(R.id.add_to_favorites);
        }
    }

    public FormulaAdapter(Context context, List<FormulaEntity> formulas, FormulaDao formulaDao) {
        this.inflater = LayoutInflater.from(context);
        this.formulas = formulas;
        this.formulaDao = formulaDao;
    }

    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.formula_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        FormulaEntity formulaEntity = formulas.get(position);

        holder.formulaExpressionView.setLatex(formulaEntity.expression);
        holder.formulaNameView.setText(formulaEntity.name);
        holder.addToFavoritesButton.setActivated(formulaEntity.isFavorite);

        holder.addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isActivated()) {
                    formulaDao.deleteFormulaFromFavorites(formulaEntity.name);
                    view.setActivated(false);
                    Toast.makeText(inflater.getContext(), "Формула удалена из избранного.", Toast.LENGTH_SHORT).show();
                } else {
                    formulaDao.addFormulaToFavorites(formulaEntity.name);
                    view.setActivated(true);
                    Toast.makeText(inflater.getContext(), "Формула добавлена в избранное!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return formulas.size();
    }
}
