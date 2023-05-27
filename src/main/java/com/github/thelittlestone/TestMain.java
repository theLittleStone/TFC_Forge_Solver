package com.github.thelittlestone;


import com.github.thelittlestone.logic.components.ActionCombination;
import com.github.thelittlestone.logic.json.JsonRecipe;
import com.github.thelittlestone.logic.json.JsonRecipeInput;
import com.github.thelittlestone.logic.json.JsonRecipeResult;
import com.github.thelittlestone.logic.json.JsonRecipeRules;

import java.io.IOException;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        JsonRecipe jsonRecipe = new JsonRecipe();
        jsonRecipe.input = new JsonRecipeInput();
        jsonRecipe.input.tag = "forge:ingots/bismuth_bronze";
        jsonRecipe.result = new JsonRecipeResult();
        jsonRecipe.result.item = "tfc:metal/axe_head/bismuth_bronze";
        jsonRecipe.result.count = 1;
        jsonRecipe.rules = new JsonRecipeRules();
        jsonRecipe.rules.add("punch_last");
        jsonRecipe.rules.add("hit_second_last");
        jsonRecipe.rules.add("upset_third_last");
        jsonRecipe.value = 56;
        ActionCombination solve = jsonRecipe.solve();
        System.out.println(solve);
    }
}
