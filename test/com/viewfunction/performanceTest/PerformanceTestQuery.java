package com.viewfunction.performanceTest;

import com.viewfnction.infoDiscoverEngine.dataMart.Fact;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering.BetweenFilteringItem;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;

import java.util.Date;
import java.util.List;

public class PerformanceTestQuery {
    public static void main(String[] args){
        Date startDate=new Date();
        InfoDiscoverSpace ids=null;
        try{
            ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace("PerformanceTestDB");
            performQuery(ids);
        } catch (InfoDiscoveryEngineException e) {
            e.printStackTrace();
        }
        finally {
            if (ids != null) {
                ids.closeSpace();
            }
        }
        Date finishDate=new Date();
        System.out.println("===============================");
        System.out.println("Start at: "+startDate.toString());
        System.out.println("Finish at: "+finishDate.toString());
        System.out.println("===============================");
    }

    public static void performQuery(InfoDiscoverSpace ids) throws InfoDiscoveryEngineException{
        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);
        ExploreParameters ep=new ExploreParameters();
        ep.setType("unitTestRootFactTypeA");
        //ep.setResultNumber(50000);
        //ep.setDefaultFilteringItem(new BetweenFilteringItem("factTestProp_B", 1, 9910));
        //ep.setStartPage(52);
        //ep.setEndPage(53);
        ep.setPageSize(300);

        ep.setDefaultFilteringItem(new EqualFilteringItem("factTestProp_B", 500));

        List<Fact> factList = ie.discoverFacts(ep);
        System.out.println(factList.size());
        System.out.println(factList.get(0).getId());

    }
}
