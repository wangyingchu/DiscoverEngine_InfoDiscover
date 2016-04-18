package com.viewfunction.performanceTest;

import com.viewfnction.infoDiscoverEngine.dataMart.*;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.viewfunction.testNGTest.UnitTestConfigInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadPerformanceTestData {
    public static void main_0(String[] args){
        InfoDiscoverSpace ids=null;
        try{
            //ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace("PerformanceTestDB");
            ids= DiscoverEngineComponentFactory.connectNoTransactionInfoDiscoverSpace("Test001");
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

    public static void main(String[] args){
        roundLoadPerformanceTestingFacts(200,1000);
    }

    private static void createTypes(InfoDiscoverSpace ids) throws InfoDiscoveryEngineException{
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA)){
            FactType factType1=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
            factType1.addTypeProperty("factTestProp_A",PropertyType.DATE);
            factType1.addTypeProperty("factTestProp_B",PropertyType.INT);
            factType1.addTypeProperty("factTestProp_C",PropertyType.SHORT);
            factType1.addTypeProperty("factTestProp_D",PropertyType.LONG);
        }
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB)){
            FactType factType2=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
            factType2.addTypeProperty("factTestProp_A",PropertyType.DATE);
            factType2.addTypeProperty("factTestProp_B",PropertyType.INT);
            factType2.addTypeProperty("factTestProp_C",PropertyType.SHORT);
            factType2.addTypeProperty("factTestProp_D",PropertyType.LONG);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            DimensionType dimensionType1=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
            dimensionType1.addTypeProperty("dimensionTestProp_A", PropertyType.STRING);
            dimensionType1.addTypeProperty("dimensionTestProp_B", PropertyType.INT);
            dimensionType1.addTypeProperty("dimensionTestProp_C", PropertyType.BOOLEAN);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB)){
            DimensionType dimensionType2=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
            dimensionType2.addTypeProperty("dimensionTestProp_A", PropertyType.STRING);
            dimensionType2.addTypeProperty("dimensionTestProp_B", PropertyType.INT);
            dimensionType2.addTypeProperty("dimensionTestProp_C", PropertyType.BOOLEAN);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA)){
            DimensionType dimensionType3=ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA, UnitTestConfigInfo.unitTestRootDimensionTypeA);
            //dimensionType3.addTypeProperty("dimensionTestProp_A", PropertyType.STRING);
            //dimensionType3.addTypeProperty("dimensionTestProp_B", PropertyType.INT);
            //dimensionType3.addTypeProperty("dimensionTestProp_C", PropertyType.BOOLEAN);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB)){
            DimensionType dimensionType4=ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB, UnitTestConfigInfo.unitTestRootDimensionTypeB);
            //dimensionType4.addTypeProperty("dimensionTestProp_A", PropertyType.STRING);
            //dimensionType4.addTypeProperty("dimensionTestProp_B", PropertyType.INT);
            //dimensionType4.addTypeProperty("dimensionTestProp_C", PropertyType.BOOLEAN);
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

        for(int i=0;i<2000000;i++){
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

    private static void roundLoadPerformanceTestingFacts(int roundNumber,int batchSize){
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        Date startDate=new Date();
        for(int i=0;i<roundNumber;i++){
            System.out.println("Start loading for round "+i);
            batchLoadPerformanceTestingFacts(i,batchSize);
            System.out.println("Finish loading for round "+i);
        }
        Date finishDate=new Date();
        System.out.println("===============================");
        System.out.println("Start at: "+startDate.toString());
        System.out.println("Finish at: "+finishDate.toString());
        System.out.println("===============================");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
    }

    private static void batchLoadPerformanceTestingFacts(int batchSequence,int batchSize){
        Date startDate=new Date();
        System.out.println("Start loading for batch "+batchSequence);
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
        InfoDiscoverSpace ids=null;
        try{
            //ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace("PerformanceTestDB");
            ids= DiscoverEngineComponentFactory.connectNoTransactionInfoDiscoverSpace("Test001");
            List<Dimension> dimensionList=createDimension(ids);
            for(int i=0;i<batchSize;i++){
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
        } catch (InfoDiscoveryEngineException e) {
            e.printStackTrace();
        } finally {
            if (ids != null) {
                ids.closeSpace();
            }
        }
        System.out.println("Done batch "+batchSequence);
        Date finishDate=new Date();
        System.out.println("===============================");
        System.out.println("Start at: "+startDate.toString());
        System.out.println("Finish at: "+finishDate.toString());
        System.out.println("===============================");
    }
}
