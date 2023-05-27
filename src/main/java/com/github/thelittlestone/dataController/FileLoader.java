package com.github.thelittlestone.dataController;

import com.github.thelittlestone.MainApplication;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class FileLoader {
    public static final String[] InPackageFileArray = {"Recipes.json", "World_1.json", "Config.json"};
//    public static String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()
//            .replace("TFC_Forge_Solver.jar", "").replaceFirst("/", "");
    //包所在的路径
    public static String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation()
        .getPath().replaceFirst("/", "");
    //包内的资源路径
    public static String InPackageResourcePath = "/com/github/thelittlestone/";
    //包外的配置文件存放文件夹
    public static String OutPackageResourcePath = "TFC_Forge_solver_config/";

    public static boolean InitResult = false;
    static {
        try {
            init();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() throws URISyntaxException, IOException {
        URI targetURI = new URI(PackagePath + OutPackageResourcePath);
        File f = new File(targetURI.getPath());
        //创建外部配置文件夹(如果不存在)
        if (!f.exists() || !f.isDirectory()){
            if (!f.mkdirs()) {
                throw new IOException("创建config文件夹失败");
            }
        }
        //如果config中不存在配置文件, 则向其中释放默认配置文件
        try {
            for (String s : InPackageFileArray) {
                URI u = new URI(PackagePath + OutPackageResourcePath + s);
                if (!(new File(u.getPath()).exists())){
                    copyFile(InPackageResourcePath + s, u.getPath());
                }
            }
            InitResult = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void copyFile(String sourcePath, String targetPath) throws IOException {
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

    public static String getFileContent(String fileName, boolean readInPackageConfigFile) throws IOException {
        if (!InitResult && !readInPackageConfigFile){
            throw new IOException("无法读取指定文件" + fileName);
        }
        if (readInPackageConfigFile){
            //读取包内配置
            InputStream is = MainApplication.class.getResourceAsStream(InPackageResourcePath + fileName);
            assert is != null;
            return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
        }
        //包外配置
        URI u;
        try {
            u = new URI(PackagePath + OutPackageResourcePath + fileName);
        }catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("无法读取包外配置文件");
        }
        Scanner scanner = new Scanner(Paths.get(u.getPath()));
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }
        return content.toString();
    }

    public static void writeToFile(String fileName, String content) throws IOException {
        if (!InitResult){
            throw new IOException("初始化配置文件夹失败");
        }
        String targetPath = PackagePath + OutPackageResourcePath + fileName;
        try (FileWriter fileWriter = new FileWriter(targetPath, false)){
            fileWriter.write(content);
        }
    }

    public static ArrayList<String> getOutPackageFileNamesContains(String regex){
        ArrayList<String> target = new ArrayList<>();
        File file = new File(PackagePath + OutPackageResourcePath);
        String[] fileNameList = file.list();
        if (fileNameList == null) {
            return null;
        }
        for (String fileName : fileNameList) {
            File f = new File(PackagePath + OutPackageResourcePath + fileName);
            if (f.isDirectory()){
                continue;
            }if (fileName.matches(regex)){
                target.add(fileName);
            }
        }
        return target;
    }
}
