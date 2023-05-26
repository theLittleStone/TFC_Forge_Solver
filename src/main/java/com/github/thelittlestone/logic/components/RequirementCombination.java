package com.github.thelittlestone.logic.components;

/**
 * Created by theLittleStone on 2023/5/13.
 */

/*
* 用于外部传入的类
* 需要注意的是这个类允许内容为null, 在使用的时候需要加以判断
* */
public class RequirementCombination {
    public Requirement[] requirementArray = new Requirement[3];

    public RequirementCombination(Requirement r1, Requirement r2, Requirement r3) {
        this.requirementArray[0] = r1;
        this.requirementArray[1] = r2;
        this.requirementArray[2] = r3;
    }
    public RequirementCombination(Requirement r1, Requirement r2){
        this(r1, r2, null);
    }
    public RequirementCombination(Requirement r){
        this(r, null, null);
    }


}
