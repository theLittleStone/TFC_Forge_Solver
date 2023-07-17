package com.github.thelittlestone.logic.json;


import com.github.thelittlestone.exception.RequirementConvertException;
import com.github.thelittlestone.logic.MainLogic;
import com.github.thelittlestone.logic.components.ActionCombination;

import java.util.Objects;

/**
 * Created by theLittleStone on 2023/5/26.
 */
public class JsonRecipe {
    public JsonRecipeInput input;
    public JsonRecipeResult result;
    public JsonRecipeRules rules;
    public int value = 0;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonRecipe that = (JsonRecipe) o;

        if (!Objects.equals(input, that.input)) return false;
        if (!Objects.equals(result, that.result)) return false;
        return Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        int result1 = input != null ? input.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (rules != null ? rules.hashCode() : 0);
        return result1;
    }
}

