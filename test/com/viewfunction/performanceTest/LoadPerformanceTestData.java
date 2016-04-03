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
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA)){
            ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA, UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB)){
            ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB, UnitTestConfigInfo.unitTestRootDimensionTypeB);
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
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfB)){
            ids.addChildRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfB, UnitTestConfigInfo.unitTestRootRelationTypeB);
        }
    }

    private static List<Dimension> createDimension(InfoDiscoverSpace ids) throws InfoDiscoveryEngineRuntimeException{
        List<Dimension> resultDimensionList=new ArrayList<>();
        for(int i=0;i<1000;i++){
            Dimension currentDimension=null;
            int randomSeed=(int)(Math.random()*4);
            int randomPropValue=(int)(Math.random()*200000);
            if(randomSeed==0){
                currentDimension=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
                currentDimension.setInitProperty("dimensionTestProp_A","strA");
                currentDimension.setInitProperty("dimensionTestProp_B",5000);
                currentDimension.setInitProperty("dimensionTestProp_C",true);
                currentDimension.setInitProperty("dimensionTestProp_A1",9000);
                currentDimension.setInitProperty("dimensionTestProp_randomValue",randomPropValue);
                currentDimension=ids.addDimension(currentDimension);
             }
            if(randomSeed==1){
                currentDimension=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeB);
                currentDimension.setInitProperty("dimensionTestProp_A","strB");
                currentDimension.setInitProperty("dimensionTestProp_B",12000);
                currentDimension.setInitProperty("dimensionTestProp_C",false);
                currentDimension.setInitProperty("dimensionTestProp_B1",6000);
                currentDimension.setInitProperty("dimensionTestProp_randomValue",randomPropValue);
                currentDimension=ids.addDimension(currentDimension);
            }
            if(randomSeed==2){
                currentDimension= DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestChildDimensionTypeOfA);
                currentDimension.setInitProperty("dimensionTestProp_A","strC");
                currentDimension.setInitProperty("dimensionTestProp_B",7000);
                currentDimension.setInitProperty("dimensionTestProp_C",true);
                currentDimension.setInitProperty("dimensionTestProp_C1",200);
                currentDimension.setInitProperty("dimensionTestProp_randomValue",randomPropValue);
                currentDimension=ids.addDimension(currentDimension);
            }
            if(randomSeed==3){
                currentDimension= DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestChildDimensionTypeOfB);
                currentDimension.setInitProperty("dimensionTestProp_A","strD");
                currentDimension.setInitProperty("dimensionTestProp_B",650);
                currentDimension.setInitProperty("dimensionTestProp_C",false);
                currentDimension.setInitProperty("dimensionTestProp_D1",200);
                currentDimension.setInitProperty("dimensionTestProp_randomValue",randomPropValue);
                currentDimension=ids.addDimension(currentDimension);
            }
            resultDimensionList.add(currentDimension);
        }
        return resultDimensionList;
    }

    private static void loadFacts(InfoDiscoverSpace ids,List<Dimension> dimensionList) throws InfoDiscoveryEngineRuntimeException{
        Date startDate=new Date();
        System.out.println("Start loading.");
        String[] relationTypeArray=new String[]{
                UnitTestConfigInfo.unitTestRootRelationTypeA,
                UnitTestConfigInfo.unitTestRootRelationTypeB,
                UnitTestConfigInfo.unitTestChildRelationTypeAOfA,
                UnitTestConfigInfo.unitTestChildRelationTypeAOfB
        };

        String[] factTypeArray=new String[]{
                UnitTestConfigInfo.unitTestRootFactTypeA,
                UnitTestConfigInfo.unitTestRootFactTypeB
        };

        for(int i=0;i<500000;i++){
            int factTypeInt=(int)(Math.random()*factTypeArray.length);
            String factType=factTypeArray[factTypeInt];
            Fact currentFact=DiscoverEngineComponentFactory.createFact(factType);
            currentFact.setInitProperty("factTestProp_A",new Date());
            currentFact.setInitProperty("factTestProp_B", (int)(Math.random()*10000));
            currentFact.setInitProperty("factTestProp_C", (short)(Math.random()*100));
            currentFact.setInitProperty("factTestProp_D", (long)(Math.random()*500000));
            currentFact=ids.addFact(currentFact);
            int relationTypeInt=(int)(Math.random()*relationTypeArray.length);
            String relationTypeType=relationTypeArray[relationTypeInt];
            int dimensionInt=(int)(Math.random()*dimensionList.size());
            Dimension currentDimension=dimensionList.get(dimensionInt);
            ids.attachFactToDimension(currentFact.getId(),currentDimension.getId(),relationTypeType);
            System.out.println("Next Record->......");
        }
        System.out.println("Done.");
        Date finishDate=new Date();
        System.out.println("===============================");
        System.out.println("Start at: "+startDate.toString());
        System.out.println("Finish at: "+finishDate.toString());
        System.out.println("===============================");
    }
}
