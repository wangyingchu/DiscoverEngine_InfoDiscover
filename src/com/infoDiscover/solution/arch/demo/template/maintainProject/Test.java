package com.infoDiscover.solution.arch.demo.template.maintainProject;

import com.infoDiscover.common.util.FileUtil;

import java.util.List;

/**
 * Created by sun.
 */
public class Test {

    public static void main(String[] args){
        String file = "/Users/sun/InfoDiscovery/Demodata/test.csv";

        List<String> list = FileUtil.importCsv(file);

        for(String line: list) {
//            System.out.println("line: " + line);
            String[] value = line.trim().split(",");
            String type = value[2];
            String name = value[0];
            String comment = "// " + value[1];
            System.out.println(type + " " + name + "; " + comment);
        }
    }
}
