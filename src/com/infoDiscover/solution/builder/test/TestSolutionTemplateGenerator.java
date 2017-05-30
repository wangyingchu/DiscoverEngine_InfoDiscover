package com.infoDiscover.solution.builder.test;

import com.infoDiscover.solution.builder.SolutionGenerator;

/**
 * Created by sun.
 */
public class TestSolutionTemplateGenerator {

    public static void main(String[] args) throws Exception {

        //
        SolutionGenerator generator = new SolutionGenerator(TestConstants.spaceName,
                TestConstants.prefix);
        generator.generateSolutionFromTemplate();
    }

}
