package com.github.thelittlestone.util;

import com.github.thelittlestone.gui.ComponentBoard;

public class StageLocation {
    public static double getStageX(){
        return ComponentBoard.mainStage.getX();
    }
    public static double getStageY(){
        return ComponentBoard.mainStage.getY();
    }
    public static double getStageWidth(){
        return ComponentBoard.mainStage.getWidth();
    }
    public static double getStageHeight(){
        return ComponentBoard.mainStage.getHeight();
    }

    public static double getStageCenterX(){
        return getStageX() + getStageWidth() / 2;
    }
    public static double getStageCenterY() {
        return getStageY() + getStageHeight() /2;
    }
}
