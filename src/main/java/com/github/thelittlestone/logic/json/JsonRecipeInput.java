package com.github.thelittlestone.logic.json;

public class JsonRecipeInput {
    public String tag = "";
    public String item = "";

    public String getStr(){
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
}
