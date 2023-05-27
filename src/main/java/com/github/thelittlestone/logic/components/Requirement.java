package com.github.thelittlestone.logic.components;

import com.github.thelittlestone.exception.RequirementConvertException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Requirement(String content) throws RequirementConvertException {
        int i = content.indexOf("_");
        this.actionKind = ActionEnum.toThis(content.substring(0, i));
        this.requirementKind = RequirementEnum.toThis(content.substring(i));
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "actionKind=" + actionKind +
                ", requirementKind=" + requirementKind +
                '}';
    }
}
