package com.github.thelittlestone.logic.components;

/**
 * Created by theLittleStone on 2023/5/14.
 */

/*
* mainLogic内部使用的类
* */
public class RequiredAction {
    public ActionEnum action1Last;
    public ActionEnum action2Last;
    public ActionEnum action3Last;


    public RequiredAction(ActionEnum action1Last, ActionEnum action2Last, ActionEnum action3Last) {
        this.action1Last = action1Last;
        this.action2Last = action2Last;
        this.action3Last = action3Last;
    }
    public RequiredAction(RequiredAction re){
        this(re.action1Last, re.action2Last, re.action3Last);
    }
    public RequiredAction(ActionEnum action, int index){
        this(null, null, null);
        switch (index){
            case 1 -> action1Last = action;
            case 2 -> action2Last = action;
            case 3 -> action3Last = action;
        }
    }
    public RequiredAction(){
        this(null, null, null);
    }
    //复制一个类
    public RequiredAction newAndReplaceOne(ActionEnum actionLast, int index){
        RequiredAction newRequiredAction = new RequiredAction(this);
        newRequiredAction.replaceOne(actionLast, index);
        return newRequiredAction;
    }
    //替换本类
    public void replaceOne(ActionEnum actionLast, int index){
        switch (index){
            case 1 -> action1Last = actionLast;
            case 2 -> action2Last = actionLast;
            case 3 -> action3Last = actionLast;
        }
    }

    //倒序生成一个list, 直接插入在最后即可
    public ActionCombination toCombination(){
        //默认不存在AXB这样的结构, 只有ABC或者ABX这种(X表示不固定顺序, 其他表示固定顺序)
        ActionCombination combination = new ActionCombination();
        if (null != action3Last) {
            combination.add(action3Last);
        }if (null != action2Last) {
            combination.add(action2Last);
        }if (null != action1Last) {
            combination.add(action1Last);
        }
        return combination;
    }

    public int getDistance(){
        int target = 0;
        if (null != action3Last){
            target += action3Last.value;
        }if (null != action2Last) {
            target += action2Last.value;
        }if (null != action1Last) {
            target += action1Last.value;
        }
        return target;
    }

    @Override
    public String toString() {
        return "RequiredAction{" +
                "action1Last=" + action1Last +
                ", action2Last=" + action2Last +
                ", action3Last=" + action3Last +
                '}';
    }
}
