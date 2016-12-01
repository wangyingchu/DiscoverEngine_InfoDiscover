package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class InfoDiscoverSpaceRelationTypeUnitTest {
    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceRelationTypeUnitTest");
        System.out.println("--------------------------------------------------");
    }

    @Test
    public void testRelationTypeBasicFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InterruptedException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        RelationType relationTypeA=ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(relationTypeA);
        Assert.assertEquals(relationTypeA.getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeA);

        RelationType parentTypeOfA=relationTypeA.getParentRelationType();
        Assert.assertNull(parentTypeOfA);

        List<RelationType> childRelationTypeList=relationTypeA.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 0);
        List<RelationType> ancestorRelationTypeList=relationTypeA.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 0);
        List<RelationType> descendantRelationTypeList=relationTypeA.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 0);

        RelationType childType1=ids.addChildRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1", UnitTestConfigInfo.unitTestRootRelationTypeA);
        parentTypeOfA=relationTypeA.getParentRelationType();
        Assert.assertNull(parentTypeOfA);

        RelationType parentTypeOf_CLD1=childType1.getParentRelationType();
        Assert.assertNotNull(parentTypeOf_CLD1);
        Assert.assertEquals(parentTypeOf_CLD1.getTypeName(), relationTypeA.getTypeName());
        childRelationTypeList=childType1.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 0);
        ancestorRelationTypeList=childType1.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 1);
        Assert.assertEquals(ancestorRelationTypeList.get(0).getTypeName(), relationTypeA.getTypeName());
        descendantRelationTypeList=childType1.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 0);

        RelationType childType2=ids.addChildRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2", UnitTestConfigInfo.unitTestRootRelationTypeA);
        parentTypeOfA=relationTypeA.getParentRelationType();
        Assert.assertNull(parentTypeOfA);

        RelationType parentTypeOf_CLD2=childType2.getParentRelationType();
        Assert.assertNotNull(parentTypeOf_CLD2);
        Assert.assertEquals(parentTypeOf_CLD2.getTypeName(), relationTypeA.getTypeName());
        childRelationTypeList=childType2.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 0);
        ancestorRelationTypeList=childType2.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 1);
        Assert.assertEquals(ancestorRelationTypeList.get(0).getTypeName(), relationTypeA.getTypeName());
        descendantRelationTypeList=childType2.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 0);

        childRelationTypeList=relationTypeA.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 2);
        ancestorRelationTypeList=relationTypeA.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 0);
        descendantRelationTypeList=relationTypeA.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 2);

        RelationType childChildType1=ids.addChildRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1", UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD1");
        parentTypeOfA=childChildType1.getParentRelationType();
        Assert.assertNotNull(parentTypeOfA);
        Assert.assertEquals(parentTypeOfA.getTypeName(), childType1.getTypeName());

        childRelationTypeList=childChildType1.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 0);

        ancestorRelationTypeList=childChildType1.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 2);
        for(RelationType currentRelationType:ancestorRelationTypeList){
            boolean isCorrectRelationType=currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA)||
                    currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD1")?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        descendantRelationTypeList=childChildType1.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 0);

        childRelationTypeList=childType1.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(), 1);
        Assert.assertEquals(childRelationTypeList.get(0).getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1");

        ancestorRelationTypeList=childType1.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 1);
        Assert.assertEquals(ancestorRelationTypeList.get(0).getTypeName(),UnitTestConfigInfo.unitTestRootRelationTypeA);

        descendantRelationTypeList=childType1.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 1);
        Assert.assertEquals(descendantRelationTypeList.get(0).getTypeName(),UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1");

        childRelationTypeList=relationTypeA.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeList);
        Assert.assertEquals(childRelationTypeList.size(),2);
        for(RelationType currentRelationType:childRelationTypeList){
            boolean isCorrectRelationType=currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD1")||
                    currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD2")?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        ancestorRelationTypeList=relationTypeA.getAncestorRelationTypes();
        Assert.assertNotNull(ancestorRelationTypeList);
        Assert.assertEquals(ancestorRelationTypeList.size(), 0);

        descendantRelationTypeList=relationTypeA.getDescendantRelationTypes();
        Assert.assertNotNull(descendantRelationTypeList);
        Assert.assertEquals(descendantRelationTypeList.size(), 3);
        for(RelationType currentRelationType:descendantRelationTypeList){
            boolean isCorrectRelationType=currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD1")||
                    currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD1_CLD1")||
                    currentRelationType.getTypeName().equals(UnitTestConfigInfo.unitTestRootRelationTypeA+ "_CLD2")?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        String tempFactType="TESTUSINGFACT0";
        String tempDimensionType="TESTUSINGDIMENSION0";

        if(!ids.hasFactType(tempFactType)){
            ids.addFactType(tempFactType);
        }
        if(!ids.hasDimensionType(tempDimensionType)){
            ids.addDimensionType(tempDimensionType);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationA)){
            ids.addRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationA);
            ids.addChildRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA,UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        }
        Fact fact1=DiscoverEngineComponentFactory.createFact(tempFactType);
        Fact fact2=DiscoverEngineComponentFactory.createFact(tempFactType);
        Fact fact3=DiscoverEngineComponentFactory.createFact(tempFactType);
        Fact fact4=DiscoverEngineComponentFactory.createFact(tempFactType);
        ids.addFact(fact1);
        ids.addFact(fact2);
        ids.addFact(fact3);
        ids.addFact(fact4);

        Dimension dimension1=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        Dimension dimension2=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        Dimension dimension3=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        Dimension dimension4=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        Dimension dimension5=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        Dimension dimension6=DiscoverEngineComponentFactory.createDimension(tempDimensionType);
        ids.addDimension(dimension1);
        ids.addDimension(dimension2);
        ids.addDimension(dimension3);
        ids.addDimension(dimension4);
        ids.addDimension(dimension5);
        ids.addDimension(dimension6);

        ids.connectDimensionWithFact(dimension1.getId(),fact1.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        ids.connectDimensionWithFact(dimension2.getId(),fact2.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        ids.connectDimensionWithFact(dimension3.getId(),fact3.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        ids.connectDimensionWithFact(dimension4.getId(),fact4.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        ids.connectDimensionWithFact(dimension5.getId(),fact1.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA);
        ids.connectDimensionWithFact(dimension6.getId(),fact2.getId(),UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA);

        RelationType rt1=ids.getRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationA);
        Assert.assertEquals(rt1.countContainedRelations(false), 4);
        Assert.assertEquals(rt1.countContainedRelations(true), 6);

        RelationType rt2=ids.getRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA);
        Assert.assertEquals(rt2.countContainedRelations(false), 2);
        Assert.assertEquals(rt2.countContainedRelations(true), 2);

        Assert.assertFalse(ids.removeRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA));
        Assert.assertEquals(rt2.removeContainedRelations(), 2);
        Assert.assertTrue(ids.removeRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationAChildA));

        Assert.assertFalse(ids.removeRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationA));
        Assert.assertEquals(rt1.removeContainedRelations(), 4);
        Assert.assertTrue(ids.removeRelationType(UnitTestConfigInfo.unitTestRelationTypeForOperationA));

        DimensionType dt=ids.getDimensionType(tempDimensionType);
        dt.removeContainedDimensions();
        ids.removeDimensionType(tempDimensionType);
        FactType ft=ids.getFactType(tempFactType);
        ft.removeContainedFaces();
        ids.removeFactType(tempFactType);

        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceRelationTypeUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1")){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1").removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1")){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1").removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2")){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2").removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA).removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB)){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB).removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        }

        ids.closeSpace();
    }
}
