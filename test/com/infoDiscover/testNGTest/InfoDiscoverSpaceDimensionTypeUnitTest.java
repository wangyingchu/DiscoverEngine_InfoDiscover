package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class InfoDiscoverSpaceDimensionTypeUnitTest {
    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceDimensionTypeUnitTest");
        System.out.println("--------------------------------------------------");
    }

    @Test
    public void testDimensionTypeBasicFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InterruptedException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        DimensionType dimensionTypeA=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(dimensionTypeA);
        Assert.assertEquals(dimensionTypeA.getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        DimensionType parentTypeOfA=dimensionTypeA.getParentDimensionType();
        Assert.assertNull(parentTypeOfA);

        List<DimensionType> childDimensionTypeList=dimensionTypeA.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 0);
        List<DimensionType> ancestorDimensionTypeList=dimensionTypeA.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 0);
        List<DimensionType> descendantDimensionTypeList=dimensionTypeA.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 0);

        DimensionType childType1=ids.addChildDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1", UnitTestConfigInfo.unitTestRootDimensionTypeA);
        parentTypeOfA=dimensionTypeA.getParentDimensionType();
        Assert.assertNull(parentTypeOfA);

        DimensionType parentTypeOf_CLD1=childType1.getParentDimensionType();
        Assert.assertNotNull(parentTypeOf_CLD1);
        Assert.assertEquals(parentTypeOf_CLD1.getTypeName(), dimensionTypeA.getTypeName());

        childDimensionTypeList=childType1.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 0);
        ancestorDimensionTypeList=childType1.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 1);
        Assert.assertEquals(ancestorDimensionTypeList.get(0).getTypeName(), dimensionTypeA.getTypeName());
        descendantDimensionTypeList=childType1.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 0);

        DimensionType childType2=ids.addChildDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2", UnitTestConfigInfo.unitTestRootDimensionTypeA);
        parentTypeOfA=dimensionTypeA.getParentDimensionType();
        Assert.assertNull(parentTypeOfA);

        DimensionType parentTypeOf_CLD2=childType1.getParentDimensionType();
        Assert.assertNotNull(parentTypeOf_CLD2);
        Assert.assertEquals(parentTypeOf_CLD2.getTypeName(), dimensionTypeA.getTypeName());

        childDimensionTypeList=childType2.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 0);
        ancestorDimensionTypeList=childType2.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 1);
        Assert.assertEquals(ancestorDimensionTypeList.get(0).getTypeName(), dimensionTypeA.getTypeName());
        descendantDimensionTypeList=childType2.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 0);

        childDimensionTypeList=dimensionTypeA.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 2);
        ancestorDimensionTypeList=dimensionTypeA.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 0);
        descendantDimensionTypeList=dimensionTypeA.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 2);

        DimensionType childChildType1=ids.addChildDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1", UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1");
        parentTypeOfA=childChildType1.getParentDimensionType();
        Assert.assertNotNull(parentTypeOfA);
        Assert.assertEquals(parentTypeOfA.getTypeName(), childType1.getTypeName());

        childDimensionTypeList=childChildType1.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 0);

        ancestorDimensionTypeList=childChildType1.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 2);
        for(DimensionType currentDimensionType:ancestorDimensionTypeList){
            boolean isCorrectDimensionType=currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA)||
                    currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD1")?true:false;
            Assert.assertTrue(isCorrectDimensionType);
        }

        descendantDimensionTypeList=childChildType1.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 0);

        childDimensionTypeList=childType1.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(), 1);
        Assert.assertEquals(childDimensionTypeList.get(0).getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1");

        ancestorDimensionTypeList=childType1.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 1);
        Assert.assertEquals(ancestorDimensionTypeList.get(0).getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        descendantDimensionTypeList=childType1.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 1);
        Assert.assertEquals(descendantDimensionTypeList.get(0).getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1");

        childDimensionTypeList=dimensionTypeA.getChildDimensionTypes();
        Assert.assertNotNull(childDimensionTypeList);
        Assert.assertEquals(childDimensionTypeList.size(),2);
        for(DimensionType currentDimensionType:childDimensionTypeList){
            boolean isCorrectRelationType=currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD1")||
                    currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD2")?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        ancestorDimensionTypeList=dimensionTypeA.getAncestorDimensionTypes();
        Assert.assertNotNull(ancestorDimensionTypeList);
        Assert.assertEquals(ancestorDimensionTypeList.size(), 0);

        descendantDimensionTypeList=dimensionTypeA.getDescendantDimensionTypes();
        Assert.assertNotNull(descendantDimensionTypeList);
        Assert.assertEquals(descendantDimensionTypeList.size(), 3);
        for(DimensionType currentDimensionType:descendantDimensionTypeList){
            boolean isCorrectRelationType=currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD1")||
                    currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD1_CLD1")||
                    currentDimensionType.getTypeName().equals(UnitTestConfigInfo.unitTestRootDimensionTypeA+ "_CLD2")?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationA)){
            ids.addDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
            ids.addChildDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA,UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        }
        Dimension dimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension4=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension5=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension6=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Dimension dimension7=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA);
        Dimension dimension8=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA);
        Dimension dimension9=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA);
        Dimension dimension10=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA);

        ids.addDimension(dimension1);
        ids.addDimension(dimension2);
        ids.addDimension(dimension3);
        ids.addDimension(dimension4);
        ids.addDimension(dimension5);
        ids.addDimension(dimension6);
        ids.addDimension(dimension7);
        ids.addDimension(dimension8);
        ids.addDimension(dimension9);
        ids.addDimension(dimension10);

        DimensionType dt1=ids.getDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationA);
        Assert.assertEquals(dt1.countContainedDimensions(false), 6);
        Assert.assertEquals(dt1.countContainedDimensions(true), 10);
        DimensionType dt2=ids.getDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA);
        Assert.assertEquals(dt2.countContainedDimensions(false), 4);
        Assert.assertEquals(dt2.countContainedDimensions(true), 4);

        Assert.assertFalse(ids.removeDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA));
        Assert.assertEquals(dt2.removeContainedDimensions(), 4);
        Assert.assertTrue(ids.removeDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationAChildA));

        Assert.assertFalse(ids.removeDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationA));
        Assert.assertEquals(dt1.removeContainedDimensions(), 6);
        Assert.assertTrue(ids.removeDimensionType(UnitTestConfigInfo.unitTestDimensionTypeForOperationA));

        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceDimensionTypeUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1")){
            ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1").removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1")){
            ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1").removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2")){
            ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2").removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }

        ids.closeSpace();
    }
}
