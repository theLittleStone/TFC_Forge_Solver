package com.github.thelittlestone.logic.json;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonRecipeResult that = (JsonRecipeResult) o;

        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return item != null ? item.hashCode() : 0;
    }
}
