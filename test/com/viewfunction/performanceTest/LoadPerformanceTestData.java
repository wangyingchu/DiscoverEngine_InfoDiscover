package com.viewfunction.performanceTest;

import com.viewfnction.infoDiscoverEngine.dataMart.Dimension;
import com.viewfnction.infoDiscoverEngine.dataMart.Fact;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.viewfunction.testNGTest.UnitTestConfigInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadPerformanceTestData {
    public static void main(String[] args){
        InfoDiscoverSpace ids=null;
        try{
            ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace("PerformanceTestDB");
            createTypes(ids);

            List<Dimension> dimensions=createDimension(ids);

            loadFacts(ids,dimensions);

        } catch (InfoDiscoveryEngineException e) {
            e.printStackTrace();
        } finally {
            if (ids != null) {
                ids.closeSpace();
            }
        }
    }

    private static void createTypes(InfoDiscoverSpace ids) throws InfoDiscoveryEngineException{
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA)){
            ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB)){
            ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB)){
            ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeAOfA)){
            ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeAOfA, UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB)){
            ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA)){
            ids.addChildRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA, UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
    }

    private static List<Dimension> createDimension(InfoDiscoverSpace ids) throws InfoDiscoveryEngineRuntimeException{
        List<Dimension> resultDimensionList=new ArrayList<>();

        Dimension dimensionA=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        dimensionA.setInitProperty("dimensionTestProp_A","strA");
        dimensionA.setInitProperty("dimensionTestProp_B",5000);
        dimensionA.setInitProperty("dimensionTestProp_C",true);
        dimensionA.setInitProperty("dimensionTestProp_A1",9000);
        dimensionA=ids.addDimension(dimensionA);

        Dimension dimensionB=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        dimensionB.setInitProperty("dimensionTestProp_A","strB");
        dimensionB.setInitProperty("dimensionTestProp_B",12000);
        dimensionB.setInitProperty("dimensionTestProp_C",false);
        dimensionB.setInitProperty("dimensionTestProp_B1",6000);
        dimensionB=ids.addDimension(dimensionB);

        Dimension dimensionC= DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestChildDimensionTypeAOfA);
        dimensionC.setInitProperty("dimensionTestProp_A","strC");
        dimensionC.setInitProperty("dimensionTestProp_B",7000);
        dimensionC.setInitProperty("dimensionTestProp_C",true);
        dimensionC.setInitProperty("dimensionTestProp_C1",200);
        dimensionC=ids.addDimension(dimensionC);

        resultDimensionList.add(dimensionA);
        resultDimensionList.add(dimensionB);
        resultDimensionList.add(dimensionC);

        return resultDimensionList;
    }

    private static void loadFacts(InfoDiscoverSpace ids,List<Dimension> dimensionList) throws InfoDiscoveryEngineRuntimeException{

        String[] relationTypeArray=new String[]{UnitTestConfigInfo.unitTestRootRelationTypeA,
                UnitTestConfigInfo.unitTestRootRelationTypeB,
                UnitTestConfigInfo.unitTestChildRelationTypeAOfA};

        String[] factTypeArray=new String[]{UnitTestConfigInfo.unitTestRootFactTypeA,
                UnitTestConfigInfo.unitTestRootFactTypeB};

        for(int i=0;i<1000000;i++){
            int factTypeInt=(int)(Math.random()*factTypeArray.length);
            String factType=factTypeArray[factTypeInt];
            Fact currentFact=DiscoverEngineComponentFactory.createFact(factType);
            currentFact.setInitProperty("factTestProp_A",new Date());
            currentFact.setInitProperty("factTestProp_B", (int)(Math.random()*10000));
            currentFact.setInitProperty("factTestProp_C", (short)(Math.random()*100));
            currentFact=ids.addFact(currentFact);

            int relationTypeInt=(int)(Math.random()*relationTypeArray.length);
            String relationTypeType=relationTypeArray[relationTypeInt];

            int dimensionInt=(int)(Math.random()*dimensionList.size());
            Dimension currentDimension=dimensionList.get(dimensionInt);

            ids.attachFactToDimension(currentFact.getId(),currentDimension.getId(),relationTypeType);
        }
    }
}
