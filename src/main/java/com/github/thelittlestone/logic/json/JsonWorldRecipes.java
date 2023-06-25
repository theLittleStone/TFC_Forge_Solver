package com.github.thelittlestone.logic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.thelittlestone.translate.NameMappingTableLoader;
import com.github.thelittlestone.translate.NameMappingUnit;

import java.util.ArrayList;
import java.util.Comparator;
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

    //获取所有输入, 默认中文字典排序
    public ArrayList<NameMappingUnit> allInputs(){
        HashSet<NameMappingUnit> re = new HashSet<>();
        for (JsonRecipe recipe : recipes) {
            re.add(NameMappingTableLoader.get(recipe.input.toStr()));
        }
        ArrayList<NameMappingUnit> target = new ArrayList<>(re);
        target.sort((o1, o2) -> {
            Comparator<String> tComparator = Comparator.naturalOrder();
            return tComparator.compare(o1.toString(), o2.toString());
        });
        return target;
    }

    //获取输入的所有对应输出, 默认中文字典排序
    public ArrayList<NameMappingUnit> allResults(String input){
        ArrayList<JsonRecipeResult> results = allJsonRecipeResults(input);
        HashSet<NameMappingUnit> re = new HashSet<>();
        for (JsonRecipeResult result : results) {
            re.add(NameMappingTableLoader.get(result.item));
        }
        ArrayList<NameMappingUnit> target = new ArrayList<>(re);
        target.sort(new Comparator<NameMappingUnit>() {
            @Override
            public int compare(NameMappingUnit o1, NameMappingUnit o2) {
                Comparator<String> tComparator = Comparator.naturalOrder();
                return tComparator.compare(o1.toString(), o2.toString());
            }
        });
        return target;
    }


    public ArrayList<JsonRecipeResult> allJsonRecipeResults(String input){
        HashSet<JsonRecipeResult> re = new HashSet<>();
        for (JsonRecipe recipe : recipes) {
            if (input.equals(recipe.input.toStr())){
                re.add(recipe.result);
            }
        }
        return new ArrayList<>(re);
    }

    //全部使用注册名
    public JsonRecipe recipe(String input, String result){
        for (JsonRecipe recipe : recipes) {
            if (recipe.input.toStr().equals(input) && recipe.result.item.equals(result)){
                return recipe;
            }
        }
        return null;
    }
}
