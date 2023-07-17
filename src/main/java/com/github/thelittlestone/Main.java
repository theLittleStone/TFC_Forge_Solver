package com.github.thelittlestone;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.translate.NameMappingTable;
import com.github.thelittlestone.translate.NameMappingUnit;
import com.github.thelittlestone.util.FileLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by theLittleStone on 2023/5/22.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HashSet<Test> tests = new HashSet<>();
        tests.add(new Test("aaa", 1));
        tests.add(new Test("bbb", 1));
        tests.add(new Test("ccc", 1));
        for (Test test : tests) {
            System.out.println(test);
        }
    }

    public static void generateNameMap() throws IOException {
        WorldDataManager.currentWorld = WorldDataManager.worldMap.get(WorldDataManager.getAllWorldName().get(0));
        HashSet<String> stringHashSet2 = new HashSet<>();
        HashSet<String> stringHashSet1 = new HashSet<>();
        for (NameMappingUnit allInput : WorldDataManager.currentWorld.allInputs()) {
            stringHashSet1.add(allInput.origName);
        }
        for (String s : stringHashSet1) {
            ArrayList<NameMappingUnit> nameMappingUnits = WorldDataManager.currentWorld.allResults(s);
            for (NameMappingUnit nameMappingUnit : nameMappingUnits) {
                stringHashSet2.add(nameMappingUnit.origName);
            }
        }
        stringHashSet1.addAll(stringHashSet2);
        ArrayList<String> stringArrayList = new ArrayList<>(stringHashSet1);
        stringArrayList.sort(Comparator.naturalOrder());

        NameMappingTable nameMappingTable = new NameMappingTable();
        for (String s : stringArrayList) {
            NameMappingUnit nameMappingUnit = new NameMappingUnit();
            nameMappingUnit.origName = s;
            nameMappingUnit.transName = "";
            nameMappingTable.mapUnits.add(nameMappingUnit);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String content = objectMapper.writeValueAsString(nameMappingTable);
        FileLoader.writeToFile("NameMap11.json", content);
    }
}

class Test{
    String a;
    int b;

    public Test(String a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        return b == test.b;
    }

    @Override
    public int hashCode() {
        return b;
    }

    @Override
    public String toString() {
        return "Test{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}