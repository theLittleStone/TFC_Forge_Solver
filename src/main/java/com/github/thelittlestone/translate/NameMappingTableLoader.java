package com.github.thelittlestone.translate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thelittlestone.MainApplication;
import com.github.thelittlestone.translate.NameMappingTable;
import com.github.thelittlestone.util.FileLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by theLittleStone on 2023/6/8.
 */
public class NameMappingTableLoader {
    private static final NameMappingTable nameMappingTable;

    static {
        NameMappingTable nmp;
        ObjectMapper objectMapper = new ObjectMapper();
        String content;
        try {
            content = FileLoader.getFileContent("NameMap.json", false);
        } catch (IOException e) {
            try {
                content = FileLoader.getFileContent("NameMap.json", true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //序列化
        try {
            nmp = objectMapper.readValue(content, NameMappingTable.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //判断版本
        if (!MainApplication.VERSION.equals(nmp.version)){
            NameMappingTable innerMappingTable;
            //拉取内建新版本
            try {
                content = FileLoader.getFileContent("NameMap.json", true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                innerMappingTable = objectMapper.readValue(content, NameMappingTable.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //合并
            nmp.merge(innerMappingTable);
            nmp.version = MainApplication.VERSION;
            //重新存储
            try {
                FileLoader.deleteFile("NameMap.json");
                FileLoader.writeToFile("NameMap.json", nmp.generateJsonText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        nameMappingTable = nmp;

    }

    public static NameMappingUnit get(String original){
        for (NameMappingUnit nameMappingUnit : nameMappingTable.mapUnits) {
            if (Objects.equals(nameMappingUnit.origName, original)){
                return nameMappingUnit;
            }
        }
        //如果没有翻译, 返回一个无翻译的单元
        NameMappingUnit nameMappingUnit = new NameMappingUnit();
        nameMappingUnit.origName = original;
        nameMappingUnit.transName = original;
        return nameMappingUnit;
    }
}
