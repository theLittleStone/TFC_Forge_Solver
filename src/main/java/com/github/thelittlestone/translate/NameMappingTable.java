package com.github.thelittlestone.translate;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by theLittleStone on 2023/6/8.
 */
public class NameMappingTable extends ArrayList<NameMappingUnit> {

    public String translate(String original){
        for (NameMappingUnit unit : this) {
            if (unit.origName.equals(original)){
                return unit.transName;
            }
        }
        return original;
    }

    public String original(String translate){
        for (NameMappingUnit unit : this) {
            if (unit.transName.equals(translate)){
                return unit.origName;
            }
        }
        return translate;
    }

    //检查重复元素
    public boolean check(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (NameMappingUnit unit : this) {
            stringArrayList.add(unit.origName);
        }
        HashSet<String> stringHashSet = new HashSet<>(stringArrayList);
        return stringHashSet.size() == stringArrayList.size();
    }
}
