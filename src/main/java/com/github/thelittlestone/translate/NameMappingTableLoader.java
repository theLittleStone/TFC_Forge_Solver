package com.github.thelittlestone.translate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            nmp = objectMapper.readValue(content, NameMappingTable.class);
        } catch (IOException e) {
            try {
                content = FileLoader.getFileContent("NameMap.json", true);
                nmp = objectMapper.readValue(content, NameMappingTable.class);
            } catch (IOException ex) {
                System.out.println("无法解析Config文件");
                throw new RuntimeException(ex);
            }
        }
        nameMappingTable = nmp;
        if (!nameMappingTable.check()) {
            throw new RuntimeException("翻译文件出现重复");
        }
    }

    public static NameMappingUnit get(String original){
        for (NameMappingUnit nameMappingUnit : nameMappingTable) {
            if (Objects.equals(nameMappingUnit.origName, original)){
                return nameMappingUnit;
            }
        }
        return null;
    }
}
