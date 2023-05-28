package com.github.thelittlestone.util;

/**
 * Created by theLittleStone on 2023/5/28.
 */
public class ForbiddenChars {
    static char[] forbiddenChars = {' ', '/', '\\', '`', ':', '*', '?', '\'', '\"', '<', '>', '|', '_',
            '%', '^', '[', ']', '{', '}', '.'};
    public static boolean hasForbiddenChars(String s ){
        for (char forbiddenChar : forbiddenChars) {
            if (s.contains(String.valueOf(forbiddenChar))){
                return true;
            }
        }
        return false;
    }
}
