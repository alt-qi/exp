package ru.altqi.physx.formulas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormulaCard extends Formula {

    final public boolean isFavorite;

    @Expose
    @SerializedName("bg_color")
    final public int bgColor;

    public FormulaCard(String name, String expression, boolean isFavorite, int bgColor) {
        super(name, expression);
        this.isFavorite = isFavorite;
        this.bgColor = bgColor;
    }


}
