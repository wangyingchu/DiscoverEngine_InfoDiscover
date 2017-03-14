package com.infoDiscover.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class FileUtil {

    public static List<String> importCsv(String fileName) {
        List<String> dataList = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(fileName)));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }

    public static String getFileContent(String path) {

        try {
            InputStream stream = new FileInputStream(path);
            byte[] result = new byte[stream.available()];
            stream.read(result);
            stream.close();
            return new String(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveToFile(String file, String content) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            List<String> list = new ArrayList<String>();
            if (content != null) {
                list.add(content);
            } else {
                list.add("");
            }
            fos.write(list.get(0).getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLinesIntoList(String filePath) {
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getFileList(String strPath) {

        List<String> fileList = new ArrayList<>();

        File dir = new File(strPath);

        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.startsWith("Task")) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getName();
                    System.out.println(strFileName.substring(0, strFileName.indexOf(".")));
                    fileList.add(strFileName.substring(0, strFileName.indexOf(".")));
                } else {
                    continue;
                }
            }

        }


        return fileList;
    }

    public static void main(String[] args) {

        String maintainTasks = "/Users/sun/InfoDiscovery/Code/DiscoverEngine_InfoDiscover/src/com" +
                "/infoDiscover/solution/arch/demo/template/maintainProject/task";

        String newTaks = "/Users/sun/InfoDiscovery/Code/DiscoverEngine_InfoDiscover/src/com" +
                "/infoDiscover/solution/arch/demo/template/newProject/task";

        List<String> fileNameList = new ArrayList<>();
        fileNameList = getFileList(newTaks);


    }
}
