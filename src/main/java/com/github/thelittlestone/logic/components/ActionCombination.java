package com.github.thelittlestone.logic.components;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by theLittleStone on 2023/5/13.
 */
public class ActionCombination extends ArrayList<ActionEnum> {
    public ActionCombination(ActionCombination oldCombination){
        super();
        this.addAll(oldCombination);
    }
    public ActionCombination(ActionEnum... actionEnums){
        super();
        this.addAll(Arrays.asList(actionEnums));
    }

    public int getTotalValue(){
        int target = 0;
        for (ActionEnum actionEnum : this) {
            target += actionEnum.value;
        }
        return target;
    }

    public boolean matches(int target){
        return target == getTotalValue();
    }

    public int getAbsDistance(int target){
        return Math.abs(target - getTotalValue());
    }

    public ActionCombination newAndAddOne(ActionEnum actionEnum){
        ActionCombination target = new ActionCombination(this);
        target.add(actionEnum);
        return target;
    }


    public void sortByValue(){
        sort((a1, a2) -> a2.value - a1.value);
    }

    public void sortByAbsValue(){
        sort((a1, a2) -> Math.abs(a2.value) - Math.abs(a1.value));
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActionEnum actionEnum : this) {
            stringBuilder.append(actionEnum.toString()).append(",  ");
        }
        return stringBuilder.toString();
    }
}
