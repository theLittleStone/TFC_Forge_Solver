package com.github.thelittlestone.translate;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by theLittleStone on 2023/6/8.
 */
public class NameMappingTable extends ArrayList<NameMappingUnit> {

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
