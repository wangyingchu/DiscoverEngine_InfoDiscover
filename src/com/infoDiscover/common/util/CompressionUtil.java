package com.infoDiscover.common.util;

import com.infoDiscover.solution.builder.SolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by sun.
 */
public class CompressionUtil {

    private final static Logger logger = LoggerFactory.getLogger(CompressionUtil.class);

    public static void zip(String zipFileName, String fileName, String input)
            throws Exception {
        logger.info("Start to zip with fileName: {} to zipFileName: {}", fileName, zipFileName);

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));

        out.putNextEntry(new ZipEntry(fileName));
        out.write(input.getBytes());

        logger.info("zip completed.");

        out.close();
    }

    public static void zip(String zipFileName, Map<String, String> files)
            throws Exception {
        logger.info("Start to zip with files: {} to zipFileName: {}", files, zipFileName);

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        Set<String> keys = files.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String file = it.next();
            String input = files.get(file);
            out.putNextEntry(new ZipEntry(file));
            out.write(input.getBytes("UTF-8"));
        }

        logger.info("zip completed.");

        out.close();
    }

    public static String unzip(String filePath, String fileName) throws Exception {
        logger.info("Start to unzip file: {}", filePath);

        String content = "";

        InputStream inputStream = new FileInputStream(new File(filePath));
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.isDirectory()) {
                // TODO: to implement
            } else {
                String name = zipEntry.getName();
                logger.info("file in zipped file: {}", name);
                if (fileName.equalsIgnoreCase(name)) {
                    content = FileUtil.inputStreamToString(zipInputStream, 4096, "UTF-8");
                } else {
                    return null;
                }

            }
        }

        return content;
    }

    public static Map<String, String> unzip(String filePath) throws Exception {
        logger.info("Start to unzip file: {}", filePath);

        Map<String, String> filesMap = new HashMap<>();

        InputStream inputStream = new FileInputStream(new File(filePath));
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.isDirectory()) {
                // TODO: to implement
                return filesMap;
            } else {
                String name = zipEntry.getName();
                logger.info("file in zipped file: {}", name);
                String content = FileUtil.inputStreamToString(zipInputStream, 4096, "UTF-8");
                filesMap.put(name, content);
            }
        }

        return filesMap;
    }
}
