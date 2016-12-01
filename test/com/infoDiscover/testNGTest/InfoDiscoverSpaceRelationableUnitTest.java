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

public class InfoDiscoverSpaceRelationableUnitTest {

    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceRelationableUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB)){
            ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        }
        ids.closeSpace();
    }

    @Test
    public void testFactRelationableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InterruptedException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Fact testRelationable=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        testRelationable=ids.addFact(testRelationable);
        Assert.assertNotNull(testRelationable);

        doTestForRelationableFunc(ids,testRelationable);

        ids.removeFact(testRelationable.getId());
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testFactRelationableFunc" })
    public void testDimensionRelationableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InterruptedException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Dimension testRelationable=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        testRelationable=ids.addDimension(testRelationable);
        Assert.assertNotNull(testRelationable);

        doTestForRelationableFunc(ids,testRelationable);

        ids.removeDimension(testRelationable.getId());
        ids.closeSpace();
    }

    private void doTestForRelationableFunc(InfoDiscoverSpace ids,Relationable testRelationable) throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InterruptedException{
        List<Relation> attachedRelationList=testRelationable.getAllRelations();
        Assert.assertNotNull(attachedRelationList);
        Assert.assertEquals(attachedRelationList.size(), 0);

        long relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 0);

        List<Relation> specifiedRelationList=testRelationable.getSpecifiedRelations(null, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        Fact factInstanceAForTest=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        Fact factInstanceBForTest=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        factInstanceAForTest=ids.addFact(factInstanceAForTest);
        factInstanceBForTest=ids.addFact(factInstanceBForTest);

        Dimension dimensionInstanceAForTest=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Dimension dimensionInstanceBForTest=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        dimensionInstanceAForTest=ids.addDimension(dimensionInstanceAForTest);
        dimensionInstanceBForTest=ids.addDimension(dimensionInstanceBForTest);

        Relation fromFactRelation=testRelationable.addFromRelation(factInstanceAForTest, UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(fromFactRelation);
        attachedRelationList=testRelationable.getAllRelations();
        Assert.assertNotNull(attachedRelationList);
        Assert.assertEquals(attachedRelationList.size(), 1);
        Assert.assertEquals(fromFactRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertEquals(fromFactRelation.getFromRelationable().getId(), factInstanceAForTest.getId());
        Assert.assertEquals(fromFactRelation.getToRelationable().getId(), testRelationable.getId());

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 1);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.TO);
        Assert.assertEquals(relationCount, 1);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.FROM);
        Assert.assertEquals(relationCount, 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(null, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        Relation toFactRelation=testRelationable.addToRelation(factInstanceBForTest,UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNotNull(toFactRelation);
        attachedRelationList=testRelationable.getAllRelations();
        Assert.assertNotNull(attachedRelationList);
        Assert.assertEquals(attachedRelationList.size(), 2);
        Assert.assertEquals(toFactRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertEquals(toFactRelation.getFromRelationable().getId(), testRelationable.getId());
        Assert.assertEquals(toFactRelation.getToRelationable().getId(), factInstanceBForTest.getId());

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 2);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.TO);
        Assert.assertEquals(relationCount, 1);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.FROM);
        Assert.assertEquals(relationCount, 1);

        specifiedRelationList=testRelationable.getSpecifiedRelations(null, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 2);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);

        Relation fromDimensionRelation=testRelationable.addFromRelation(dimensionInstanceAForTest, UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(fromDimensionRelation);
        attachedRelationList=testRelationable.getAllRelations();
        Assert.assertNotNull(attachedRelationList);
        Assert.assertEquals(attachedRelationList.size(), 3);
        Assert.assertEquals(fromDimensionRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertEquals(fromDimensionRelation.getFromRelationable().getId(), dimensionInstanceAForTest.getId());
        Assert.assertEquals(fromDimensionRelation.getToRelationable().getId(), testRelationable.getId());

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 3);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.TO);
        Assert.assertEquals(relationCount, 2);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.FROM);
        Assert.assertEquals(relationCount, 1);

        specifiedRelationList=testRelationable.getSpecifiedRelations(null, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 3);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 2);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);

        Relation toDimensionRelation=testRelationable.addToRelation(dimensionInstanceBForTest, UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNotNull(toDimensionRelation);
        attachedRelationList=testRelationable.getAllRelations();
        Assert.assertNotNull(attachedRelationList);
        Assert.assertEquals(attachedRelationList.size(), 4);
        Assert.assertEquals(toDimensionRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertEquals(toDimensionRelation.getFromRelationable().getId(), testRelationable.getId());
        Assert.assertEquals(toDimensionRelation.getToRelationable().getId(), dimensionInstanceBForTest.getId());

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 4);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.TO);
        Assert.assertEquals(relationCount, 2);
        relationCount=testRelationable.getRelationCount(null,RelationDirection.FROM);
        Assert.assertEquals(relationCount, 2);

        specifiedRelationList=testRelationable.getSpecifiedRelations(null, RelationDirection.TWO_WAY);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 4);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 2);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 0);

        specifiedRelationList=testRelationable.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 2);

        specifiedRelationList= dimensionInstanceAForTest.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.FROM);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);
        Relation resultRelation=specifiedRelationList.get(0);
        Assert.assertEquals(resultRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertEquals(resultRelation.getId(),fromDimensionRelation.getId());

        specifiedRelationList= dimensionInstanceBForTest.getSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeB, RelationDirection.TO);
        Assert.assertNotNull(specifiedRelationList);
        Assert.assertEquals(specifiedRelationList.size(), 1);
        resultRelation=specifiedRelationList.get(0);
        Assert.assertEquals(resultRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertEquals(resultRelation.getId(), toDimensionRelation.getId());

        for(Relation currentRelation:attachedRelationList){
            boolean isCorrectRelation= currentRelation.getId().equals(fromFactRelation.getId())||
                    toFactRelation.getId().equals(toFactRelation.getId())||
                    fromDimensionRelation.getId().equals(fromDimensionRelation.getId())||
                    toDimensionRelation.getId().equals(toDimensionRelation.getId())?true:false;
            Assert.assertTrue(isCorrectRelation);
            boolean isCorrectRelationType=currentRelation.getType().equals(UnitTestConfigInfo.unitTestRootRelationTypeA)||
                    currentRelation.getType().equals(UnitTestConfigInfo.unitTestRootRelationTypeB)?true:false;
            Assert.assertTrue(isCorrectRelationType);
        }

        boolean exceptionShouldThrownForAddNotExistFromRelationType=false;
        try {
            testRelationable.addFromRelation(dimensionInstanceBForTest, UnitTestConfigInfo.unitTestRootRelationTypeB + "NotExist");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistFromRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistFromRelationType);

        boolean exceptionShouldThrownForAddNotExistToRelationType=false;
        try {
            testRelationable.addToRelation(dimensionInstanceBForTest, UnitTestConfigInfo.unitTestRootRelationTypeB + "NotExist");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistToRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistToRelationType);


        boolean removeRelationResult=testRelationable.removeRelation(fromFactRelation.getId());
        Assert.assertTrue(removeRelationResult);

        Fact factInstanceAForTestNoRelated=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        Fact factInstanceBForTestNoRelated=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        factInstanceAForTestNoRelated=ids.addFact(factInstanceAForTestNoRelated);
        factInstanceBForTestNoRelated=ids.addFact(factInstanceBForTestNoRelated);

        Relation notRelatedRelation=factInstanceAForTestNoRelated.addToRelation(factInstanceBForTestNoRelated,UnitTestConfigInfo.unitTestRootRelationTypeB);

        boolean exceptionShouldThrownForDeleteNotRelatedRelation=false;
        try {
            testRelationable.removeRelation(notRelatedRelation.getId());
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForDeleteNotRelatedRelation=true;
        }
        Assert.assertTrue(exceptionShouldThrownForDeleteNotRelatedRelation);

        boolean exceptionShouldThrownForDeleteNotExistRelation=false;
        try {
            testRelationable.removeRelation("NotExistRelationId");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForDeleteNotExistRelation=true;
        }
        Assert.assertTrue(exceptionShouldThrownForDeleteNotExistRelation);

        boolean exceptionShouldThrownForDeleteNotExistRelationType=false;
        try {
            testRelationable.removeSpecifiedRelations("NotExistRelationType", RelationDirection.TWO_WAY);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForDeleteNotExistRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForDeleteNotExistRelationType);

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 3);

        List<String> removeSpecialRelationList= testRelationable.removeSpecifiedRelations(UnitTestConfigInfo.unitTestRootRelationTypeA, RelationDirection.TO);
        Assert.assertNotNull(removeSpecialRelationList);
        Assert.assertEquals(removeSpecialRelationList.size(), 1);

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 2);

        List<String> removeAllRelationList=testRelationable.removeAllRelations();
        Assert.assertNotNull(removeAllRelationList);
        Assert.assertEquals(removeAllRelationList.size(), 2);

        relationCount=testRelationable.getRelationCount(null, RelationDirection.TWO_WAY);
        Assert.assertEquals(relationCount, 0);

        ids.removeRelation(notRelatedRelation.getId());
        ids.removeFact(factInstanceAForTestNoRelated.getId());
        ids.removeFact(factInstanceBForTestNoRelated.getId());

        ids.removeFact(factInstanceAForTest.getId());
        ids.removeFact(factInstanceBForTest.getId());
        ids.removeDimension(dimensionInstanceAForTest.getId());
        ids.removeDimension(dimensionInstanceBForTest.getId());
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceRelationableUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA)){
            ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeA).removeContainedFaces();
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }
        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB)){
            ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeB).removeContainedFaces();
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA).removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB).removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
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
