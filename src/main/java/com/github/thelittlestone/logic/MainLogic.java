package com.github.thelittlestone.logic;

import com.github.thelittlestone.logic.components.*;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by theLittleStone on 2023/5/13.
 */
public class MainLogic {
    public static ActionCombination solve(int target, RequirementCombination requirementCombination){
        ArrayList<ActionCombination> addingOnHeads = new ArrayList<>();
        ArrayList<RequiredAction> addingOnLasts = new ArrayList<>();

        for (Requirement requirement : requirementCombination.requirementArray) {
            if (null != requirement) {
                switch (requirement.requirementKind) {
                    case First_Last -> addingOnLasts = generateAddingOnLast(requirement, addingOnLasts, 1);
                    case Second_Last -> addingOnLasts = generateAddingOnLast(requirement, addingOnLasts, 2);
                    case Third_Last -> addingOnLasts = generateAddingOnLast(requirement, addingOnLasts, 3);
                    case Not_Last, Any -> addingOnHeads = generateAddingOnHead(requirement, addingOnHeads);
                }
            }
        }
        if (addingOnHeads.isEmpty()){
            addingOnHeads.add(new ActionCombination());
        }if (addingOnLasts.isEmpty()){
            addingOnLasts.add(new RequiredAction());
        }

        return multiSolve(target, addingOnHeads, addingOnLasts);
    }

    //要求的位于任意位置的操作, 先生成并作为初始操作
    //生成的是一组组合, 对每个组合进行求解并最终找出一个最优解
    public static ArrayList<ActionCombination> generateAddingOnHead(Requirement requirement, ArrayList<ActionCombination> originalCombinations){
        if (requirement.actionKind == ActionEnum.LightHit ||
                requirement.actionKind == ActionEnum.MediumHit ||
                requirement.actionKind == ActionEnum.HeavyHit){
            if (originalCombinations.isEmpty()){
                originalCombinations.add(new ActionCombination(ActionEnum.LightHit));
                originalCombinations.add(new ActionCombination(ActionEnum.MediumHit));
                originalCombinations.add(new ActionCombination(ActionEnum.HeavyHit));
            }else {
                ArrayList<ActionCombination> newAddingOnHead = new ArrayList<>();
                for (ActionCombination actionCombination : originalCombinations) {
                    newAddingOnHead.add(actionCombination.newAndAddOne(ActionEnum.LightHit));
                    newAddingOnHead.add(actionCombination.newAndAddOne(ActionEnum.MediumHit));
                    newAddingOnHead.add(actionCombination.newAndAddOne(ActionEnum.HeavyHit));
                }
                originalCombinations = newAddingOnHead;
            }
        }else {
            if (originalCombinations.isEmpty()){
                originalCombinations.add(new ActionCombination(requirement.actionKind));
            }else {
                for (ActionCombination actionCombination : originalCombinations) {
                    actionCombination.add(requirement.actionKind);
                }
            }
        }
        return originalCombinations;
    }

    //要求的位于最后的操作, 先生成, 最后再添加上去
    //生成的是一组组合, 对每个组合进行求解并最终找出一个最优解
    public static ArrayList<RequiredAction> generateAddingOnLast(Requirement requirement, ArrayList<RequiredAction> originalCombinations, int index){
        if (requirement.actionKind == ActionEnum.LightHit ||
                requirement.actionKind == ActionEnum.MediumHit ||
                requirement.actionKind == ActionEnum.HeavyHit){
            if(originalCombinations.isEmpty()){
                originalCombinations.add(new RequiredAction(ActionEnum.LightHit, index));
                originalCombinations.add(new RequiredAction(ActionEnum.MediumHit, index));
                originalCombinations.add(new RequiredAction(ActionEnum.HeavyHit, index));
            }else {
                ArrayList<RequiredAction> newAddingOnLast = new ArrayList<>();
                for (RequiredAction requiredAction : originalCombinations) {
                    newAddingOnLast.add(requiredAction.newAndReplaceOne(ActionEnum.LightHit, index));
                    newAddingOnLast.add(requiredAction.newAndReplaceOne(ActionEnum.MediumHit, index));
                    newAddingOnLast.add(requiredAction.newAndReplaceOne(ActionEnum.HeavyHit, index));
                }
                originalCombinations = newAddingOnLast;
            }
        }else {
            if (originalCombinations.isEmpty()){
                originalCombinations.add(new RequiredAction(requirement.actionKind, index));
            }else {
                for (RequiredAction requiredAction : originalCombinations) {
                    requiredAction.replaceOne(requirement.actionKind, index);
                }
            }
        }
        return originalCombinations;
    }

    //求解一个固定值
    public static ActionCombination basicSolve(int target, ActionCombination originCombination){
        ArrayList<ActionCombination> particlePool = new ArrayList<>();
        //创建两层粒子池, 共64个粒子
        for (ActionEnum action : ActionEnum.values()) {
            ActionCombination firstFloor = new ActionCombination(originCombination);
            firstFloor.add(action);
            for (ActionEnum a : ActionEnum.values()) {
                ActionCombination secondFloor = new ActionCombination(firstFloor);
                secondFloor.add(a);
                particlePool.add(secondFloor);
            }
        }

        while (true) {
            //重采样, 新池里面应该有512个元素
            ArrayList<ActionCombination> largerParticlePool = new ArrayList<>();
            for (ActionCombination combination : particlePool) {
                for (ActionEnum action : ActionEnum.values()) {
                    largerParticlePool.add(combination.newAndAddOne(action));
                }
            }
            //按距离排序
            largerParticlePool.sort(Comparator.comparingInt(o -> o.getAbsDistance(target)));
            //滤波
            particlePool = new ArrayList<>(largerParticlePool.subList(0, 63));
            //校验
            for (ActionCombination combination : particlePool) {
                if (combination.matches(target)){
                    return combination;
                }
            }
        }
    }

    public static ActionCombination singleSolve(int target, ActionCombination addingOnHead, RequiredAction addingOnLast){
        int targetOffset = addingOnLast.getDistance();
        ActionCombination solve = basicSolve(target - targetOffset, addingOnHead);
        solve.sortByValue();  //先排序
        solve.addAll(addingOnLast.toCombination());
        return solve;
    }

    public static ActionCombination multiSolve(int target, ArrayList<ActionCombination> addingOnHeads,
                                                   ArrayList<RequiredAction> addingOnLasts){
        ArrayList<ActionCombination> results = new ArrayList<>();
        if (addingOnHeads.isEmpty()){
            addingOnHeads.add(new ActionCombination());
        }
        if (addingOnLasts.isEmpty()) {
            addingOnLasts.add(new RequiredAction());
        }

        for (ActionCombination addingOnHead : addingOnHeads) {
            for (RequiredAction addingOnLast : addingOnLasts) {
                results.add(singleSolve(target, addingOnHead, addingOnLast));
            }
        }

        results.sort(Comparator.comparingInt(ArrayList::size));
        return results.get(0);
    }
}
