package com.github.thelittlestone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.thelittlestone.util.FileLoader;

import java.io.IOException;

/**
 * Created by theLittleStone on 2023/5/27.
 */
public class ConfigLoader {
    public static final Config config;

    static {
        Config config1;
        ObjectMapper objectMapper = new ObjectMapper();
        String content;
        try {
            content = FileLoader.getFileContent("Config.json", false);
            config1 = objectMapper.readValue(content, Config.class);
        } catch (IOException e) {
            try {
                content = FileLoader.getFileContent("Config.json", true);
                config1 = objectMapper.readValue(content, Config.class);
            } catch (IOException ex) {
                System.out.println("无法解析Config文件");
                throw new RuntimeException(ex);
            }
        }
        config = config1;
    }

    public static void saveConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String content = objectMapper.writeValueAsString(config);
        FileLoader.writeToFile("Config.json", content);
    }

}
