package com.infoDiscover.common.time;

import com.infoDiscover.common.dimension.time.dimension.TimeDimension;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverAdminSpace;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.testNGTest.UnitTestConfigInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by sun.
 */
public class TimeDimensionUnitTest {

    @BeforeTest
    public void initData(){
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for TimeDimensionUnitTest");
        System.out.println("--------------------------------------------------");
        if(!DiscoverEngineComponentFactory.checkDiscoverSpaceExistence
                (TimeDimensionUnitTestConfigInfo.unitTestTimeSpaceName)){
            DiscoverEngineComponentFactory.createInfoDiscoverSpace(TimeDimensionUnitTestConfigInfo.unitTestTimeSpaceName);
        }
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
}
