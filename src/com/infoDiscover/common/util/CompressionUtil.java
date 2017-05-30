package com.infoDiscover.common.util;

import com.infoDiscover.solution.builder.SolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public static String unzip(String filePath) throws Exception {
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
                if (SolutionConstants.SOLUTION_TEMPLATE_JSON_FILE.equalsIgnoreCase(name)) {
                    content = FileUtil.inputStreamToString(zipInputStream, 4096, "UTF-8");
                } else {
                    throw new IOException("This is not a solution template file: " + name);
                }

            }
        }

        return content;
    }

}
