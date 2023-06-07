package com.github.thelittlestone.logic.components;

import com.github.thelittlestone.exception.RequirementConvertException;

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

    public static ActionEnum toThis(String content) throws RequirementConvertException {
        switch (content) {
            case "hit", "hit_" -> {
                return ActionEnum.LightHit;
            }
            case "draw", "draw_" -> {
                return ActionEnum.Draw;
            }
            case "punch", "punch_" -> {
                return ActionEnum.Punch;
            }
            case "bend", "bend_" -> {
                return ActionEnum.Bend;
            }
            case "upset", "upset_" -> {
                return ActionEnum.Upset;
            }
            case "shrink", "shrink_" -> {
                return ActionEnum.Shrink;
            }
        }
        throw new RequirementConvertException("转换actionEnum时出错 转换内容: " + content);
    }
    public String zh(){
        switch (this){
            case LightHit -> {
                return "轻击";
            }
            case MediumHit -> {
                return "击打";
            }
            case HeavyHit -> {
                return "重击";
            }
            case Draw -> {
                return "牵拉";
            }
            case Punch -> {
                return "冲压";
            }
            case Bend -> {
                return "弯曲";
            }
            case Upset -> {
                return "镦锻";
            }
            case Shrink -> {
                return "收缩";
            }
        }
        return null;
    }
}
