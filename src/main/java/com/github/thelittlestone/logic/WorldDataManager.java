package com.github.thelittlestone.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thelittlestone.dataController.FileLoader;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class WorldDataManager {
    public static ArrayList<String> fileNames;
    static {
        fileNames = FileLoader.getOutPackageFileNamesContains("^((?i)w)orld_(.*).json");
    }

    public JsonWorldRecipes getjsonWorldRecipes(String worldName){
        for (String filename : fileNames) {
            try {
                String fileContent = FileLoader.getFileContent(filename, false);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonWorldRecipes target = objectMapper.readValue(fileContent, JsonWorldRecipes.class);
                if (target.worldName.equals(worldName)){
                    return target;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        return null;
    }

    public void writeOneWorldRecipes(String fileName, JsonWorldRecipes worldRecipes) throws IOException {
        String content = worldRecipes.generateJsonText();
        FileLoader.writeToFile(fileName, content);
    }
}
