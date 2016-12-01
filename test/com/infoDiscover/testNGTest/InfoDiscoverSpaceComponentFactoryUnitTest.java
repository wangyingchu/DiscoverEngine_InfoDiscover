package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.config.PropertyHandler;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticHelper;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticMetrics;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBDiscoverSpaceStatisticHelperImpl;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBMeasurableContentHelperImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class InfoDiscoverSpaceComponentFactoryUnitTest {

    @BeforeTest
    public void initData(){
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceComponentFactoryUnitTest");
        System.out.println("--------------------------------------------------");
    }

    @Test
    public void testConnectInfoDiscoverSpace() {
        InfoDiscoverSpace idsNotExist=DiscoverEngineComponentFactory.
                connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName + "NotExist");
        Assert.assertNull(idsNotExist);
        InfoDiscoverSpace ids=DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        Assert.assertNotNull(ids);
        String spaceName=ids.getSpaceName();
        Assert.assertEquals(spaceName, UnitTestConfigInfo.unitTestSpaceName);
        ids.closeSpace();

        InfoDiscoverSpace nTxIsNotExist=DiscoverEngineComponentFactory.
                connectNoTransactionInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName + "NotExist");
        Assert.assertNull(nTxIsNotExist);
        InfoDiscoverSpace noTrIds=DiscoverEngineComponentFactory.connectNoTransactionInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        Assert.assertNotNull(noTrIds);
        String noTrSpaceName=noTrIds.getSpaceName();
        Assert.assertEquals(noTrSpaceName, UnitTestConfigInfo.unitTestSpaceName);
        noTrIds.closeSpace();
    }

    @Test(dependsOnMethods = { "testConnectInfoDiscoverSpace" })
    public void testConnectInfoDiscoverAdminSpace() {
        InfoDiscoverAdminSpace idsNotExist=DiscoverEngineComponentFactory.
                connectInfoDiscoverAdminSpace(UnitTestConfigInfo.unitTestSpaceName + "NotExist");
        Assert.assertNull(idsNotExist);
        InfoDiscoverAdminSpace ids=DiscoverEngineComponentFactory.connectInfoDiscoverAdminSpace(UnitTestConfigInfo.unitTestSpaceName);
        Assert.assertNotNull(ids);
        String spaceName=ids.getSpaceName();
        Assert.assertEquals(spaceName, UnitTestConfigInfo.unitTestSpaceName);
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testConnectInfoDiscoverAdminSpace" })
    public void testCreateFact(){
        Fact factA=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeA);
        Assert.assertNotNull(factA);
        Assert.assertEquals(factA.getType(), UnitTestConfigInfo.unitTestRootFactTypeA);

        Fact factB=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestRootFactTypeB);
        Assert.assertNotNull(factB);
        Assert.assertEquals(factB.getType(), UnitTestConfigInfo.unitTestRootFactTypeB);

        Assert.assertNotSame(factB.getType(), factA.getType());
    }

    @Test(dependsOnMethods = { "testCreateFact" })
    public void testCreateDimension(){
        Dimension dimensionA=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeA);
        Assert.assertNotNull(dimensionA);
        Assert.assertEquals(dimensionA.getType(),UnitTestConfigInfo.unitTestRootDimensionTypeA);

        Dimension dimensionB=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNotNull(dimensionB);
        Assert.assertEquals(dimensionB.getType(), UnitTestConfigInfo.unitTestRootDimensionTypeB);
        Assert.assertNotSame(dimensionB.getType(), dimensionA.getType());
    }

    @Test(dependsOnMethods = { "testCreateDimension" })
    public void testGetMeasurableContentHelper(){
        MeasurableContentHelper mch=DiscoverEngineComponentFactory.getMeasurableContentHelper();
        Assert.assertNotNull(mch);
        Assert.assertTrue(mch instanceof OrientDBMeasurableContentHelperImpl);
    }

    @Test(dependsOnMethods = { "testGetMeasurableContentHelper" })
    public void testExceptions() throws InfoDiscoveryEngineException{
        InfoDiscoveryEngineRuntimeException re= InfoDiscoveryEngineException.getRuntimeException("RuntimeException");
        Assert.assertNotNull(re);
        InfoDiscoveryEngineDataMartException de=InfoDiscoveryEngineException.getDataMartException("DataMartException");
        Assert.assertNotNull(de);
        InfoDiscoveryEngineInfoExploreException iee=InfoDiscoveryEngineException.getInfoExploreException("InfoExploreException");
        Assert.assertNotNull(iee);
    }

    @Test(dependsOnMethods = { "testExceptions" })
    public void testSpaceDataOperationData() throws InfoDiscoveryEngineException{
        Assert.assertTrue(DiscoverEngineComponentFactory.checkDiscoverSpaceExistence(UnitTestConfigInfo.unitTestSpaceName));
        Assert.assertFalse(DiscoverEngineComponentFactory.checkDiscoverSpaceExistence(UnitTestConfigInfo.unitTestSpaceName + "NotExist"));

        List<String> spaceNameList=DiscoverEngineComponentFactory.getDiscoverSpacesListInEngine();
        Assert.assertTrue(spaceNameList.size()>=1);
        boolean hasUnitTestSpace=false;
        for(String spaceName:spaceNameList){
            if(spaceName.equals(UnitTestConfigInfo.unitTestSpaceName)){
                hasUnitTestSpace=true;
            }
        }
        Assert.assertTrue(hasUnitTestSpace);
        Assert.assertFalse(DiscoverEngineComponentFactory.createInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName));
        Assert.assertTrue(DiscoverEngineComponentFactory.createInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"NotExist"));
        Assert.assertTrue(DiscoverEngineComponentFactory.deleteInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"NotExist"));
        Assert.assertFalse(DiscoverEngineComponentFactory.deleteInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"NotExist"));
    }

    @Test(dependsOnMethods = { "testSpaceDataOperationData" })
    public void testDiscoverSpaceStatisticHelper() throws InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        Assert.assertTrue(DiscoverEngineComponentFactory.createInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test"));

        DiscoverSpaceStatisticHelper sh=DiscoverEngineComponentFactory.getDiscoverSpaceStatisticHelper();
        Assert.assertNotNull(sh);
        Assert.assertTrue(sh instanceof OrientDBDiscoverSpaceStatisticHelperImpl);

        DiscoverSpaceStatisticMetrics dssm = sh.getDiscoverSpaceStatisticMetrics(UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test");
        Assert.assertNotNull(dssm);
        Assert.assertNotNull(dssm.getDimensionsStatisticMetrics());
        Assert.assertNotNull(dssm.getFactsStatisticMetrics());
        Assert.assertNotNull(dssm.getRelationsStatisticMetrics());
        Assert.assertTrue(dssm.getSpaceDiskSize() >0);
        Assert.assertTrue(dssm.getSpaceFullDataCount() >0);
        Assert.assertEquals(dssm.getSpaceLocation(), PropertyHandler.getPropertyValue(PropertyHandler.DISCOVER_ENGINE_SERVICE_LOCATION)+UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test");
        Assert.assertEquals(dssm.getSpaceOwner(), PropertyHandler.getPropertyValue(PropertyHandler.DISCOVER_ENGINE_ADMIN_ACCOUNT));
        Assert.assertTrue(dssm.getSpaceRelationDataCount()==0);
        Assert.assertTrue(dssm.getSpaceDimensionDataCount()==0);
        Assert.assertTrue(dssm.getSpaceFactDataCount()==0);

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test");
        ids.addFactType("FACTTYPEForDSSHTest");
        ids.addDimensionType("DIMENSIONTYPEForDSSHTest");
        ids.addRelationType("RELATIONTYPEForDSSHTest");

        Fact fc1=DiscoverEngineComponentFactory.createFact("FACTTYPEForDSSHTest");
        Fact fc2=DiscoverEngineComponentFactory.createFact("FACTTYPEForDSSHTest");
        ids.addFact(fc1);
        ids.addFact(fc2);
        Dimension ds1=DiscoverEngineComponentFactory.createDimension("DIMENSIONTYPEForDSSHTest");
        ids.addDimension(ds1);
        ids.connectDimensionWithFact(ds1.getId(),fc1.getId(),"RELATIONTYPEForDSSHTest");
        ids.connectDimensionWithFact(ds1.getId(),fc2.getId(),"RELATIONTYPEForDSSHTest");

        dssm = sh.getDiscoverSpaceStatisticMetrics(UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test");
        Assert.assertTrue(dssm.getSpaceRelationDataCount()==2);
        Assert.assertTrue(dssm.getSpaceDimensionDataCount()==1);
        Assert.assertTrue(dssm.getSpaceFactDataCount()==2);

        Assert.assertEquals(dssm.getDimensionsStatisticMetrics().size(),1);
        Assert.assertEquals(dssm.getDimensionsStatisticMetrics().get(0).getDataTypeName(),"ID_DIMENSION_DIMENSIONTYPEForDSSHTest");
        Assert.assertEquals(dssm.getDimensionsStatisticMetrics().get(0).getTypeDataCount(),1);

        Assert.assertEquals(dssm.getFactsStatisticMetrics().size(),1);
        Assert.assertEquals(dssm.getFactsStatisticMetrics().get(0).getDataTypeName(),"ID_FACT_FACTTYPEForDSSHTest");
        Assert.assertEquals(dssm.getFactsStatisticMetrics().get(0).getTypeDataCount(),2);

        Assert.assertEquals(dssm.getRelationsStatisticMetrics().size(),1);
        Assert.assertEquals(dssm.getRelationsStatisticMetrics().get(0).getDataTypeName(),"ID_RELATION_RELATIONTYPEForDSSHTest");
        Assert.assertEquals(dssm.getRelationsStatisticMetrics().get(0).getTypeDataCount(),2);

        Assert.assertEquals(dssm.getFactsStatisticMetrics().size(),1);
        Assert.assertEquals(dssm.getRelationsStatisticMetrics().size(),1);

        ids.getRelationType("RELATIONTYPEForDSSHTest").removeContainedRelations();
        ids.getFactType("FACTTYPEForDSSHTest").removeContainedFaces();
        ids.getDimensionType("DIMENSIONTYPEForDSSHTest").removeContainedDimensions();
        ids.removeFactType("FACTTYPEForDSSHTest");
        ids.removeDimensionType("DIMENSIONTYPEForDSSHTest");
        ids.removeRelationType("RELATIONTYPEForDSSHTest");
        ids.closeSpace();
        Assert.assertTrue(DiscoverEngineComponentFactory.deleteInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName+"ForDSSH_Test"));
    }
}
