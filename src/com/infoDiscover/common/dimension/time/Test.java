package com.infoDiscover.common.dimension.time;

import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.infoDiscover.solution.arch.database.DatabaseManager;
import com.infoDiscover.common.dimension.time.dimension.YearDimensionVO;
import com.infoDiscover.common.dimension.time.manager.TimeDimensionManager;
import com.infoDiscover.common.dimension.time.analytics.TimeAnalytics;
import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.DimensionsFilter;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class Test {
    public static void main(String[] args) throws InfoDiscoveryEngineInfoExploreException,
            InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineDataMartException {

        // get fact 2018-2-1
        TimeDimensionManager manager = new TimeDimensionManager();
        InfoDiscoverSpace ids = DatabaseManager.getInfoDiscoverSpace();

        InformationExplorer ie = ids.getInformationExplorer();
        ExploreParameters ep = new ExploreParameters();
        ep.setType("Test");

        ep.addFilteringItem(new EqualFilteringItem("year", 2018), ExploreParameters
                .FilteringLogic.AND);

        ep.addFilteringItem(new EqualFilteringItem("month", 2), ExploreParameters
                .FilteringLogic.AND);
        ep.addFilteringItem(new EqualFilteringItem("day", 1), ExploreParameters
                .FilteringLogic.AND);

        List<Fact> factList = ie.discoverFacts(ep);
        System.out.println("factlist: " + factList.size());

        String factId = factList.get(0).getId();

        System.out.println("factId: " + factId);


        // get dimension 2018-2-1
        ExploreParameters ep2 = new ExploreParameters();
        ep2.setType("d");

        ep2.addFilteringItem(new EqualFilteringItem("y", 2018), ExploreParameters
                .FilteringLogic.AND);

        ep2.addFilteringItem(new EqualFilteringItem("m", 2), ExploreParameters
                .FilteringLogic.AND);
        ep2.addFilteringItem(new EqualFilteringItem("d", 1), ExploreParameters
                .FilteringLogic.AND);

        List<Dimension> dimensionList = ie.discoverDimensions(ep2);
        System.out.println("dimensionList: " + dimensionList.size());

        String dimensionId = dimensionList.get(0).getId();
        System.out.println("dimensionId: " + dimensionId);

        if (!ids.hasRelationType("created")) {
            ids.addRelationType("created");
        }
        Relation r = ids.attachFactToDimension(factId, dimensionId, "created");


        // get fact 2018-2
        ExploreParameters ep3 = new ExploreParameters();
        ep3.setType("Test");

        ep3.addFilteringItem(new EqualFilteringItem("year", 2018), ExploreParameters
                .FilteringLogic.AND);

        ep3.addFilteringItem(new EqualFilteringItem("month", 2), ExploreParameters
                .FilteringLogic.AND);

        List<Fact> factList2 = ie.discoverFacts(ep3);
        System.out.println("factlist2: " + factList2.size());

        String factId2 = factList2.get(0).getId();

        System.out.println("factId: " + factId2);


        // get dimension 2018-2
        ExploreParameters ep4 = new ExploreParameters();
        ep4.setType("m");

        ep4.addFilteringItem(new EqualFilteringItem("y", 2018), ExploreParameters
                .FilteringLogic.AND);

        ep4.addFilteringItem(new EqualFilteringItem("m", 2), ExploreParameters
                .FilteringLogic.AND);

        List<Dimension> dimensionList4 = ie.discoverDimensions(ep4);
        System.out.println("dimensionList: " + dimensionList4.size());

        String dimensionId4 = dimensionList4.get(0).getId();
        System.out.println("dimensionId4: " + dimensionId4);

        if (!ids.hasRelationType("created")) {
            ids.addRelationType("created");
        }
        Relation r2 = ids.attachFactToDimension(factId2, dimensionId4, "created");

        // query with 2018-2
        List<DimensionsFilter> dfl = new ArrayList<>();
        DimensionsFilter df2 = new DimensionsFilter();
        df2.addDimensionId(dimensionId4);
        df2.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
        df2.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        df2.setRelationDirection(RelationDirection.FROM);
        df2.setRelationType("created");
        dfl.add(df2);

//        DimensionsFilter df22 = new DimensionsFilter();
//        df22.addDimensionId(dimensionId4);
//        df22.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
//        df22.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
//        df22.setRelationDirection(RelationDirection.FROM);
//        df22.setRelationType("inherit");
//        dfl.add(df22);

//
//        DimensionsFilter df1=new DimensionsFilter(testDimension1.getId(),RelationDirection.FROM,
//                UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,DimensionsFilter
// .FilteringLogic.OR,DimensionsFilter.RelationExistenceLogic.HAS);
//        dfl.add(df1);


//        ExploreParameters ep5 = new ExploreParameters();
//        ep5.setType("Test");
//        List<Fact> facts = ie.exploreFactsByRelatedDimensions(dfl, ep5);
//        System.out.println("facts.count: " + facts.size());

        String sql = "select expand(in('ID_RELATION_created')) from id_dimension_m where y=2018 " +
                "and m=2 ";
        List<Relationable> facts = new TimeAnalytics().drillDownByYear(ids, new YearDimensionVO
                ("test" +
                TimeDimensionConstants.YEAR,2018), "created");

        System.out.println("facts:" + facts.size());
        ids.closeSpace();
        System.out.println("id: " + facts.get(0).getId());


    }
}
