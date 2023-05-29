package com.github.thelittlestone.logic;

import com.github.thelittlestone.util.FileLoader;

import java.io.IOException;

/**
 * Created by theLittleStone on 2023/5/26.
 */
public class RecipeLoader {
    public static String recipeContent;
    static {
        try {
            recipeContent = FileLoader.getFileContent("Recipes.json", false);;
        } catch (IOException e) {
            try {
                recipeContent = FileLoader.getFileContent("Recipes.json", true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
