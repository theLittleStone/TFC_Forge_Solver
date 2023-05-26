package com.github.thelittlestone.logic.json;

public class JsonRecipeResult {
    public String item;
    public int count;

    public JsonRecipeResult() {
        item = "";
        count = 1;
    }

    @Override
    public String toString() {
        return "JsonRecipeResult{" +
                "item='" + item + '\'' +
                ", count=" + count +
                '}';
    }
}
