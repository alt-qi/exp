package ru.altqi.physx.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.altqi.physx.R;
import ru.altqi.physx.formulas.FormulaCard;
import ru.noties.jlatexmath.JLatexMathView;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final FormulasDBWrapper db;
    public ArrayList<FormulaCard> formulas;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView formulaNameView;
        final JLatexMathView formulaExpressionView;
        final ImageButton addToFavoritesButton;
        final LinearLayout bgLayout;

        ViewHolder(View view){
            super(view);
            formulaNameView = view.findViewById(R.id.formula_name);
            formulaExpressionView = view.findViewById(R.id.formula_expression);
            addToFavoritesButton = view.findViewById(R.id.add_to_favorites);
            bgLayout = view.findViewById(R.id.background_layout);
        }
    }

    public FormulaAdapter(Context context, ArrayList<FormulaCard> formulas, FormulasDBWrapper db) {
        this.inflater = LayoutInflater.from(context);
        this.formulas = formulas;
        this.db = db;
    }

    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.formula_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FormulaCard formulaCard = formulas.get(position);

        holder.formulaExpressionView.setLatex(formulaCard.expression);
        holder.formulaNameView.setText(formulaCard.name);
        holder.addToFavoritesButton.setActivated(formulaCard.isFavorite);
        holder.bgLayout.setBackgroundColor(formulaCard.bgColor);

        holder.addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isActivated()) {
                    db.deleteFormulaCardFromFavorites(formulaCard.name);
                    view.setActivated(false);
                    Toast.makeText(inflater.getContext(), "Формула удалена из избранного.", Toast.LENGTH_SHORT).show();
                } else {
                    db.addFormulaCardToFavorites(formulaCard.name);
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
