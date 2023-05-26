package com.github.thelittlestone.logic.json;



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
}

