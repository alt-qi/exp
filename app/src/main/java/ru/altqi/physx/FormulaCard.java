package ru.altqi.physx;

import android.graphics.Color;

public class FormulaCard extends Formula {

    final Color gradientColor1;
    final Color gradientColor2;

    public FormulaCard(String name, String expression, Color gradientColor1, Color gradientColor2) {
        super(name, expression);

        this.gradientColor1 = gradientColor1;
        this.gradientColor2 = gradientColor2;
    }


}
