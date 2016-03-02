import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.command.OCommandPredicate;
import com.orientechnologies.orient.core.command.traverse.OTraverse;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.filter.OSQLPredicate;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.viewfnction.infoDiscoverEngine.dataMart.*;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.DimensionsFilter;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering.*;
import com.viewfnction.infoDiscoverEngine.dataWarehouse.RelationTypeFilter;
import com.viewfnction.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.viewfnction.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.viewfnction.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import com.viewfunction.testNGTest.UnitTestConfigInfo;
import org.testng.Assert;

import java.util.*;

public class SmokeTest {
    /* smock test logic*/
    public static void main(String[] args) throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException, InfoDiscoveryEngineInfoExploreException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA)){
            ids.addFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        }
        if(!ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB)){
            ids.addFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA)){
            ids.addDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        }
        if(!ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB)){
            ids.addDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA)){
            ids.addRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        }
        if(!ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB)){
            ids.addRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        }
        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);

        Fact testFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact1.setInitProperty("queryPropertyA_int", 100);
        testFact1.setInitProperty("queryPropertyB_int", 500);
        testFact1=ids.addFact(testFact1);
        Fact testFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        testFact2.setInitProperty("queryPropertyA_long", (long)100);
        testFact2.setInitProperty("queryPropertyB_int", 600);
        testFact2=ids.addFact(testFact2);
        Fact testFact3=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact3.setInitProperty("queryPropertyA_int", 200);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 200);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);

        Relation relation1=testFact1.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation2=testFact1.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation3=testFact1.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation4=testFact2.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation5=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation6=testFact3.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        ExploreParameters ep=new ExploreParameters();

        List<Fact> exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact3.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertNotNull(exploredFactList);
        Assert.assertEquals(exploredFactList.size(), 2);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TO,testFact3.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY,testFact3.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact2.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertEquals(exploredFactList.size(), 1);
        Assert.assertEquals(exploredFactList.get(0).getId(),testFact1.getId());
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact1.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact3.getId(),UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,ep);
        Assert.assertEquals(exploredFactList.size(), 1);
        Assert.assertEquals(exploredFactList.get(0).getId(), testFact2.getId());

        ep.setType(null);
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact3.getId(),null,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TO,testFact3.getId(),null,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        ep.setDistinctMode(false);
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.FROM,testFact3.getId(),null,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY,testFact3.getId(),null,ep);
        Assert.assertEquals(exploredFactList.size(), 2);





        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.setDefaultFilteringItem(efi);
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), null, ep);
        Assert.assertEquals(exploredFactList.size(), 1);



        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        Assert.assertEquals(exploredFactList.size(), 0);


        ep.setDistinctMode(true);

        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), null, ep);
        Assert.assertEquals(exploredFactList.size(), 1);



/*
        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());
*/
        ids.closeSpace();
    }



    public static void main0(String[] args) {

        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/orientTest", "root", "wyc").setupPool(1, 10);
        //try {
        final OrientGraph graph = factory.getTx();


        OrientVertex v=graph.getVertex("#29:1");


//System.out.println(v);


  OTraverse tr= v.traverse();


        //System.out.println(tr.getLastTraversed());

       // System.out.println(tr.execute());

List<OIdentifiable> oList=tr.execute();

        for(OIdentifiable currOIdentifiable:oList){



          //  System.out.println(currOIdentifiable.getIdentity());



        }


/*

        for (OIdentifiable id : new OTraverse()
                .field("*")
                .target(v)
                .predicate( new OSQLPredicate("$depth <= 5"))) {

            System.out.println(id);
        }
*/



        for (OIdentifiable id : new OTraverse()
                .field("in").field("out")
                .target(v)
                .predicate(new OCommandPredicate() {

                    @Override
                    public Object evaluate(OIdentifiable oIdentifiable, ODocument oDocument, OCommandContext oCommandContext) {

                        System.out.println(oIdentifiable);

                        return null;
                    }

                    public Object evaluate(ORecord iRecord, ODocument iCurrentResult, OCommandContext iContext) {
                        return ((Integer) iContext.getVariable("depth")) <= 5;
                    }
                })) {

            System.out.println(id);
        }


        for (OIdentifiable id : new OTraverse()
                //.target(new ORecordId("#6:0"), new ORecordId("#6:1"))
                .target(new ORecordId("#29:8"))
                //.fields("out", "int")
                .field("*")
                .limit(100)
                .predicate( new OSQLPredicate("$depth <= 10"))) {

            System.out.println( id);
        }


        graph.shutdown();


        // } finally {


        factory.close();
        // }
    }







    }





