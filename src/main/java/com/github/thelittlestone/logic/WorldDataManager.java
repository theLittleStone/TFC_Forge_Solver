package com.github.thelittlestone.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thelittlestone.dataController.FileLoader;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class WorldDataManager {
    public static boolean hasNoWorld = true;
    public static HashMap<String, String> nameMap = new HashMap<>(); //世界名, 文件名
    public static HashMap<String, JsonWorldRecipes> worldMap = new HashMap<>(); //世界名, 世界类

    //初始化, 将所有的world文件加载到程序中,
    static {
        ArrayList<String> fileNames = FileLoader.getOutPackageFileNamesContains("^((?i)w)orld_(.*).json");
        if (fileNames != null) {
            hasNoWorld = false;
            for (String fileName : fileNames) {
                try {
                    String fileContent = FileLoader.getFileContent(fileName, false);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonWorldRecipes jwr = objectMapper.readValue(fileContent, JsonWorldRecipes.class);
                    nameMap.put(jwr.worldName, fileName);
                    worldMap.put(jwr.worldName, jwr);
                } catch (IOException e) {
                    continue;
                }
            }
        }
    }

    //根据世界名读取世界配置
    public static JsonWorldRecipes getJsonWorldRecipes(String worldName){
        return worldMap.get(worldName);
    }

    //获取所有的世界名
    public static ArrayList<String> getAllWorldName(){
        return new ArrayList<>(worldMap.keySet());
    }

    //把一个世界配置写到文件中, 同时也会更新本地的世界库
    public static void updateWorld(JsonWorldRecipes worldRecipes, String fileName) throws IOException {
        if(fileName == null){
            fileName = "world_" + worldRecipes.worldName + ".json";
        }
        String content = worldRecipes.generateJsonText();
        FileLoader.writeToFile(fileName, content);
        nameMap.put(worldRecipes.worldName, fileName);
        worldMap.put(worldRecipes.worldName, worldRecipes);
    }
    //把一个世界配置写到文件中, 同时也会更新本地的世界库, 如果保存有文件名, 使用原文件名, 否则使用默认文件名
    public static void updateWorld(JsonWorldRecipes worldRecipes) throws IOException {
        String filename = nameMap.get(worldRecipes.worldName);
        if (filename == null) {
            updateWorld(worldRecipes, "world_" + worldRecipes.worldName + ".json");
        }else {
            updateWorld(worldRecipes, filename);
        }
    }
    //将所有程序内的世界配置全部重新写入文件中
    public static void updateAllWorld() throws IOException {
        ArrayList<String> allWorldName = getAllWorldName();
        for (String worldName : allWorldName) {
            updateWorld(worldMap.get(worldName), nameMap.get(worldName));
        }
    }

    public static JsonWorldRecipes createWorldFromRecipe(String worldName, String fileName) throws IOException {
        JsonWorldRecipes jwr = new JsonWorldRecipes(worldName, RecipeLoader.recipeContent);
        updateWorld(jwr, fileName);
        return jwr;
    }
    public static JsonWorldRecipes createWorldFromRecipe(String worldName) throws IOException {
        return createWorldFromRecipe(worldName, "world_" + worldName + ".json");
    }
}
