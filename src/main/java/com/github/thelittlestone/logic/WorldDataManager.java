package com.github.thelittlestone.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thelittlestone.MainApplication;
import com.github.thelittlestone.util.FileLoader;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class WorldDataManager {
    public static HashMap<String, String> nameMap = new HashMap<>(); //世界名, 文件名
    public static HashMap<String, JsonWorldRecipes> worldMap = new HashMap<>(); //世界名, 世界类

    //显示当前页面展示的是哪个世界, 很重要
    public static JsonWorldRecipes currentWorld;

    //初始化, 将所有的world文件加载到程序中,
    static {
        ArrayList<String> fileNames = FileLoader.getOutPackageFileNamesContains("^((?i)w)orld_(.*).json");
        if (fileNames != null && !fileNames.isEmpty()) {
            for (String fileName : fileNames) {
                try {
                    String fileContent = FileLoader.getFileContent(fileName, false);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonWorldRecipes jwr = objectMapper.readValue(fileContent, JsonWorldRecipes.class);
                    //添加配方版本更新功能
                    if (!MainApplication.VERSION.equals(jwr.version)){
                        jwr.merge(new JsonWorldRecipes("raw", RecipeLoader.rawRecipes));
                        jwr.version = MainApplication.VERSION;
                        FileLoader.deleteFile(fileName);
                        FileLoader.writeToFile(fileName, jwr.generateJsonText());
                    }
                    nameMap.put(jwr.worldName, fileName);
                    worldMap.put(jwr.worldName, jwr);
                } catch (IOException e) {
                    continue;
                }
            }
            currentWorld = getJsonWorldRecipes(getAllWorldName().get(0));

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
            fileName = defaultRecipeFileName(worldRecipes.worldName);
        }
        String content = worldRecipes.generateJsonText();
        FileLoader.writeToFile(fileName, content);
        nameMap.put(worldRecipes.worldName, fileName);
        worldMap.put(worldRecipes.worldName, worldRecipes);
    }
    //把一个世界配置写到文件中, 同时也会更新本地的世界库, 如果保存有文件名, 使用原文件名, 否则使用默认文件名
    public static void updateWorld(JsonWorldRecipes worldRecipes) throws IOException {
        if (worldRecipes == null){
            return;
        }
        String filename = nameMap.get(worldRecipes.worldName);
        if (filename == null) {
            updateWorld(worldRecipes, defaultRecipeFileName(worldRecipes.worldName));
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
        JsonWorldRecipes jwr = new JsonWorldRecipes(worldName, RecipeLoader.rawRecipes);
        updateWorld(jwr, fileName);
        return jwr;
    }
    public static JsonWorldRecipes createWorldFromRecipe(String worldName) throws IOException {
        return createWorldFromRecipe(worldName, defaultRecipeFileName(worldName));
    }

    //寻找索引中的世界配置文件名并删除之
    public static void deleteWorldRecipeFile(String worldName) throws IOException {

        String fileName = nameMap.get(worldName);
        if (fileName != null){
            FileLoader.deleteFile(fileName);
        }else {
            throw new IOException("找不到世界"+ worldName +"对应的文件");
        }

        nameMap.remove(worldName);
        worldMap.remove(worldName);
    }

    //根据世界名重命名世界配置文件, 这会删除旧文件, 然后创建新文件
    public static void renameWorldRecipeFile(String worldName, String newFileName) throws IOException {
        deleteWorldRecipeFile(worldName);
        updateWorld(worldMap.get(worldName), newFileName);
    }
    public static void renameWorldRecipeFile(String worldName, JsonWorldRecipes recipes, String newFileName) throws IOException {
        deleteWorldRecipeFile(worldName);
        updateWorld(recipes, newFileName);
    }

    public static String defaultRecipeFileName(String worldName){
        return "world_" + worldName + ".json";
    }

}

