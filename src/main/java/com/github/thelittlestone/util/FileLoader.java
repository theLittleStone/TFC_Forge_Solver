package com.github.thelittlestone.util;

import com.github.thelittlestone.MainApplication;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by theLittleStone on 2023/5/10.
 */
public class FileLoader {
    public static final String[] InPackageFileArray = {"Recipes.json", "Config.json"};
//    public static String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()
//            .replace("TFC_Forge_Solver.jar", "").replaceFirst("/", "");
    //包所在的路径
    public static final String PackagePath = MainApplication.class.getProtectionDomain().getCodeSource().getLocation()
        .getPath().replaceFirst("/", "");
    //包内的资源路径
    public static final String InPackageResourcePath = "/com/github/thelittlestone/files/";
    //包外的配置文件存放文件夹
    public static final String OutPackageResourcePath = "TFC_Forge_solver_config/";

    //包外配置文件路径, 此项可更换(根据开发流程选择打包前还是打包后)
    public static final String OutPath = OutPackageResourcePath; //PackagePath + OutPackageResourcePath

    public static boolean InitResult = false;
    static {
        try {
            init();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() throws URISyntaxException, IOException {
        URI targetURI = new URI(OutPath);
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
                URI u = new URI(OutPath + s);
                if (!(new File(u.getPath()).exists())){
                    copyFile(InPackageResourcePath + s, u.getPath());
                }
            }
            InitResult = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //复制文件, 两个参数都需要完整路径名
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

    //读取配置文件, 只要求文件名, 不含路径, 可选择读取包外文件或是包内默认文件
    public static String getFileContent(String fileName, boolean readInPackageConfigFile) throws IOException {
        if (!InitResult && !readInPackageConfigFile){
            throw new IOException("无法读取指定文件" + fileName);
        }
        if (readInPackageConfigFile){
            //读取包内配置
            InputStream is = MainApplication.class.getResourceAsStream(InPackageResourcePath + fileName);
            assert is != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String result =  bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            bufferedReader.close();
            is.close();
            return result;
        }
        //包外配置
        URI u;
        try {
            u = new URI(OutPath + fileName);
        }catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("无法读取包外配置文件");
        }
        Scanner scanner = new Scanner(Paths.get(u.getPath()));
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }
        scanner.close();
        return content.toString();
    }

    //将字符串写入文件, fileName只要求文件名, 不带路径
    public static void writeToFile(String fileName, String content) throws IOException {
        if (!InitResult){
            throw new IOException("初始化配置文件夹失败");
        }
        String targetPath = OutPath + fileName;
        try (FileWriter fileWriter = new FileWriter(targetPath, false)){
            fileWriter.write(content);
        }
    }

    //删掉配置文件, fileName只要求文件名, 不带路径
    public static void deleteFile(String fileName) throws IOException {
        String pathName = OutPath + fileName;
        Files.delete(Path.of(pathName));
    }

    //根据正则表达式返回符合条件的文件名
    public static ArrayList<String> getOutPackageFileNamesContains(String regex){
        ArrayList<String> target = new ArrayList<>();
        File file = new File(OutPath);
        String[] fileNameList = file.list();
        if (fileNameList == null) {
            return null;
        }
        for (String fileName : fileNameList) {
            File f = new File(OutPath + fileName);
            if (f.isDirectory()){
                continue;
            }if (fileName.matches(regex)){
                target.add(fileName);
            }
        }
        return target;
    }
}
