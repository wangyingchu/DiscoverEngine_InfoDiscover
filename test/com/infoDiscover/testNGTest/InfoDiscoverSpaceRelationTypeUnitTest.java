package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.RelationType;
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

        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceRelationTypeUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1")){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1_CLD1");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1")){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD1");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2")){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA + "_CLD2");
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        }

        ids.closeSpace();
    }
}
