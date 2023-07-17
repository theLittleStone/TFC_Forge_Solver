package com.github.thelittlestone.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thelittlestone.MainApplication;
import com.github.thelittlestone.logic.json.JsonRawWorldRecipes;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;
import com.github.thelittlestone.util.FileLoader;

import java.io.IOException;

/**
 * Created by theLittleStone on 2023/5/26.
 */
public class RecipeLoader {

    public static final JsonRawWorldRecipes rawRecipes;

    static {
        String recipeContent;
        try {
            recipeContent = FileLoader.getFileContent("Recipes.json", false);;
        } catch (IOException e) {
            try {
                recipeContent = FileLoader.getFileContent("Recipes.json", true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //在初始化阶段就逆序列化
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             rawRecipes = objectMapper.readValue(recipeContent, JsonRawWorldRecipes.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //判断版本
        if (!MainApplication.VERSION.equals(rawRecipes.version)){
            //拉取内建配方表
            JsonRawWorldRecipes innerRawRecipes;
            try {
                recipeContent = FileLoader.getFileContent("Recipes.json", true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                innerRawRecipes = objectMapper.readValue(recipeContent, JsonRawWorldRecipes.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //合并
            rawRecipes.merge(innerRawRecipes);
            rawRecipes.version = MainApplication.VERSION;
            //重新存储
            try {
                FileLoader.deleteFile("Recipes.json");
                FileLoader.writeToFile("Recipes.json", rawRecipes.generateJsonText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
