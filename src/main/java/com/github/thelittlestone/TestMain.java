package com.github.thelittlestone;


import com.github.thelittlestone.components.*;
import com.github.thelittlestone.logic.MainLogic;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class TestMain {
    public static void main(String[] args) {
        ActionCombination result = MainLogic.solve(65, new RequirementCombination(
                new Requirement(ActionEnum.Upset, RequirementEnum.First_Last),
                new Requirement(ActionEnum.LightHit, RequirementEnum.Second_Last),
                null));

        System.out.println(result);
    }
}
