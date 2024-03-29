package com.github.thelittlestone.translate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by theLittleStone on 2023/6/8.
 */
public class NameMappingTable {
    public String version;
    public HashSet<NameMappingUnit> mapUnits;


    public void merge(NameMappingTable nmt){
        this.mapUnits.addAll(nmt.mapUnits);
    }

    public String generateJsonText() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(this);
    }
}
