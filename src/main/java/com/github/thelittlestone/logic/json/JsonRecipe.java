package com.github.thelittlestone.logic.json;


import com.github.thelittlestone.exception.RequirementConvertException;
import com.github.thelittlestone.logic.MainLogic;
import com.github.thelittlestone.logic.components.ActionCombination;

/**
 * Created by theLittleStone on 2023/5/26.
 */
public class JsonRecipe {
    public JsonRecipeInput input;
    public JsonRecipeResult result;
    public JsonRecipeRules rules;
    public int value;

    @Override
    public String toString() {
        return "JsonRecipe{" +
                "input=" + input + ",\r\n" +
                "result=" + result + ",\r\n" +
                "rules=" + rules + ",\r\n" +
                "value=" + value + ",\r\n" +
                '}';
    }

    public ActionCombination solve(){
        try {
            return MainLogic.solve(value, rules.toRequirementCombination());
        } catch (RequirementConvertException e) {
            e.printStackTrace();
            return null;
        }
    }
}

