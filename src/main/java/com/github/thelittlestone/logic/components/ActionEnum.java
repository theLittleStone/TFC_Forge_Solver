package com.github.thelittlestone.logic.components;

/**
 * Created by theLittleStone on 2023/5/13.
 */
public enum ActionEnum {
    /**
     * 前四个, 红色, 左移*/
    LightHit(-3), MediumHit(-6), HeavyHit(-9), Draw(-15),
    /*
     * 后四个, 绿色, 右移*/
    Punch(2), Bend(7), Upset(13), Shrink(16);

    public final int value;
    ActionEnum(int value) {
        this.value = value;
    }

}
