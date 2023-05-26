package com.github.thelittlestone;


import com.github.thelittlestone.dataController.FileLoader;
import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        System.out.println(WorldDataManager.getjsonWorldRecipes("aaa"));
//        WorldDataManager.createWorldFromRecipe("aaa" );
    }
}
