package com.github.thelittlestone.logic.json;


import java.util.Objects;

/**
 * Created by theLittleStone on 2023/5/26.
 */

public class JsonRecipeInput {
    public String tag = "";
    public String item = "";

    public String toStr(){
        if (!tag.equals("")){
            return tag;
        }else if (!item.equals("")){
            return item;
        }else {
            return "";
        }
    }

    @Override
    public String toString() {
        if (!tag.equals("")) {
            return "JsonRecipeInput{" +
                    "tag='" + tag + '\'' +
                    '}';
        }else if (!item.equals("")){
            return "JsonRecipeInput{" +
                    "item='" + item + '\'' +
                    '}';
        }else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonRecipeInput that = (JsonRecipeInput) o;

        if (!Objects.equals(tag, that.tag)) return false;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        int result = tag != null ? tag.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
