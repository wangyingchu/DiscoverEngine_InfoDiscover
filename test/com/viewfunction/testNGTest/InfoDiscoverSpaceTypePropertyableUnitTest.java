package com.viewfunction.testNGTest;

import com.viewfnction.infoDiscoverEngine.dataMart.*;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class InfoDiscoverSpaceTypePropertyableUnitTest {
    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceTypePropertyableUnitTest");
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
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        ids.closeSpace();
    }

    @Test
    public void testFactTypePropertyableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        FactType addFactTypeAResult=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(addFactTypeAResult);
        doTestForTypePropertyable(addFactTypeAResult);
        ids.closeSpace();

        InfoDiscoverAdminSpace idas=DiscoverEngineComponentFactory.connectInfoDiscoverAdminSpace(UnitTestConfigInfo.unitTestSpaceName);
        boolean removePropertyResult=idas.removeFactTypeProperty(UnitTestConfigInfo.unitTestRootFactTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        Assert.assertTrue(removePropertyResult);

        boolean exceptionShouldThrownForRemoveNotExistProperty=false;
        try {
            idas.removeFactTypeProperty(UnitTestConfigInfo.unitTestRootFactTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistProperty);

        boolean exceptionShouldThrownForRemoveNotExistType=false;
        try {
            idas.removeFactTypeProperty(UnitTestConfigInfo.unitTestRootFactTypeA+"NotExist", UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistType);
        idas.closeSpace();

        ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        addFactTypeAResult=ids.getFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(addFactTypeAResult);
        doTestAfterRemoveProperty(addFactTypeAResult);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testFactTypePropertyableFunc" })
    public void testDimensionTypePropertyableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        DimensionType addDimensionTypeAResult=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(addDimensionTypeAResult);
        doTestForTypePropertyable(addDimensionTypeAResult);
        ids.closeSpace();

        InfoDiscoverAdminSpace idas=DiscoverEngineComponentFactory.connectInfoDiscoverAdminSpace(UnitTestConfigInfo.unitTestSpaceName);
        boolean removePropertyResult=idas.removeDimensionTypeProperty(UnitTestConfigInfo.unitTestRootDimensionTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        Assert.assertTrue(removePropertyResult);

        boolean exceptionShouldThrownForRemoveNotExistProperty=false;
        try {
            idas.removeDimensionTypeProperty(UnitTestConfigInfo.unitTestRootDimensionTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistProperty);

        boolean exceptionShouldThrownForRemoveNotExistType=false;
        try {
            idas.removeDimensionTypeProperty(UnitTestConfigInfo.unitTestRootDimensionTypeA + "NotExist", UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistType);
        idas.closeSpace();

        ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        addDimensionTypeAResult=ids.getDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(addDimensionTypeAResult);
        doTestAfterRemoveProperty(addDimensionTypeAResult);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDimensionTypePropertyableFunc" })
    public void testRelationTypePropertyableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        RelationType addRelationTypeResult=ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(addRelationTypeResult);
        doTestForTypePropertyable(addRelationTypeResult);
        ids.closeSpace();

        InfoDiscoverAdminSpace idas=DiscoverEngineComponentFactory.connectInfoDiscoverAdminSpace(UnitTestConfigInfo.unitTestSpaceName);
        boolean removePropertyResult=idas.removeRelationTypeProperty(UnitTestConfigInfo.unitTestRootRelationTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        Assert.assertTrue(removePropertyResult);

        boolean exceptionShouldThrownForRemoveNotExistProperty=false;
        try {
            idas.removeRelationTypeProperty(UnitTestConfigInfo.unitTestRootRelationTypeA, UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistProperty);
        boolean exceptionShouldThrownForRemoveNotExistType=false;
        try {
            idas.removeRelationTypeProperty(UnitTestConfigInfo.unitTestRootDimensionTypeA+"NotExist", UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistType);
        idas.closeSpace();

        ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        addRelationTypeResult=ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(addRelationTypeResult);
        doTestAfterRemoveProperty(addRelationTypeResult);
        ids.closeSpace();
    }

    private void doTestAfterRemoveProperty(TypePropertyable typePropertyable) throws InfoDiscoveryEngineRuntimeException{
        List<TypeProperty> typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertNotNull(typePropertyList);
        Assert.assertEquals(typePropertyList.size(), 9);

        List<String> typePropertyNames=typePropertyable.getTypePropertyNames();
        Assert.assertNotNull(typePropertyNames);
        Assert.assertEquals(typePropertyNames.size(), 9);

        Assert.assertFalse(typePropertyable.hasTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE));

        boolean exceptionShouldThrownForGetNotExistProperty=false;
        try {
            typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForGetNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForGetNotExistProperty);
    }

    private void doTestForTypePropertyable(TypePropertyable typePropertyable) throws InfoDiscoveryEngineRuntimeException{
        List<TypeProperty> typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertNotNull(typePropertyList);
        Assert.assertEquals(typePropertyList.size(), 0);

        List<String> typePropertyNames=typePropertyable.getTypePropertyNames();
        Assert.assertNotNull(typePropertyNames);
        Assert.assertEquals(typePropertyNames.size(), 0);

        TypeProperty booleanProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_BOOLEAN, PropertyType.BOOLEAN);
        Assert.assertNotNull(booleanProperty);
        Assert.assertEquals(booleanProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_BOOLEAN);
        Assert.assertEquals(booleanProperty.getPropertyType(), PropertyType.BOOLEAN);

        typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertEquals(typePropertyList.size(), 1);

        TypeProperty intProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_INT, PropertyType.INT);
        Assert.assertNotNull(intProperty);
        TypeProperty shortProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_SHORT, PropertyType.SHORT);
        Assert.assertNotNull(shortProperty);
        TypeProperty longProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_LONG, PropertyType.LONG);
        Assert.assertNotNull(longProperty);

        typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertEquals(typePropertyList.size(), 4);
        typePropertyNames=typePropertyable.getTypePropertyNames();
        Assert.assertEquals(typePropertyNames.size(), 4);

        TypeProperty floatProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_FLOAT, PropertyType.FLOAT);
        Assert.assertNotNull(floatProperty);
        TypeProperty doubleProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE, PropertyType.DOUBLE);
        Assert.assertNotNull(doubleProperty);
        TypeProperty dateProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DATE, PropertyType.DATE);
        Assert.assertNotNull(dateProperty);
        TypeProperty stringProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_STRING, PropertyType.STRING);
        Assert.assertNotNull(stringProperty);
        typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertEquals(typePropertyList.size(), 8);
        TypeProperty binaryProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_BINARY, PropertyType.BINARY);
        Assert.assertNotNull(binaryProperty);
        TypeProperty byteProperty=typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_BYTE, PropertyType.BYTE);
        Assert.assertNotNull(byteProperty);
        typePropertyList=typePropertyable.getTypeProperties();
        Assert.assertEquals(typePropertyList.size(), 10);
        typePropertyNames=typePropertyable.getTypePropertyNames();
        Assert.assertEquals(typePropertyNames.size(), 10);

        for(String propertyName:typePropertyNames){
            boolean isTestPropertyName=propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BOOLEAN)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_INT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_SHORT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_LONG)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_FLOAT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_DATE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_STRING)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BINARY)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BYTE)?true:false;
            Assert.assertTrue(isTestPropertyName);
        }

        for(TypeProperty typeProperty:typePropertyList){
            String propertyName=typeProperty.getPropertyName();
            boolean isTestPropertyName=propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BOOLEAN)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_INT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_SHORT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_LONG)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_FLOAT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_DATE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_STRING)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BINARY)||
                    propertyName.equals(UnitTestConfigInfo.unitTestTypePropertyable_BYTE)?true:false;
            Assert.assertTrue(isTestPropertyName);
        }

        boolean exceptionShouldThrownForAddExistProperty=false;
        try {
            typePropertyable.addTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DATE, PropertyType.DATE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddExistProperty);

        TypeProperty intProperty_2=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_INT);
        Assert.assertNotNull(intProperty_2);
        Assert.assertEquals(intProperty_2.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_INT);
        Assert.assertEquals(intProperty_2.getPropertyType(), PropertyType.INT);

        boolean exceptionShouldThrownForGetNotExistProperty=false;
        try {
            typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_INT+"NotExist");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForGetNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForGetNotExistProperty);

        Assert.assertTrue(typePropertyable.hasTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE));
        Assert.assertFalse(typePropertyable.hasTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE + "NotExist"));

        TypeProperty floatTypeProperty=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_FLOAT);
        Assert.assertNotNull(floatTypeProperty);
        Assert.assertEquals(floatTypeProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_FLOAT);
        Assert.assertEquals(floatTypeProperty.getPropertyType(), PropertyType.FLOAT);

        TypeProperty dateTypeProperty=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_DATE);
        Assert.assertNotNull(dateTypeProperty);
        Assert.assertEquals(dateTypeProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_DATE);
        Assert.assertEquals(dateTypeProperty.getPropertyType(), PropertyType.DATE);

        TypeProperty byteTypeProperty=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_BYTE);
        Assert.assertNotNull(byteTypeProperty);
        Assert.assertEquals(byteTypeProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_BYTE);
        Assert.assertEquals(byteTypeProperty.getPropertyType(), PropertyType.BYTE);

        TypeProperty binaryTypeProperty=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_BINARY);
        Assert.assertNotNull(binaryTypeProperty);
        Assert.assertEquals(binaryTypeProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_BINARY);
        Assert.assertEquals(binaryTypeProperty.getPropertyType(), PropertyType.BINARY);

        TypeProperty stringTypeProperty=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_STRING);
        Assert.assertNotNull(stringTypeProperty);
        Assert.assertEquals(stringTypeProperty.getPropertyName(), UnitTestConfigInfo.unitTestTypePropertyable_STRING);
        Assert.assertEquals(stringTypeProperty.getPropertyType(), PropertyType.STRING);

        Assert.assertFalse(stringTypeProperty.isMandatory());
        Assert.assertTrue(stringTypeProperty.isNullable());
        Assert.assertFalse(stringTypeProperty.isReadOnly());
        stringTypeProperty.setMandatory(true);
        stringTypeProperty.setNullable(false);
        stringTypeProperty.setReadOnly(true);
        Assert.assertTrue(stringTypeProperty.isMandatory());
        Assert.assertFalse(stringTypeProperty.isNullable());
        Assert.assertTrue(stringTypeProperty.isReadOnly());

        TypeProperty stringTypeProperty2=typePropertyable.getTypePropertyType(UnitTestConfigInfo.unitTestTypePropertyable_STRING);
        Assert.assertTrue(stringTypeProperty2.isMandatory());
        Assert.assertFalse(stringTypeProperty2.isNullable());
        Assert.assertTrue(stringTypeProperty2.isReadOnly());

        boolean exceptionShouldThrownForRemoveNotExistProperty=false;
        try {
            typePropertyable.removeTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE + "NotExist");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistProperty);

        boolean exceptionShouldThrownForRemovePropertyFromType=false;
        try {
            typePropertyable.removeTypeProperty(UnitTestConfigInfo.unitTestTypePropertyable_DOUBLE);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemovePropertyFromType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemovePropertyFromType);
    }

    @AfterTest
    public void cleanTestData(){
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceTypePropertyableUnitTest");
        System.out.println("--------------------------------------------------");
    }
}
