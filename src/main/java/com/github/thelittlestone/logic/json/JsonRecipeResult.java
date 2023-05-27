package com.github.thelittlestone.logic.json;


/**
 * Created by theLittleStone on 2023/5/26.
 */
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
