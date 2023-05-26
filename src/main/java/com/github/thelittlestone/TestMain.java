package com.github.thelittlestone;


import com.github.thelittlestone.dataController.FileLoader;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class TestMain {
    public static void main(String[] args) {
        ArrayList<String> strings = FileLoader.getOutPackageFileNamesContains("^((?i)w)orld_(.*).json");
        for (String string : strings) {
            System.out.println(string);
        }

    }
}
