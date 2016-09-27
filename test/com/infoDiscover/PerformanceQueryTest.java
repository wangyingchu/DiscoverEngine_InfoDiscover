package com.infoDiscover;

import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.Fact;
import com.infoDiscover.infoDiscoverEngine.dataMart.Relation;
import com.infoDiscover.infoDiscoverEngine.dataMart.RelationDirection;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.DimensionsFilter;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.RelationTypeFilter;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.BetweenFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangychu on 11/27/15.
 */
public class PerformanceQueryTest {

    public static void main(String[] args){
        InfoDiscoverSpace ids=null;
        try{
            ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace("PerformanceTestDB");

            InformationExplorer ie=ids.getInformationExplorer();
            Assert.assertNotNull(ie);
            ExploreParameters ep=new ExploreParameters();
            ep.setType("unitTestRootFactTypeA");
            ep.setResultNumber(50000);

            //

            ep.setDefaultFilteringItem(new BetweenFilteringItem("factTestProp_B", 9000, 9910));

            //ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("factTestProp_B", 7990));

        //    ep.setDefaultFilteringItem(new EqualFilteringItem("factTestProp_B", 600));

         //   ep.setDefaultFilteringItem(new EqualFilteringItem("factTestProp_D", "50000"));

           // ep.addFilteringItem(new EqualFilteringItem("factTestProp_C", 50),ExploreParameters.FilteringLogic.OR);



            System.out.println(new Date());
            List<Fact> factList = ie.discoverFacts(ep);
            System.out.println(factList.size());
            System.out.println(new Date());


            Fact targetFact=ids.getFactById("#13:1");
/*
            System.out.println(targetFact);

            System.out.println(targetFact.getAllRelations());

            Relation firstRelation=targetFact.getAllRelations().get(0);


            System.out.println(firstRelation.getType());


            System.out.println(firstRelation.getFromRelationable().getId());

            System.out.println(firstRelation.getToRelationable().getId());
*/

            /*
            System.out.println(new Date());
            List<Relationable> list= ie.discoverRelationables("SELECT EXPAND( in ) FROM(SELECT in(\"VF_RELATION_unitTestRootRelationTypeA\") FROM #18:0) LIMIT 10");
        //    System.out.println(list);


            Relationable r=list.get(0);

            System.out.println(r.getId());
           // System.out.println(r.getPropertyNames());


            System.out.println(new Date());
*/
//ep.setType(null);
         //   ep.setType("unitTestChildDimensionTypeAOfA");
            ep.setType(null);
            ep.setDefaultFilteringItem(null);
            ep.addRelatedRelationType("unitTestRootRelationTypeB");
            ep.addRelatedRelationType("unitTestChildRelationTypeAOfA");
            //ep.addRelatedRelationType("unitTestRootRelationTypeA");

List<String> ssss=new ArrayList<>();
            ssss.add("#16:0");
            ssss.add("#18:0");

            List<Relation> relList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TWO_WAY, ssss, ep);

            System.out.println(relList.size());
            System.out.println(new Date());

            System.out.println(new Date());

            System.out.println("+++++++++++++++++++++++++++++++++");

            List<String> fidList=new ArrayList<>();
            fidList.add("#14:19");
            fidList.add("#14:20");
            fidList.add("#14:21");
            fidList.add("#14:22");
            fidList.add("#14:23");
            fidList.add("#14:24");
            fidList.add("#14:25");
            fidList.add("#14:26");
            List<Dimension> dimLis=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, fidList, ep);

            System.out.println(dimLis.size());

            System.out.println(dimLis.get(0).getId());
            System.out.println(dimLis.get(0).getPropertyNames());
            System.out.println(dimLis.get(0).getType());
            System.out.println(new Date());



            ExploreParameters ep2=new ExploreParameters();

            List<RelationTypeFilter> dlist=new ArrayList<>();


            RelationTypeFilter dtf=new RelationTypeFilter();
            dtf.setTypeName("unitTestRootRelationTypeA");
            dtf.setFilteringLogic(RelationTypeFilter.FilteringLogic.AND);
            //dtf.setRelationCount();
            dtf.setRelationDirection(RelationDirection.FROM);
            dtf.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.HAS);
            dlist.add(dtf);


            RelationTypeFilter dtf1=new RelationTypeFilter();
            dtf1.setTypeName("unitTestRootRelationTypeB");
            dtf1.setFilteringLogic(RelationTypeFilter.FilteringLogic.AND);
            dtf1.setRelationDirection(RelationDirection.TO);
            dtf1.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.NOTHAS);
            dlist.add(dtf1);


/*
            RelationTypeFilter dtf2=new RelationTypeFilter();
            dtf2.setTypeName("yyyyy");
            dtf2.setFilteringLogic(RelationTypeFilter.FilteringLogic.OR);
            dtf2.setRelationDirection(RelationDirection.TWO_WAY);
            dtf2.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.NOTHAS);
            dlist.add(dtf2);


            RelationTypeFilter dtf3=new RelationTypeFilter();
            dtf3.setTypeName("uuuuuuu");
            dtf3.setFilteringLogic(RelationTypeFilter.FilteringLogic.AND);
            dtf3.setRelationDirection(RelationDirection.FROM);
            dtf3.setRelationCount(500);
            //dtf3.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.HAS);
            dlist.add(dtf3);

*/

            ep.setType("unitTestRootFactTypeA");

            ep.setType(null);

            System.out.println(new Date());
            List<Fact> factLis=ie.exploreFactsByRelatedRelationTypes(dlist, ep);

            System.out.println(factLis.size());
            System.out.println(new Date());


            List<DimensionsFilter> dimensionsFilters=new ArrayList<>();


            DimensionsFilter df1=new DimensionsFilter();
            df1.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
            df1.setRelationDirection(RelationDirection.TO);
            df1.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.NOTHAS);
            df1.setRelationType("cdcddd");
            df1.addDimensionId("#18:1");
            dimensionsFilters.add(df1);


            DimensionsFilter df2=new DimensionsFilter();
            df2.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
            df2.setRelationDirection(RelationDirection.TWO_WAY);
            df2.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
            df2.setRelationType(null);
            df2.addDimensionId("#18:1");
            dimensionsFilters.add(df2);


            DimensionsFilter df3=new DimensionsFilter();
            df3.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
            df3.setRelationDirection(RelationDirection.FROM);
            df3.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
            //df3.setRelationType("cdcdddd");
            df3.addDimensionId("#18:1");
            df3.addDimensionId("#17:1");
            df3.addDimensionId("#16:1");
            dimensionsFilters.add(df3);



            List<Fact> factLis1=ie.exploreFactsByRelatedDimensions(dimensionsFilters, ep);

            System.out.println(factLis1.size());
            System.out.println(factLis1.get(0).getType());
            System.out.println(factLis1.get(0).getId());





            /*

            System.out.println(factLis1.size());
            System.out.println(factLis1.get(0).getType());
            System.out.println(factLis1.get(0).getId());

//ep.setType("unitTestRootRelationTypeB");
        //    ep.setType("unitTestChildDimensionTypeAOfA");
            ep.setType(null);
//ep.setType("unitTestRootFactTypeB");
            System.out.println(new Date());







            List<String> idList=new ArrayList<>();

            idList.add("#21:0");idList.add("#21:10");idList.add("#21:555");

            String ss= SQLBuilder.buildExploreRelationablesByRelationSQL(idList,RelationDirection.TWO_WAY,ep,InformationType.FACT);

        System.out.println(ss);

            ep.setDistinctMode(false);
            System.out.println(ie.exploreDimensionsByRelations(idList, RelationDirection.TWO_WAY, ep));

            System.out.println(ie.exploreFactsByRelations(idList, RelationDirection.TWO_WAY, ep));

*/




        } catch (InfoDiscoveryEngineException e) {
            e.printStackTrace();
        }
       finally {
            if (ids != null) {
                ids.closeSpace();
            }
        }




    }
}
