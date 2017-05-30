package com.infoDiscover.solution.builder.test;

import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.builder.SolutionTemplateParser;
import com.infoDiscover.solution.builder.vo.RelationMappingVO;

import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class TestSolutionTemplateParser {

    public static void main(String[] args) {

        InfoDiscoverSpace ids = DiscoverEngineComponentFactory.connectInfoDiscoverSpace
                (TestConstants.spaceName);

        SolutionTemplateParser parser = new SolutionTemplateParser(TestConstants.spaceName, TestConstants
                .prefix);

        parser.init();

        Map<String, List<RelationMappingVO>> factToDimensionMap = parser.getFactToDimensionMap();
        System.out.println("Fact to dimensions: " + factToDimensionMap);

        Map<String, List<RelationMappingVO>> factToFactMap = parser.getFactToFactMap();
        System.out.println("Fact to fact: " + factToFactMap);

        Map<String, List<RelationMappingVO>> dimensionToDimensionMap = parser
                .getDimensionToDimensionMap();
        System.out.println("dimension to dimensions: " + dimensionToDimensionMap);

        Map<String, List<RelationMappingVO>> factToDateDimensionMap = parser
                .getFactToDateDimension();
        System.out.println("Fact to date dimensions: " + factToDateDimensionMap);


        ids.closeSpace();
    }
}
