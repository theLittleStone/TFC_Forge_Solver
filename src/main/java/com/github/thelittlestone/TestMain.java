package com.github.thelittlestone;


import com.github.thelittlestone.logic.WorldDataManager;

import java.io.IOException;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
//        System.out.println(WorldDataManager.getJsonWorldRecipes("aaa"));
        WorldDataManager.createWorldFromRecipe("1" );
    }
}
