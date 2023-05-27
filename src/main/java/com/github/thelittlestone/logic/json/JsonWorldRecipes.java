package com.github.thelittlestone.logic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by theLittleStone on 2023/5/26.
 */
public class JsonWorldRecipes {
    public String worldName;
    public ArrayList<JsonRecipe> recipes;

    public JsonWorldRecipes(String worldName, String jsonContent) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.worldName = worldName;
        try {
            this.recipes = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            this.recipes = new ArrayList<>();
            throw new RuntimeException(e);
        }
    }
    public JsonWorldRecipes(String worldName){
        this.worldName = worldName;
        this.recipes = new ArrayList<>();
    }
    public JsonWorldRecipes(){
        this("");
    }


    public String generateJsonText() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(this);
    }

    public ArrayList<String> allInputs(){
        HashSet<String> re = new HashSet<>();
        for (JsonRecipe recipe : recipes) {
            re.add(recipe.input.toStr());
        }
        return new ArrayList<>(re);
    }

    public ArrayList<JsonRecipeResult> allResults(String input){
        HashSet<JsonRecipeResult> re = new HashSet<>();
        for (JsonRecipe recipe : recipes) {
            if (input.equals(recipe.input.toStr())){
                re.add(recipe.result);
            }
        }
        return new ArrayList<>(re);
    }

    public JsonRecipe recipe(String input, String result){
        for (JsonRecipe recipe : recipes) {
            if (recipe.input.toStr().equals(input) && recipe.result.item.equals(result)){
                return recipe;
            }
        }
        return null;
    }
}
