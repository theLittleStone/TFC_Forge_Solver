package com.github.thelittlestone.logic.json;

import java.util.ArrayList;

public class JsonRecipeRules extends ArrayList<String>{


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JsonRecipeRules = ").append("[");
        for (String rule : this) {
            sb.append(rule).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
