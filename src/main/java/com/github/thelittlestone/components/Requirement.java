package com.github.thelittlestone.components;

/**
 * Created by theLittleStone on 2023/5/13.
 */
public class Requirement {
    public ActionEnum actionKind;
    public RequirementEnum requirementKind;

    public Requirement(ActionEnum actionKind, RequirementEnum requirementKind) {
        this.actionKind = actionKind;
        this.requirementKind = requirementKind;
    }

}
