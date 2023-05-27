package com.github.thelittlestone.logic.components;

import com.github.thelittlestone.exception.RequirementConvertException;

/**
 * Created by theLittleStone on 2023/5/13.
 */
public enum RequirementEnum {
    First_Last, Second_Last, Third_Last,
    Not_Last,
    Any;

    public static RequirementEnum toThis(String content) throws RequirementConvertException {
        switch (content){
            case "_not_last", "not_last" ->{
                return RequirementEnum.Not_Last;
            }
            case "_any", "any" ->{
                return RequirementEnum.Any;
            }
            case "_second_last", "second_last" ->{
                return RequirementEnum.Second_Last;
            }
            case "_third_last", "third_last" ->{
                return RequirementEnum.Third_Last;
            }
            case "_last", "last" ->{
                return RequirementEnum.First_Last;
            }
        }
        throw new RequirementConvertException("转换requirement出错");
    }
}
