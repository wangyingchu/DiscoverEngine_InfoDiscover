package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.infoDiscoverEngine.util.helper.MeasurableContentHelper;
import com.infoDiscover.infoDiscoverEngine.util.helperImpl.OrientDBMeasurableContentHelperImpl;
import org.testng.Assert;
import org.testng.annotations.*;

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
    public void getMeasurableContentHelper(){
        MeasurableContentHelper mch=DiscoverEngineComponentFactory.getMeasurableContentHelper();
        Assert.assertNotNull(mch);
        Assert.assertTrue(mch instanceof OrientDBMeasurableContentHelperImpl);
    }

    @Test(dependsOnMethods = { "getMeasurableContentHelper" })
    public void testExceptions() throws InfoDiscoveryEngineException{
        InfoDiscoveryEngineRuntimeException re= InfoDiscoveryEngineException.getRuntimeException("RuntimeException");
        Assert.assertNotNull(re);
        InfoDiscoveryEngineDataMartException de=InfoDiscoveryEngineException.getDataMartException("DataMartException");
        Assert.assertNotNull(de);
        InfoDiscoveryEngineInfoExploreException iee=InfoDiscoveryEngineException.getInfoExploreException("InfoExploreException");
        Assert.assertNotNull(iee);
    }
}
