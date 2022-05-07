package ru.altqi.physx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Formula> formulas;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView formulaNameView, formulaExpressionView;

        ViewHolder(View view){
            super(view);
            formulaNameView = view.findViewById(R.id.formula_name);
            formulaExpressionView = view.findViewById(R.id.formula_expression);
        }
    }

    FormulaAdapter(Context context, List<Formula> formulas) {
        this.inflater = LayoutInflater.from(context);
        this.formulas = formulas;
    }

    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.formula_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        Formula formula = formulas.get(position);
        holder.formulaExpressionView.setText(formula.expression);
        holder.formulaNameView.setText(formula.name);
    }

    @Override
    public int getItemCount() {
        return formulas.size();
    }
}
