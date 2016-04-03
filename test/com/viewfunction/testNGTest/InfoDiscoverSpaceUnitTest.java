package com.viewfunction.testNGTest;

import com.viewfnction.infoDiscoverEngine.dataMart.*;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.viewfnction.infoDiscoverEngine.dataWarehouseImpl.OrientDBInformationExplorerImpl;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.viewfnction.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class InfoDiscoverSpaceUnitTest {
    @BeforeClass
    public void initData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA)){
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }
        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB)){
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        }

        if(ids.hasRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        }
        ids.closeSpace();
    }

    @Test
    public void testSpaceBasicFunc() {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        Assert.assertNotNull(ids);
        String spaceName=ids.getSpaceName();
        Assert.assertEquals(spaceName, UnitTestConfigInfo.unitTestSpaceName);
        InformationExplorer informationExplore=ids.getInformationExplorer();
        Assert.assertNotNull(informationExplore);
        Assert.assertTrue(informationExplore instanceof OrientDBInformationExplorerImpl);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceBasicFunc" })
    public void testSpaceFactTypeFunc() throws InfoDiscoveryEngineDataMartException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        List<String> factTypeList=ids.getFactTypesList();
        Assert.assertNotNull(factTypeList);
        Assert.assertEquals(factTypeList.size(), 0);

        boolean hasTypeA=ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertFalse(hasTypeA);

        FactType typeAFactType=ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNull(typeAFactType);

        typeAFactType=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(typeAFactType);
        Assert.assertEquals(typeAFactType.getTypeName(), UnitTestConfigInfo.unitTestRootFactTypeA);

        factTypeList=ids.getFactTypesList();
        Assert.assertEquals(factTypeList.size(), 1);

        hasTypeA=ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertTrue(hasTypeA);

        typeAFactType=ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(typeAFactType);
        Assert.assertEquals(typeAFactType.getTypeName(), UnitTestConfigInfo.unitTestRootFactTypeA);

        FactType typeBFactType=ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertNull(typeBFactType);

        typeBFactType=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertNotNull(typeBFactType);
        Assert.assertEquals(typeBFactType.getTypeName(), UnitTestConfigInfo.unitTestRootFactTypeB);

        boolean hasTypeB=ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertTrue(hasTypeB);

        factTypeList=ids.getFactTypesList();
        Assert.assertEquals(factTypeList.size(), 2);

        typeBFactType=ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertNotNull(typeBFactType);
        Assert.assertEquals(typeBFactType.getTypeName(), UnitTestConfigInfo.unitTestRootFactTypeB);

        for(String currentType:factTypeList){
            boolean typeMatched=(currentType.equals(UnitTestConfigInfo.unitTestRootFactTypeA)||
                    currentType.equals(UnitTestConfigInfo.unitTestRootFactTypeB))?true:false;
            Assert.assertTrue(typeMatched);
        }

        boolean exceptionShouldThrownForAdd=false;
        try {
            ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForAdd=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAdd);

        boolean removeFactTypeResult=ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertTrue(removeFactTypeResult);

        hasTypeA=ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertFalse(hasTypeA);

        factTypeList=ids.getFactTypesList();
        Assert.assertEquals(factTypeList.size(), 1);

        boolean removeFactTypeBResult=ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertTrue(removeFactTypeBResult);

        hasTypeB=ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertFalse(hasTypeB);

        factTypeList=ids.getFactTypesList();
        Assert.assertEquals(factTypeList.size(), 0);

        boolean exceptionShouldThrownForRemove=false;
        try {
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForRemove=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemove);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceFactTypeFunc" })
    public void testSpaceDimensionTypeFunc() throws InfoDiscoveryEngineDataMartException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        List<String> rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertNotNull(rootDimensionTypesList);
        Assert.assertEquals(rootDimensionTypesList.size(), 0);

        boolean hasTypeA=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertFalse(hasTypeA);

        DimensionType dimensionTypeA=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNull(dimensionTypeA);

        dimensionTypeA=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(dimensionTypeA);

        hasTypeA=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertTrue(hasTypeA);

        Assert.assertEquals(dimensionTypeA.getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        dimensionTypeA=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(dimensionTypeA);

        rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertEquals(rootDimensionTypesList.size(), 1);

        boolean hasTypeB=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertFalse(hasTypeB);

        DimensionType dimensionTypeB=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNull(dimensionTypeB);

        dimensionTypeB=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNotNull(dimensionTypeB);

        hasTypeB=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertTrue(hasTypeB);

        Assert.assertEquals(dimensionTypeB.getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeB);

        dimensionTypeB=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNotNull(dimensionTypeB);

        rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertEquals(rootDimensionTypesList.size(), 2);

        for(String currentType:rootDimensionTypesList){
            boolean typeMatched=(currentType.equals(UnitTestConfigInfo.unitTestRootDimensionTypeA)||
                    currentType.equals(UnitTestConfigInfo.unitTestRootDimensionTypeB))?true:false;
            Assert.assertTrue(typeMatched);
        }

        boolean exceptionShouldThrownForAdd=false;
        try {
            ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForAdd=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAdd);

        DimensionType childDimensionTypeA=ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA, UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(childDimensionTypeA);
        Assert.assertNotNull(childDimensionTypeA.getParentDimensionType());
        Assert.assertEquals(childDimensionTypeA.getTypeName(), UnitTestConfigInfo.unitTestChildDimensionTypeOfA);
        Assert.assertEquals(childDimensionTypeA.getParentDimensionType().getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        DimensionType childDimensionType=ids.getDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA);
        Assert.assertNotNull(childDimensionType);

        rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertEquals(rootDimensionTypesList.size(), 2);

        List<DimensionType> childTypesOfTypeA=dimensionTypeA.getChildDimensionTypes();
        Assert.assertNotNull(childTypesOfTypeA);
        Assert.assertEquals(childTypesOfTypeA.size(), 1);
        Assert.assertEquals(childTypesOfTypeA.get(0).getTypeName(),UnitTestConfigInfo.unitTestChildDimensionTypeOfA);

        boolean exceptionShouldThrownForAddExistChildType=false;
        try {
            ids.addChildDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA, UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForAddExistChildType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddExistChildType);

        boolean exceptionShouldThrownForRemoveTypeHasChildren=false;
        try {
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForRemoveTypeHasChildren=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveTypeHasChildren);

        boolean removeChildTypeResult=ids.removeDimensionType(UnitTestConfigInfo.unitTestChildDimensionTypeOfA);
        Assert.assertTrue(removeChildTypeResult);

        boolean removeTypeAResult=ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertTrue(removeTypeAResult);

        dimensionTypeA=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNull(dimensionTypeA);

        hasTypeA=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertFalse(hasTypeA);

        rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertEquals(rootDimensionTypesList.size(), 1);

        boolean removeTypeBResult=ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertTrue(removeTypeBResult);

        dimensionTypeB=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNull(dimensionTypeB);

        hasTypeB=ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertFalse(hasTypeB);

        rootDimensionTypesList=ids.getRootDimensionTypesList();
        Assert.assertEquals(rootDimensionTypesList.size(), 0);

        boolean exceptionShouldThrownForRemove=false;
        try {
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForRemove=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemove);

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceDimensionTypeFunc" })
    public void testSpaceRelationTypeFunc() throws InfoDiscoveryEngineDataMartException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        List<String> rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 0);

        boolean hasTypeA=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertFalse(hasTypeA);

        RelationType relationTypeA=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNull(relationTypeA);

        relationTypeA=ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(relationTypeA);

        hasTypeA=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertTrue(hasTypeA);

        relationTypeA=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(relationTypeA);

        Assert.assertEquals(relationTypeA.getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeA);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 1);

        boolean hasTypeB=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertFalse(hasTypeB);

        RelationType relationTypeB=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNull(relationTypeB);

        relationTypeB=ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNotNull(relationTypeB);

        hasTypeB=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertTrue(hasTypeB);

        relationTypeB=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNotNull(relationTypeB);

        Assert.assertEquals(relationTypeB.getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeB);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 2);

        for(String currentType:rootRelationTypesList){
            boolean typeMatched=(currentType.equals(UnitTestConfigInfo.unitTestRootRelationTypeA)||
                    currentType.equals(UnitTestConfigInfo.unitTestRootRelationTypeB))?true:false;
            Assert.assertTrue(typeMatched);
        }

        boolean exceptionShouldThrownForAdd=false;
        try {
            ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForAdd=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAdd);

        RelationType childRelationType=ids.addChildRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA,UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(childRelationType);
        Assert.assertNotNull(childRelationType.getParentRelationType());
        Assert.assertEquals(childRelationType.getTypeName(), UnitTestConfigInfo.unitTestChildRelationTypeAOfA);
        Assert.assertEquals(childRelationType.getParentRelationType().getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeA);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 2);

        List<RelationType> childRelationTypeOfTypeA=relationTypeA.getChildRelationTypes();
        Assert.assertNotNull(childRelationTypeOfTypeA);
        Assert.assertEquals(childRelationTypeOfTypeA.size(), 1);
        Assert.assertEquals(childRelationTypeOfTypeA.get(0).getTypeName(), UnitTestConfigInfo.unitTestChildRelationTypeAOfA);

        boolean exceptionShouldThrownForAddExistChildType=false;
        try {
            ids.addChildRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA, UnitTestConfigInfo.unitTestRootRelationTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForAddExistChildType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddExistChildType);

        boolean exceptionShouldThrownForRemoveTypeHasChildren=false;
        try {
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForRemoveTypeHasChildren=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveTypeHasChildren);

        boolean removeChildTypeResult=ids.removeRelationType(UnitTestConfigInfo.unitTestChildRelationTypeAOfA);
        Assert.assertTrue(removeChildTypeResult);

        boolean removeTypeAResult=ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertTrue(removeTypeAResult);

        relationTypeA=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNull(relationTypeA);

        hasTypeA=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertFalse(hasTypeA);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 1);

        boolean removeTypeBResult=ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertTrue(removeTypeBResult);

        relationTypeB=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertNull(relationTypeB);

        hasTypeB=ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeB);
        Assert.assertFalse(hasTypeB);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 0);

        boolean exceptionShouldThrownForRemove=false;
        try {
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }catch (InfoDiscoveryEngineDataMartException e){
            exceptionShouldThrownForRemove=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemove);

        rootRelationTypesList=ids.getRootRelationTypesList();
        Assert.assertNotNull(rootRelationTypesList);
        Assert.assertEquals(rootRelationTypesList.size(), 0);

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceRelationTypeFunc" })
    public void testSpaceFactFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Fact testFactA=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);

        boolean exceptionShouldThrownForNotExistFactType=false;
        try {
            ids.addFact(testFactA);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistFactType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistFactType);

        FactType addFactTypeAResult=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(addFactTypeAResult);

        Fact resultFactA=ids.addFact(testFactA);
        Assert.assertNotNull(resultFactA);
        Assert.assertNotNull(resultFactA.getId());
        Assert.assertEquals(resultFactA.getType(), UnitTestConfigInfo.unitTestRootFactTypeA);

        Fact wrongIdReturn=ids.getFactById(resultFactA.getId() + "NotMatch");
        Assert.assertNull(wrongIdReturn);

        String currentFactId=resultFactA.getId();
        Fact correctIdReturnFact=ids.getFactById(currentFactId);

        Assert.assertNotNull(correctIdReturnFact);
        Assert.assertEquals(correctIdReturnFact.getType(), UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertEquals(correctIdReturnFact.getId(), currentFactId);

        boolean removeExistFactResult=ids.removeFact(currentFactId);
        Assert.assertTrue(removeExistFactResult);

        correctIdReturnFact=ids.getFactById(currentFactId);
        Assert.assertNull(correctIdReturnFact);

        boolean exceptionShouldThrownForRemoveNotExistFactId=false;
        try {
            ids.removeFact(currentFactId + "NotMatch");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistFactId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistFactId);

        ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Dimension dimensionForTest=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        dimensionForTest=ids.addDimension(dimensionForTest);
        String dimensionId=dimensionForTest.getId();

        boolean exceptionShouldThrownForGetNotFactMeasurable=false;
        try {
            ids.getFactById(dimensionId);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForGetNotFactMeasurable=true;
        }
        Assert.assertTrue(exceptionShouldThrownForGetNotFactMeasurable);

        Fact testFactB=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);

        boolean addInitProperty_BOOLEAN=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, true);
        Assert.assertTrue(addInitProperty_BOOLEAN);
        boolean addInitProperty_INT=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT,1000);
        Assert.assertTrue(addInitProperty_INT);
        boolean addInitProperty_SHORT=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT,(short)11);
        Assert.assertTrue(addInitProperty_SHORT);
        boolean addInitProperty_LONG=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG,(long)5500);
        Assert.assertTrue(addInitProperty_LONG);
        boolean addInitProperty_FLOAT=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT, (float) 445.66);
        Assert.assertTrue(addInitProperty_FLOAT);
        boolean addInitProperty_DOUBLE=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE,(double)456.777);
        Assert.assertTrue(addInitProperty_DOUBLE);
        Date testDate=new Date();
        boolean addInitProperty_DATE=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE, testDate);
        Assert.assertTrue(addInitProperty_DATE);
        boolean addInitProperty_STRING=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING,"this is a string");
        Assert.assertTrue(addInitProperty_STRING);
        byte[] byeArray="This is a str".getBytes();
        boolean addInitProperty_BINARY=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY,byeArray);
        Assert.assertTrue(addInitProperty_BINARY);
        boolean addInitProperty_BYTE=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE, (byte) 444);
        Assert.assertTrue(addInitProperty_BYTE);

        boolean addExistInitProperty=testFactB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, false);
        Assert.assertFalse(addExistInitProperty);
        testFactB=ids.addFact(testFactB);

        Property property_BOOLEAN= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNotNull(property_BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyType(), PropertyType.BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyValue(), true);

        Property property_INT= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertNotNull(property_INT);
        Assert.assertEquals(property_INT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertEquals(property_INT.getPropertyType(),PropertyType.INT);
        Assert.assertEquals(property_INT.getPropertyValue(), 1000);

        Property property_SHORT= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertNotNull(property_SHORT);
        Assert.assertEquals(property_SHORT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertEquals(property_SHORT.getPropertyType(),PropertyType.SHORT);
        Assert.assertEquals(property_SHORT.getPropertyValue(), (short) 11);

        Property property_LONG= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertNotNull(property_LONG);
        Assert.assertEquals(property_LONG.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertEquals(property_LONG.getPropertyType(),PropertyType.LONG);
        Assert.assertEquals(property_LONG.getPropertyValue(), (long) 5500);

        Property property_FLOAT= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertNotNull(property_FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyType(),PropertyType.FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyValue(), (float) 445.66);

        Property property_DOUBLE= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertNotNull(property_DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyType(),PropertyType.DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyValue(), (double) 456.777);

        Property property_DATE= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertNotNull(property_DATE);
        Assert.assertEquals(property_DATE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(property_DATE.getPropertyType(),PropertyType.DATE);
        Assert.assertEquals(property_DATE.getPropertyValue(), testDate);

        Property property_STRING= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertNotNull(property_STRING);
        Assert.assertEquals(property_STRING.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(property_STRING.getPropertyType(), PropertyType.STRING);
        Assert.assertEquals(property_STRING.getPropertyValue(), "this is a string");

        Property property_BINARY= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertNotNull(property_BINARY);
        Assert.assertEquals(property_BINARY.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertEquals(property_BINARY.getPropertyType(), PropertyType.BINARY);
        MeasurableContentHelper mch=DiscoverEngineComponentFactory.getMeasurableContentHelper();
        byte[] propertyContent=mch.retrieveBinaryPropertyContent(property_BINARY);
        Assert.assertEquals(propertyContent,byeArray);

        Property property_BYTE= testFactB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertNotNull(property_BYTE);
        Assert.assertEquals(property_BYTE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(property_BYTE.getPropertyType(),PropertyType.BYTE);
        Assert.assertEquals(property_BYTE.getPropertyValue(),(byte) 444);

        ids.removeFact(testFactB.getId());
        ids.removeDimension(dimensionId);
        ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceFactFunc" })
    public void testSpaceDimensionFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Dimension testDimensionA=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);

        boolean exceptionShouldThrownForNotExistDimensionType=false;
        try {
            ids.addDimension(testDimensionA);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistDimensionType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistDimensionType);

        DimensionType dimensionTypeA=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(dimensionTypeA);
        Assert.assertEquals(dimensionTypeA.getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        Dimension resultDimension=ids.addDimension(testDimensionA);
        Assert.assertNotNull(resultDimension);
        Assert.assertNotNull(resultDimension.getId());
        Assert.assertEquals(resultDimension.getType(), UnitTestConfigInfo.unitTestRootDimensionTypeA);

        Dimension wrongIdReturn=ids.getDimensionById(resultDimension.getId() + "NotMatch");
        Assert.assertNull(wrongIdReturn);

        String currentDimensionId=resultDimension.getId();
        Dimension correctIdReturnDimension=ids.getDimensionById(currentDimensionId);

        Assert.assertNotNull(correctIdReturnDimension);
        Assert.assertEquals(correctIdReturnDimension.getType(), UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertEquals(correctIdReturnDimension.getId(), currentDimensionId);

        boolean removeExistDimensionResult=ids.removeDimension(currentDimensionId);
        Assert.assertTrue(removeExistDimensionResult);

        correctIdReturnDimension=ids.getDimensionById(currentDimensionId);
        Assert.assertNull(correctIdReturnDimension);

        boolean exceptionShouldThrownForRemoveNotExistDimensionId=false;
        try {
            ids.removeDimension(currentDimensionId + "NotMatch");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistDimensionId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistDimensionId);

        ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Fact factForTest=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        factForTest=ids.addFact(factForTest);
        String factId=factForTest.getId();

        boolean exceptionShouldThrownForGetNotDimensionMeasurable=false;
        try {
            ids.getDimensionById(factId);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForGetNotDimensionMeasurable=true;
        }
        Assert.assertTrue(exceptionShouldThrownForGetNotDimensionMeasurable);

        Dimension testDimensionB=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);

        boolean addInitProperty_BOOLEAN=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, true);
        Assert.assertTrue(addInitProperty_BOOLEAN);
        boolean addInitProperty_INT=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT,1000);
        Assert.assertTrue(addInitProperty_INT);
        boolean addInitProperty_SHORT=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT,(short)11);
        Assert.assertTrue(addInitProperty_SHORT);
        boolean addInitProperty_LONG=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG,(long)5500);
        Assert.assertTrue(addInitProperty_LONG);
        boolean addInitProperty_FLOAT=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT, (float) 445.66);
        Assert.assertTrue(addInitProperty_FLOAT);
        boolean addInitProperty_DOUBLE=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE,(double)456.777);
        Assert.assertTrue(addInitProperty_DOUBLE);
        Date testDate=new Date();
        boolean addInitProperty_DATE=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE, testDate);
        Assert.assertTrue(addInitProperty_DATE);
        boolean addInitProperty_STRING=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING,"this is a string");
        Assert.assertTrue(addInitProperty_STRING);
        byte[] byeArray="This is a str".getBytes();
        boolean addInitProperty_BINARY=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY,byeArray);
        Assert.assertTrue(addInitProperty_BINARY);
        boolean addInitProperty_BYTE=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE, (byte) 444);
        Assert.assertTrue(addInitProperty_BYTE);

        boolean addExistInitProperty=testDimensionB.setInitProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, false);
        Assert.assertFalse(addExistInitProperty);
        testDimensionB=ids.addDimension(testDimensionB);

        Property property_BOOLEAN= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNotNull(property_BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyType(), PropertyType.BOOLEAN);
        Assert.assertEquals(property_BOOLEAN.getPropertyValue(), true);

        Property property_INT= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertNotNull(property_INT);
        Assert.assertEquals(property_INT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertEquals(property_INT.getPropertyType(),PropertyType.INT);
        Assert.assertEquals(property_INT.getPropertyValue(), 1000);

        Property property_SHORT= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertNotNull(property_SHORT);
        Assert.assertEquals(property_SHORT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertEquals(property_SHORT.getPropertyType(),PropertyType.SHORT);
        Assert.assertEquals(property_SHORT.getPropertyValue(), (short) 11);

        Property property_LONG= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertNotNull(property_LONG);
        Assert.assertEquals(property_LONG.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertEquals(property_LONG.getPropertyType(),PropertyType.LONG);
        Assert.assertEquals(property_LONG.getPropertyValue(), (long) 5500);

        Property property_FLOAT= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertNotNull(property_FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyType(),PropertyType.FLOAT);
        Assert.assertEquals(property_FLOAT.getPropertyValue(), (float) 445.66);

        Property property_DOUBLE= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertNotNull(property_DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyType(),PropertyType.DOUBLE);
        Assert.assertEquals(property_DOUBLE.getPropertyValue(), (double) 456.777);

        Property property_DATE= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertNotNull(property_DATE);
        Assert.assertEquals(property_DATE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(property_DATE.getPropertyType(),PropertyType.DATE);
        Assert.assertEquals(property_DATE.getPropertyValue(), testDate);

        Property property_STRING= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertNotNull(property_STRING);
        Assert.assertEquals(property_STRING.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(property_STRING.getPropertyType(),PropertyType.STRING);
        Assert.assertEquals(property_STRING.getPropertyValue(), "this is a string");

        Property property_BINARY= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertNotNull(property_BINARY);
        Assert.assertEquals(property_BINARY.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertEquals(property_BINARY.getPropertyType(),PropertyType.BINARY);
        MeasurableContentHelper mch=DiscoverEngineComponentFactory.getMeasurableContentHelper();
        byte[] propertyContent=mch.retrieveBinaryPropertyContent(property_BINARY);
        Assert.assertEquals(propertyContent,byeArray);

        Property property_BYTE= testDimensionB.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertNotNull(property_BYTE);
        Assert.assertEquals(property_BYTE.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(property_BYTE.getPropertyType(),PropertyType.BYTE);
        Assert.assertEquals(property_BYTE.getPropertyValue(),(byte) 444);

        ids.removeDimension(testDimensionB.getId());

        ids.removeFact(factId);
        ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceDimensionFunc" })
    public void testSpaceRelationFunc()throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Fact fromFact=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        Fact toFact=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        fromFact=ids.addFact(fromFact);
        toFact=ids.addFact(toFact);
        String fromFactId=fromFact.getId();
        String toFactId=toFact.getId();

        boolean exceptionShouldThrownForAddNotExistRelationType=false;
        try {
            ids.addDirectionalFactRelation(fromFact,toFact,UnitTestConfigInfo.unitTestRootRelationTypeA,false);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistRelationType);

        ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);

        Relation firstRelation=ids.addDirectionalFactRelation(fromFact, toFact, UnitTestConfigInfo.unitTestRootRelationTypeA, false);
        Assert.assertNotNull(firstRelation);
        Assert.assertEquals(firstRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertEquals(firstRelation.getFromRelationable().getId(), fromFactId);
        Assert.assertEquals(firstRelation.getToRelationable().getId(),toFactId);

        Relation secondRelation=ids.addDirectionalFactRelation(fromFact, toFact, UnitTestConfigInfo.unitTestRootRelationTypeA, true);
        Assert.assertNotNull(secondRelation);
        Assert.assertEquals(secondRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertEquals(secondRelation.getFromRelationable().getId(), fromFactId);
        Assert.assertEquals(secondRelation.getToRelationable().getId(), toFactId);

        boolean exceptionShouldThrownForAddUnrepeatableRelation=false;
        try {
            ids.addDirectionalFactRelation(fromFact,toFact,UnitTestConfigInfo.unitTestRootRelationTypeA,false);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddUnrepeatableRelation=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddUnrepeatableRelation);

        Relation testGetByIdA=ids.getRelationById(firstRelation.getId());
        Assert.assertNotNull(testGetByIdA);
        Assert.assertEquals(testGetByIdA.getId(), firstRelation.getId());
        Assert.assertEquals(testGetByIdA.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);

        Relation testGetByIdB=ids.getRelationById(secondRelation.getId());
        Assert.assertNotNull(testGetByIdB);
        Assert.assertEquals(testGetByIdB.getId(), secondRelation.getId());
        Assert.assertEquals(testGetByIdB.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);

        String firstRelationId=firstRelation.getId();

        boolean removeRelationAResult=ids.removeRelation(firstRelation.getId());
        Assert.assertTrue(removeRelationAResult);
        boolean removeRelationBResult=ids.removeRelation(secondRelation.getId());
        Assert.assertTrue(removeRelationBResult);

        testGetByIdA=ids.getRelationById(firstRelationId);
        Assert.assertNull(testGetByIdA);

        boolean exceptionShouldThrownForDeleteNotExistRelation=false;
        try {
            ids.removeRelation(firstRelationId);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForDeleteNotExistRelation=true;
        }
        Assert.assertTrue(exceptionShouldThrownForDeleteNotExistRelation);

        ids.removeFact(fromFactId);
        ids.removeFact(toFactId);
        ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testSpaceRelationFunc" })
    public void testSpaceDimensionFactActionFunc()throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);

        Fact factA=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        factA=ids.addFact(factA);

        Dimension dimensionA=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        dimensionA=ids.addDimension(dimensionA);

        Relation attachFactToDimensionRelation=ids.attachFactToDimension(factA.getId(), dimensionA.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(attachFactToDimensionRelation);
        Assert.assertEquals(attachFactToDimensionRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(attachFactToDimensionRelation.getFromRelationable());
        Assert.assertNotNull(attachFactToDimensionRelation.getToRelationable());
        Assert.assertEquals(attachFactToDimensionRelation.getFromRelationable().getId(), factA.getId());
        Assert.assertEquals(attachFactToDimensionRelation.getToRelationable().getId(), dimensionA.getId());

        Relation attachFactToDimensionRelationAgain=ids.attachFactToDimension(factA.getId(), dimensionA.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(attachFactToDimensionRelationAgain);
        Assert.assertEquals(attachFactToDimensionRelationAgain.getId(), attachFactToDimensionRelation.getId());

        boolean exceptionShouldThrownForAddNotExistRelationType=false;
        try {
            ids.attachFactToDimension(factA.getId(), dimensionA.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA+"NotExist");
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistRelationType);

        boolean exceptionShouldThrownForAddNotExistFact=false;
        try {
            ids.attachFactToDimension(factA.getId()+"NotExist", dimensionA.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistFact=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistFact);

        boolean exceptionShouldThrownForAddNotExistDimension=false;
        try {
            ids.attachFactToDimension(factA.getId(), dimensionA.getId()+"NotExist", UnitTestConfigInfo.unitTestRootRelationTypeA);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistDimension=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistDimension);

        boolean exceptionShouldThrownForAddNotMatchedDataType=false;
        try {
            ids.attachFactToDimension(dimensionA.getId(), factA.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotMatchedDataType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotMatchedDataType);

        Fact factB=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        factB=ids.addFact(factB);

        Dimension dimensionB=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        dimensionB=ids.addDimension(dimensionB);

        Relation connectDimensionWithFactRelation=ids.connectDimensionWithFact(dimensionB.getId(), factB.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(connectDimensionWithFactRelation);
        Assert.assertEquals(connectDimensionWithFactRelation.getType(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(connectDimensionWithFactRelation.getFromRelationable());
        Assert.assertNotNull(connectDimensionWithFactRelation.getToRelationable());
        Assert.assertEquals(connectDimensionWithFactRelation.getFromRelationable().getId(), dimensionB.getId());
        Assert.assertEquals(connectDimensionWithFactRelation.getToRelationable().getId(), factB.getId());

        Relation connectDimensionWithFactRelationAgain=ids.connectDimensionWithFact(dimensionB.getId(),factB.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(connectDimensionWithFactRelationAgain);
        Assert.assertEquals(connectDimensionWithFactRelationAgain.getId(), connectDimensionWithFactRelation.getId());

        exceptionShouldThrownForAddNotExistRelationType=false;
        try {
            ids.connectDimensionWithFact(dimensionB.getId(), factB.getId(), UnitTestConfigInfo.unitTestRootRelationTypeA+"NotExist");
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistRelationType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistRelationType);

        exceptionShouldThrownForAddNotExistDimension=false;
        try {
            ids.connectDimensionWithFact(dimensionB.getId()+ "NotExist", factB.getId() , UnitTestConfigInfo.unitTestRootRelationTypeA);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistDimension=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistDimension);

        exceptionShouldThrownForAddNotExistFact=false;
        try {
            ids.connectDimensionWithFact(dimensionB.getId(), factB.getId()+ "NotExist", UnitTestConfigInfo.unitTestRootRelationTypeA );
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotExistFact=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotExistFact);

        exceptionShouldThrownForAddNotMatchedDataType=false;
        try {
            ids.connectDimensionWithFact(factB.getId(), dimensionB.getId() + "NotExist", UnitTestConfigInfo.unitTestRootRelationTypeA);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddNotMatchedDataType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddNotMatchedDataType);

        ids.removeRelation(attachFactToDimensionRelation.getId());
        ids.removeRelation(connectDimensionWithFactRelation.getId());
        ids.removeFact(factA.getId());
        ids.removeDimension(dimensionA.getId());
        ids.removeFact(factB.getId());
        ids.removeDimension(dimensionB.getId());
        ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeA)){
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        }
        if(ids.hasFactType(UnitTestConfigInfo.unitTestRootFactTypeB)){
            ids.removeFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB)){
            ids.removeDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeB);
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
