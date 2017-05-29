package com.infoDiscover.solution.builder.test;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionGenerator;
import com.infoDiscover.solution.common.util.PrefixSetting;

/**
 * Created by sun.
 */
public class TestSolutionTemplateGnerator {

    private final static String spaceName = "TEST_SOLUTION";
    private final static String prefix = "Test2_";


    public static void main(String[] args) throws Exception {

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace(spaceName);
        //
        SolutionGenerator generator = new SolutionGenerator(ids, prefix);
        generator.generateSolutionFromTemplate();
        ids.closeSpace();
    }

}
