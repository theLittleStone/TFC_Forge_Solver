package com.github.thelittlestone.logic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashSet;

public class JsonRawWorldRecipes {
    public String version;
    public HashSet<JsonRecipe> recipes;

    public JsonWorldRecipes toJsonWorldRecipes(String worldName){
        JsonWorldRecipes worldRecipes = new JsonWorldRecipes(worldName);
        worldRecipes.recipes = recipes;
        return worldRecipes;
    }
    public String generateJsonText() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(this);
    }
    public void merge(JsonRawWorldRecipes jrwr){
        this.recipes.addAll(jrwr.recipes);
    }
}
