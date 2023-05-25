package com.github.thelittlestone.dataController;

import com.github.thelittlestone.MainApplication;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class ConfigLoader {
    public static final String[] InPackageConfigFileArray = {"Recipes.json", "Worlds.json"};
//    public static String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()
//            .replace("TFC_Forge_Solver.jar", "").replaceFirst("/", "");
    public static String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation()
        .getPath().replaceFirst("/", "");
    //包内的资源路径
    public static String ResourcePath = "/com/github/thelittlestone/";
    //包外的配置文件存放文件夹
    public static String ConfigPath = "TFC_Forge_solver_config/";

    public static boolean InitResult = false;
    static {
        try {
            init();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() throws URISyntaxException, IOException {
        URI uri = new URI(PackagePath + ConfigPath);
        File f = new File(uri.getPath());
        if (!f.exists() || !f.isDirectory()){
            if (!f.mkdirs()) {
                throw new IOException("创建config文件夹失败");
            }
        }
        //如果config中不存在配置文件, 则向其中释放默认配置文件
        try {
            for (String s : InPackageConfigFileArray) {
                URI u = new URI(PackagePath + ConfigPath + s);
                if (!(new File(u.getPath()).exists())){
                    writeToFile(ResourcePath + s, u.getPath());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeToFile(String sourcePath, String targetPath) throws IOException {
        InputStream ins = MainApplication.class.getResourceAsStream(sourcePath);
        if (null != ins){
            FileOutputStream fos = new FileOutputStream(targetPath);
            int readByte;
            byte[] bytes = new byte[1024];
            while ((readByte = ins.read(bytes)) != -1){
                fos.write(bytes, 0, readByte);
            }
            fos.close();
            ins.close();
        }else {
            throw new IOException("复制文件失败");
        }
    }

    public static JSONObject getConfigJsonObject(String fileName, boolean readDefaultConfigFile) throws IOException {
        if (!InitResult && !readDefaultConfigFile){
            throw new IOException("config文件读取失败");
        }
        if (readDefaultConfigFile){
            //读取包内配置
            InputStream is = MainApplication.class.getResourceAsStream(ResourcePath + fileName);
            assert is != null;
            String content = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
            return JSON.parseObject(content, JSONObject.class);
        }
        //包外配置
        URI u;
        try {
            u = new URI(PackagePath + ConfigPath + fileName);
        }catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("无法读取包外配置文件");
        }
        Scanner scanner = new Scanner(Paths.get(u.getPath()));
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }
        return JSON.parseObject(content.toString(), JSONObject.class);
    }

}
