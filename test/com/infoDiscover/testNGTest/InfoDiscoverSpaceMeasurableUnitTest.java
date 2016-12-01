package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoDiscoverSpaceMeasurableUnitTest {

    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceMeasurableUnitTest");
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
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA)){
            ids.getRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA).removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        }
        ids.closeSpace();
    }


    @Test
    public void testFactMeasurableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        FactType addFactTypeAResult=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(addFactTypeAResult);
        Fact testMeasurable=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        testMeasurable=ids.addFact(testMeasurable);
        Assert.assertNotNull(testMeasurable);

        doTestForMeasurable(testMeasurable);

        ids.removeFact(testMeasurable.getId());
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testFactMeasurableFunc" })
    public void testDimensionMeasurableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        DimensionType addDimensionTypeResult=ids.addDimensionType(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(addDimensionTypeResult);
        Assert.assertEquals(addDimensionTypeResult.getTypeName(), UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Dimension testMeasurable=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        testMeasurable=ids.addDimension(testMeasurable);
        Assert.assertNotNull(testMeasurable);

        doTestForMeasurable(testMeasurable);

        ids.removeDimension(testMeasurable.getId());
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDimensionMeasurableFunc" })
    public void testRelationMeasurableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        FactType addFactTypeBResult=ids.addFactType(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertNotNull(addFactTypeBResult);

        RelationType addRelationTypeResult=ids.addRelationType(UnitTestConfigInfo.unitTestRootRelationTypeA);
        Assert.assertNotNull(addRelationTypeResult);
        Assert.assertEquals(addRelationTypeResult.getTypeName(), UnitTestConfigInfo.unitTestRootRelationTypeA);
        Fact factA=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        factA=ids.addFact(factA);
        Assert.assertNotNull(factA);
        Fact factB=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        factB=ids.addFact(factB);
        Assert.assertNotNull(factB);
        Relation testMeasurable=ids.addDirectionalFactRelation(factA, factB, UnitTestConfigInfo.unitTestRootRelationTypeA, false);
        Assert.assertNotNull(testMeasurable);

        doTestForMeasurable(testMeasurable);

        ids.removeRelation(testMeasurable.getId());
        ids.removeFact(factA.getId());
        ids.removeFact(factB.getId());
        ids.closeSpace();
    }

    private void doTestForMeasurable(Measurable testMeasurable) throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException{
        List<String> propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 0);

        List<Property> propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 0);

        Property testPropertyBoolean=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNull(testPropertyBoolean);
        boolean hasBooleanProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertFalse(hasBooleanProperty);

        testPropertyBoolean=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, true);
        Assert.assertNotNull(testPropertyBoolean);
        Assert.assertEquals(testPropertyBoolean.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertEquals(testPropertyBoolean.getPropertyType(), PropertyType.BOOLEAN);
        Assert.assertEquals(((Boolean) testPropertyBoolean.getPropertyValue()).booleanValue(), true);

        testPropertyBoolean=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNotNull(testPropertyBoolean);
        Assert.assertEquals(testPropertyBoolean.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertEquals(testPropertyBoolean.getPropertyType(), PropertyType.BOOLEAN);
        Assert.assertEquals(((Boolean) testPropertyBoolean.getPropertyValue()).booleanValue(), true);

        testPropertyBoolean=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN,false);
        Assert.assertNotNull(testPropertyBoolean);
        Assert.assertEquals(testPropertyBoolean.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertEquals(testPropertyBoolean.getPropertyType(), PropertyType.BOOLEAN);
        Assert.assertEquals(((Boolean) testPropertyBoolean.getPropertyValue()).booleanValue(), false);

        testPropertyBoolean=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNotNull(testPropertyBoolean);
        hasBooleanProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertTrue(hasBooleanProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 1);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 1);

        Property testPropertyInt=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertNull(testPropertyInt);
        boolean hasIntProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertFalse(hasIntProperty);

        testPropertyInt=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT, 1500);
        Assert.assertNotNull(testPropertyInt);
        Assert.assertEquals(testPropertyInt.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertEquals(testPropertyInt.getPropertyType(), PropertyType.INT);
        Assert.assertEquals(((Integer) testPropertyInt.getPropertyValue()).intValue(), 1500);

        testPropertyInt=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertNotNull(testPropertyInt);
        Assert.assertEquals(testPropertyInt.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertEquals(testPropertyInt.getPropertyType(), PropertyType.INT);
        Assert.assertEquals(((Integer) testPropertyInt.getPropertyValue()).intValue(), 1500);

        testPropertyInt=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT,2500);
        Assert.assertNotNull(testPropertyInt);
        Assert.assertEquals(testPropertyInt.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertEquals(testPropertyInt.getPropertyType(), PropertyType.INT);
        Assert.assertEquals(((Integer) testPropertyInt.getPropertyValue()).intValue(), 2500);

        testPropertyInt=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertNotNull(testPropertyInt);
        hasIntProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_INT);
        Assert.assertTrue(hasIntProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 2);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 2);

        Property testPropertyShort=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertNull(testPropertyShort);
        boolean hasShortProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertFalse(hasShortProperty);

        testPropertyShort=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT, (short) 20);
        Assert.assertNotNull(testPropertyShort);
        Assert.assertEquals(testPropertyShort.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertEquals(testPropertyShort.getPropertyType(), PropertyType.SHORT);
        Assert.assertEquals(((Short) testPropertyShort.getPropertyValue()).shortValue(), (short) 20);

        testPropertyShort=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertNotNull(testPropertyShort);
        Assert.assertEquals(testPropertyShort.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertEquals(testPropertyShort.getPropertyType(), PropertyType.SHORT);
        Assert.assertEquals(((Short) testPropertyShort.getPropertyValue()).shortValue(), (short) 20);

        testPropertyShort=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT,(short)126);
        Assert.assertNotNull(testPropertyShort);
        Assert.assertEquals(testPropertyShort.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertEquals(testPropertyShort.getPropertyType(), PropertyType.SHORT);
        Assert.assertEquals(((Short) testPropertyShort.getPropertyValue()).shortValue(), (short) 126);

        testPropertyShort=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertNotNull(testPropertyShort);
        hasShortProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT);
        Assert.assertTrue(hasShortProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 3);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 3);

        Property testPropertyLong=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertNull(testPropertyLong);
        boolean hasLongProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertFalse(hasLongProperty);

        testPropertyLong=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG, (long) 5679000);
        Assert.assertNotNull(testPropertyLong);
        Assert.assertEquals(testPropertyLong.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertEquals(testPropertyLong.getPropertyType(), PropertyType.LONG);
        Assert.assertEquals(((Long) testPropertyLong.getPropertyValue()).longValue(), (long) 5679000);

        testPropertyLong=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertNotNull(testPropertyLong);
        Assert.assertEquals(testPropertyLong.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertEquals(testPropertyLong.getPropertyType(), PropertyType.LONG);
        Assert.assertEquals(((Long) testPropertyLong.getPropertyValue()).longValue(), (long) 5679000);

        testPropertyLong=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG,(long)57899000);
        Assert.assertNotNull(testPropertyLong);
        Assert.assertEquals(testPropertyLong.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertEquals(testPropertyLong.getPropertyType(), PropertyType.LONG);
        Assert.assertEquals(((Long) testPropertyLong.getPropertyValue()).longValue(), (long) 57899000);

        testPropertyLong=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertNotNull(testPropertyLong);
        hasLongProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_LONG);
        Assert.assertTrue(hasLongProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 4);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 4);

        Property testPropertyFloat=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertNull(testPropertyFloat);
        boolean hasFloatProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertFalse(hasFloatProperty);

        testPropertyFloat=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT, (float) 55.77);
        Assert.assertNotNull(testPropertyFloat);
        Assert.assertEquals(testPropertyFloat.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(testPropertyFloat.getPropertyType(), PropertyType.FLOAT);
        Assert.assertEquals(((Float) testPropertyFloat.getPropertyValue()).floatValue(), (float) 55.77);

        testPropertyFloat=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(((Float) testPropertyFloat.getPropertyValue()).floatValue(), (float) 55.77);
        Assert.assertNotNull(testPropertyFloat);
        Assert.assertEquals(testPropertyFloat.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(testPropertyFloat.getPropertyType(), PropertyType.FLOAT);

        testPropertyFloat=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT,(float)57.89);
        Assert.assertNotNull(testPropertyFloat);
        Assert.assertEquals(testPropertyFloat.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertEquals(testPropertyFloat.getPropertyType(), PropertyType.FLOAT);
        Assert.assertEquals(((Float) testPropertyFloat.getPropertyValue()).floatValue(), (float) 57.89);

        testPropertyFloat=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertNotNull(testPropertyFloat);
        hasFloatProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT);
        Assert.assertTrue(hasFloatProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 5);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 5);

        Property testPropertyDouble=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertNull(testPropertyDouble);
        boolean hasDoubleProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertFalse(hasDoubleProperty);

        testPropertyDouble=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE, (double) 345678000);
        Assert.assertNotNull(testPropertyDouble);
        Assert.assertEquals(testPropertyDouble.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(testPropertyDouble.getPropertyType(), PropertyType.DOUBLE);
        Assert.assertEquals(((Double) testPropertyDouble.getPropertyValue()).doubleValue(), (double) 345678000);

        testPropertyDouble=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(((Double) testPropertyDouble.getPropertyValue()).doubleValue(), (double) 345678000);
        Assert.assertNotNull(testPropertyDouble);
        Assert.assertEquals(testPropertyDouble.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(testPropertyDouble.getPropertyType(), PropertyType.DOUBLE);

        testPropertyDouble=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE,(double)5567333);
        Assert.assertNotNull(testPropertyDouble);
        Assert.assertEquals(testPropertyDouble.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertEquals(testPropertyDouble.getPropertyType(), PropertyType.DOUBLE);
        Assert.assertEquals(((Double) testPropertyDouble.getPropertyValue()).doubleValue(), (double) 5567333);

        testPropertyDouble=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertNotNull(testPropertyDouble);
        hasDoubleProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE);
        Assert.assertTrue(hasDoubleProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 6);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 6);

        Property testPropertyDate=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertNull(testPropertyDate);
        boolean hasDateProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertFalse(hasDateProperty);

        Date dateA=new Date();
        testPropertyDate=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE, dateA);
        Assert.assertNotNull(testPropertyDate);
        Assert.assertEquals(testPropertyDate.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(testPropertyDate.getPropertyType(), PropertyType.DATE);
        Assert.assertEquals(((Date) testPropertyDate.getPropertyValue()), dateA);

        testPropertyDate=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(((Date) testPropertyDate.getPropertyValue()), dateA);
        Assert.assertNotNull(testPropertyDate);
        Assert.assertEquals(testPropertyDate.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(testPropertyDate.getPropertyType(), PropertyType.DATE);

        Date dateB=new Date();
        testPropertyDate=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE,dateB);
        Assert.assertNotNull(testPropertyDate);
        Assert.assertEquals(testPropertyDate.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertEquals(testPropertyDate.getPropertyType(), PropertyType.DATE);
        Assert.assertEquals(((Date) testPropertyDate.getPropertyValue()), dateB);

        testPropertyDate=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertNotNull(testPropertyDate);
        hasDateProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_DATE);
        Assert.assertTrue(hasDateProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 7);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 7);

        Property testPropertyString=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertNull(testPropertyString);
        boolean hasStringProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertFalse(hasStringProperty);

        testPropertyString=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING, "StringValue1");
        Assert.assertNotNull(testPropertyString);
        Assert.assertEquals(testPropertyString.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(testPropertyString.getPropertyType(), PropertyType.STRING);
        Assert.assertEquals(((String) testPropertyString.getPropertyValue()), "StringValue1");

        testPropertyString=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(((String) testPropertyString.getPropertyValue()), "StringValue1");
        Assert.assertNotNull(testPropertyString);
        Assert.assertEquals(testPropertyString.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(testPropertyString.getPropertyType(), PropertyType.STRING);

        testPropertyString=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING,"StringValue2");
        Assert.assertNotNull(testPropertyString);
        Assert.assertEquals(testPropertyString.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertEquals(testPropertyString.getPropertyType(), PropertyType.STRING);
        Assert.assertEquals(((String) testPropertyString.getPropertyValue()), "StringValue2");

        testPropertyString=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertNotNull(testPropertyString);
        hasStringProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_STRING);
        Assert.assertTrue(hasStringProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 8);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 8);

        Property testPropertyByte=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertNull(testPropertyByte);
        boolean hasByteProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertFalse(hasByteProperty);

        testPropertyByte=testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE, (byte) 55);
        Assert.assertNotNull(testPropertyByte);
        Assert.assertEquals(testPropertyByte.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(testPropertyByte.getPropertyType(), PropertyType.BYTE);
        Assert.assertEquals(((Byte) testPropertyByte.getPropertyValue()).byteValue(), (byte) 55);

        testPropertyByte=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(((Byte) testPropertyByte.getPropertyValue()).byteValue(), (byte) 55);
        Assert.assertNotNull(testPropertyByte);
        Assert.assertEquals(testPropertyByte.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(testPropertyByte.getPropertyType(), PropertyType.BYTE);

        testPropertyByte=testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE,(byte)66);
        Assert.assertNotNull(testPropertyByte);
        Assert.assertEquals(testPropertyByte.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertEquals(testPropertyByte.getPropertyType(), PropertyType.BYTE);
        Assert.assertEquals(((Byte) testPropertyByte.getPropertyValue()).byteValue(), (byte) 66);

        testPropertyByte=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertNotNull(testPropertyByte);
        hasByteProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE);
        Assert.assertTrue(hasByteProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 9);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 9);

        Property testPropertyBinary=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertNull(testPropertyBinary);
        boolean hasBinaryProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertFalse(hasBinaryProperty);

        MeasurableContentHelper mch=DiscoverEngineComponentFactory.getMeasurableContentHelper();
        mch.persistBinaryContentToProperty(testMeasurable, UnitTestConfigInfo.unitTestMeasurableProperty_BINARY, UnitTestConfigInfo.unitTestMeasurableProperty_newBinaryFileName);

        testPropertyBinary=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertNotNull(testPropertyBinary);
        Assert.assertEquals(testPropertyBinary.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertEquals(testPropertyBinary.getPropertyType(), PropertyType.BINARY);

        mch.retrievePropertyBinaryContent(testPropertyBinary, null, UnitTestConfigInfo.unitTestMeasurableProperty_retrievedBinaryFileName);
        File sourceFile=new File(UnitTestConfigInfo.unitTestMeasurableProperty_newBinaryFileName);
        Assert.assertNotNull(sourceFile);
        File retrievedFile=new File(UnitTestConfigInfo.unitTestMeasurableProperty_retrievedBinaryFileName);
        Assert.assertNotNull(retrievedFile);
        Assert.assertEquals(retrievedFile.length(), sourceFile.length());

        mch.updateBinaryContentOfProperty(testMeasurable, UnitTestConfigInfo.unitTestMeasurableProperty_BINARY, UnitTestConfigInfo.unitTestMeasurableProperty_updateBinaryFileName);

        testPropertyBinary=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertNotNull(testPropertyBinary);
        Assert.assertEquals(testPropertyBinary.getPropertyName(), UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertEquals(testPropertyBinary.getPropertyType(), PropertyType.BINARY);

        mch.retrievePropertyBinaryContent(testPropertyBinary, null, UnitTestConfigInfo.unitTestMeasurableProperty_updatedBinaryFileName);
        File updateFile=new File(UnitTestConfigInfo.unitTestMeasurableProperty_updateBinaryFileName);
        Assert.assertNotNull(updateFile);
        File updatedFile=new File(UnitTestConfigInfo.unitTestMeasurableProperty_updatedBinaryFileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFile.length(), updateFile.length());

        Assert.assertNotSame(updateFile.length(), sourceFile.length());

        hasBinaryProperty=testMeasurable.hasProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY);
        Assert.assertTrue(hasBinaryProperty);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 10);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 10);

        for(Property currentProperty:propertyList){
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.BOOLEAN);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_INT)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.INT);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.SHORT);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_LONG)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.LONG);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.FLOAT);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.DOUBLE);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_DATE)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.DATE);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_STRING)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.STRING);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.BINARY);
            }
            if(currentProperty.getPropertyName().equals(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE)){
                Assert.assertEquals(currentProperty.getPropertyType(), PropertyType.BYTE);
            }
        }

        for(String propertyName:propertyNameList){
            boolean isTestPropertyName=propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_INT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_SHORT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_LONG)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_FLOAT)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_DOUBLE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_DATE)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_STRING)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_BINARY)||
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_BYTE)?true:false;
            Assert.assertTrue(isTestPropertyName);
        }

        boolean exceptionShouldThrownForAddExistProperty=false;
        try {
            testMeasurable.addProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, true);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForAddExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForAddExistProperty);

        boolean exceptionShouldThrownForUpdateNotExistProperty=false;
        try {
            testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN + "NotExist", false);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForUpdateNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForUpdateNotExistProperty);

        boolean exceptionShouldThrownForUpdateExistPropertyWithNotMatchType=false;
        try {
            testMeasurable.updateProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN, "I'm not a boolean value");
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForUpdateExistPropertyWithNotMatchType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForUpdateExistPropertyWithNotMatchType);

        Map<String,Object> propertiesMap=new HashMap<String,Object>();
        propertiesMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupA, new Integer(500));
        propertiesMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupB, "String value data");
        propertiesMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupC, new Long(890000));
        propertiesMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_STRING, "this is exist property,so should not be handled");

        List<String> resultPropertyList=testMeasurable.addProperties(propertiesMap);
        Assert.assertNotNull(resultPropertyList);
        Assert.assertEquals(resultPropertyList.size(), 3);

        for(String propertyName:resultPropertyList){
            boolean isTestPropertyName=
                    propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_GroupA)||
                            propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_GroupB)||
                            propertyName.equals(UnitTestConfigInfo.unitTestMeasurableProperty_GroupC)?true:false;
            Assert.assertTrue(isTestPropertyName);
        }

        Property groupPropertyA=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupA);
        Assert.assertNotNull(groupPropertyA);
        Assert.assertEquals(groupPropertyA.getPropertyType(), PropertyType.INT);
        Assert.assertEquals(((Integer) groupPropertyA.getPropertyValue()).intValue(), (int) 500);

        Property groupPropertyB = testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupB);
        Assert.assertNotNull(groupPropertyB);
        Assert.assertEquals(groupPropertyB.getPropertyType(), PropertyType.STRING);
        Assert.assertEquals(groupPropertyB.getPropertyValue().toString(), "String value data");

        Property groupPropertyC=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupC);
        Assert.assertNotNull(groupPropertyC);
        Assert.assertEquals(groupPropertyC.getPropertyType(), PropertyType.LONG);
        Assert.assertEquals(((Long) groupPropertyC.getPropertyValue()).longValue(), (long) 890000);

        Map<String,Object> propertiesForUpdateMap=new HashMap<String,Object>();
        propertiesForUpdateMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupA, new Integer(1500));
        propertiesForUpdateMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupB + "NotExist", new Date());
        propertiesForUpdateMap.put(UnitTestConfigInfo.unitTestMeasurableProperty_GroupC, "change to not matched type");

        List<String> updateResultPropertyList=testMeasurable.updateProperties(propertiesForUpdateMap);
        Assert.assertNotNull(updateResultPropertyList);
        Assert.assertEquals(updateResultPropertyList.size(), 1);

        Assert.assertEquals(updateResultPropertyList.get(0), UnitTestConfigInfo.unitTestMeasurableProperty_GroupA);

        Property updatedProperty=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupA);
        Assert.assertNotNull(updatedProperty);
        Assert.assertEquals(updatedProperty.getPropertyType(), PropertyType.INT);
        Assert.assertEquals(((Integer) updatedProperty.getPropertyValue()).intValue(), (int) 1500);

        Property notExistProperty=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupB + "NotExist");
        Assert.assertNull(notExistProperty);

        Property notUpdatedProperty=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_GroupC);
        Assert.assertNotNull(notUpdatedProperty);
        Assert.assertEquals(notUpdatedProperty.getPropertyType(), PropertyType.LONG);
        Assert.assertEquals(((Long) notUpdatedProperty.getPropertyValue()).longValue(), (long) 890000);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 13);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 13);


        boolean removePropertyResult=testMeasurable.removeProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertTrue(removePropertyResult);

        propertyList= testMeasurable.getProperties();
        Assert.assertNotNull(propertyList);
        Assert.assertEquals(propertyList.size(), 12);

        propertyNameList=testMeasurable.getPropertyNames();
        Assert.assertNotNull(propertyNameList);
        Assert.assertEquals(propertyNameList.size(), 12);

        Property alreadyRemovedProperty=testMeasurable.getProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        Assert.assertNull(alreadyRemovedProperty);

        boolean exceptionShouldThrownForRemoveNotExistProperty=false;
        try {
            testMeasurable.removeProperty(UnitTestConfigInfo.unitTestMeasurableProperty_BOOLEAN);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForRemoveNotExistProperty=true;
        }
        Assert.assertTrue(exceptionShouldThrownForRemoveNotExistProperty);

        retrievedFile.delete();
        updatedFile.delete();
    }

    @AfterTest
    public void cleanTestData(){
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceMeasurableUnitTest");
        System.out.println("--------------------------------------------------");
    }
}
