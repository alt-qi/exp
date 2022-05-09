package ru.altqi.physx.formulas;

import com.google.gson.annotations.Expose;

class Formula {

    @Expose
    public String name;

    @Expose
    public String expression;

    public Formula(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

}
