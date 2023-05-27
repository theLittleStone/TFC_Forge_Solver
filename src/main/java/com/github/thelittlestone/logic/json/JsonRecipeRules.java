package com.github.thelittlestone.logic.json;

import com.github.thelittlestone.exception.RequirementConvertException;
import com.github.thelittlestone.logic.components.Requirement;
import com.github.thelittlestone.logic.components.RequirementCombination;

import java.util.ArrayList;


/**
 * Created by theLittleStone on 2023/5/26.
 */
public class JsonRecipeRules extends ArrayList<String>{

    public RequirementCombination toRequirementCombination() throws RequirementConvertException {
        if(size() == 0){
            throw new RequirementConvertException("空的JsonRecipeRules");
        }else if(size() == 1){
            return new RequirementCombination(new Requirement(get(0)));
        }else if (size() == 2){
            return new RequirementCombination(new Requirement(get(0)),
                    new Requirement(get(1)));
        }else {
            return new RequirementCombination(new Requirement(get(0)),
                    new Requirement(get(1)),
                    new Requirement(get(2)));
        }
    }

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
