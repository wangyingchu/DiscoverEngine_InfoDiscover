package com.viewfunction.testNGTest;

import com.viewfnction.infoDiscoverEngine.dataMart.DimensionType;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
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

        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceDimensionTypeUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1")){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1_CLD1");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1")){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD1");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2")){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA + "_CLD2");
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }

        ids.closeSpace();
    }
}
