package com.infoDiscover.solution.builder.test;

import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionTemplateImporter;

/**
 * Created by sun.
 */
public class TestSolutionTemplateImporter {

    public static void main(String[] args) {
        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (TestConstants.spaceName);

        SolutionTemplateImporter importer = new SolutionTemplateImporter(TestConstants
                .spaceName, TestConstants.prefix);

        ids.closeSpace();
    }
}
