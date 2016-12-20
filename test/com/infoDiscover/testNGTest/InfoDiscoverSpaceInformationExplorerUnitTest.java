package com.infoDiscover.testNGTest;

import com.infoDiscover.infoDiscoverEngine.dataMart.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.*;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.DimensionsFilter;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationExplorer;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.RelationTypeFilter;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.infoDiscoverEngine.util.factory.DiscoverEngineComponentFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoDiscoverSpaceInformationExplorerUnitTest {
    @BeforeTest
    public void initData()throws InfoDiscoveryEngineDataMartException {
        System.out.println("--------------------------------------------------");
        System.out.println("Init unit test data for InfoDiscoverSpaceInformationExplorerUnitTest");
        System.out.println("--------------------------------------------------");

        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        if(ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA)){
            ids.getFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA).removeContainedFaces();
        }else{
            ids.addFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        }
        if(ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB)){
            ids.getFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB).removeContainedFaces();
        }else{
            ids.addFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA).removeContainedDimensions();
        }else{
            ids.addDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB).removeContainedDimensions();
        }else{
            ids.addDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA)){
            ids.getRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA).removeContainedRelations();
        }else{
            ids.addRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB)){
            ids.getRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB).removeContainedRelations();
        }else{
            ids.addRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        }
        ids.closeSpace();
    }

    @Test
    public void testDiscoverExploreParametersFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);

        ExploreParameters ep=new ExploreParameters();
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);

        List<Fact> factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        Fact testFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact1=ids.addFact(testFact1);
        Fact testFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact2=ids.addFact(testFact2);

        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());

        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        Fact testFact3=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact3=ids.addFact(testFact3);
        Fact testFact4=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact4=ids.addFact(testFact4);
        Fact testFact5=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact5=ids.addFact(testFact5);
        Fact testFact6=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact6=ids.addFact(testFact6);
        Fact testFact7=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact7=ids.addFact(testFact7);

        ep.setResultNumber(1);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);

        ep.setResultNumber(4);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 4);

        ep.setResultNumber(50);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 5);

        Fact testFact8=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact8=ids.addFact(testFact8);
        Fact testFact9=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact9=ids.addFact(testFact9);
        Fact testFact10=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact10=ids.addFact(testFact10);
        Fact testFact11=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact11=ids.addFact(testFact11);
        Fact testFact12=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact12=ids.addFact(testFact12);

        ep.setStartPage(1);
        ep.setPageSize(20);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);

        ep.setStartPage(2);
        ep.setPageSize(2);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 8);

        ep.setStartPage(2);
        ep.setPageSize(10);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        ep.setStartPage(1);
        ep.setPageSize(2);
        ep.setEndPage(2);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);

        ep.setStartPage(1);
        ep.setPageSize(2);
        ep.setEndPage(3);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 4);

        ep.setStartPage(2);
        ep.setPageSize(2);
        ep.setEndPage(3);
        factList= ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);

        boolean exceptionShouldThrownForLessThanZeroStartPage=false;
        try {
            ep.setStartPage(-1);
            ie.discoverFacts(ep);
        }catch (InfoDiscoveryEngineInfoExploreException e){
            exceptionShouldThrownForLessThanZeroStartPage=true;
        }
        Assert.assertTrue(exceptionShouldThrownForLessThanZeroStartPage);

        boolean exceptionShouldThrownForLessThanZeroPageSize=false;
        try {
            ep.setStartPage(1);
            ep.setPageSize(-1);
            ie.discoverFacts(ep);
        }catch (InfoDiscoveryEngineInfoExploreException e){
            exceptionShouldThrownForLessThanZeroPageSize=true;
        }
        Assert.assertTrue(exceptionShouldThrownForLessThanZeroPageSize);

        boolean exceptionShouldThrownForEndPageLessThanStartPage=false;
        try {
            ep.setStartPage(2);
            ep.setEndPage(1);
            ie.discoverFacts(ep);
        }catch (InfoDiscoveryEngineInfoExploreException e){
            exceptionShouldThrownForEndPageLessThanStartPage=true;
        }
        Assert.assertTrue(exceptionShouldThrownForEndPageLessThanStartPage);

        boolean exceptionShouldThrownForLessThanZeroResultNumber=false;
        try {
            ep.setStartPage(0);
            ep.setResultNumber(-10);
            ie.discoverFacts(ep);
        }catch (InfoDiscoveryEngineInfoExploreException e){
            exceptionShouldThrownForLessThanZeroResultNumber=true;
        }
        Assert.assertTrue(exceptionShouldThrownForLessThanZeroResultNumber);

        ids.removeFact(testFact3.getId());
        ids.removeFact(testFact4.getId());
        ids.removeFact(testFact5.getId());
        ids.removeFact(testFact6.getId());
        ids.removeFact(testFact7.getId());
        ids.removeFact(testFact8.getId());
        ids.removeFact(testFact9.getId());
        ids.removeFact(testFact10.getId());
        ids.removeFact(testFact11.getId());
        ids.removeFact(testFact12.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDiscoverExploreParametersFunc" })
    public void testDiscoverFactsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Fact testFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact1.setInitProperty("queryPropertyA_int", 100);
        testFact1.setInitProperty("queryPropertyB_int", 500);
        testFact1=ids.addFact(testFact1);
        Fact testFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact2.setInitProperty("queryPropertyA_long", (long)100);
        testFact2.setInitProperty("queryPropertyB_int", 600);
        testFact2=ids.addFact(testFact2);
        Fact testFact3=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact3.setInitProperty("queryPropertyA_int", 100);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);
        Fact testFact4=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact4.setInitProperty("queryPropertyA_byte", (byte)13);
        testFact4.setInitProperty("queryPropertyB_int", 800);
        testFact4=ids.addFact(testFact4);
        Fact testFact5=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact5.setInitProperty("queryPropertyA_double", (double)2345.66);
        testFact5.setInitProperty("queryPropertyC_str", "A");
        testFact5.setInitProperty("queryPropertyA_string", "Uiiicsiamst");
        testFact5=ids.addFact(testFact5);
        Fact testFact6=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact6.setInitProperty("queryPropertyA_int", 100);
        testFact6=ids.addFact(testFact6);
        Fact testFact7=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact7.setInitProperty("queryPropertyA_boolean",false);
        testFact7.setInitProperty("queryPropertyC_str", "C");
        testFact7=ids.addFact(testFact7);
        Fact testFact8=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact8.setInitProperty("queryPropertyA_boolean",true);
        testFact8.setInitProperty("queryPropertyA_float",(float)145666);
        testFact8.setInitProperty("queryPropertyA_string", "iamstringYuscss");
        testFact8=ids.addFact(testFact8);
        Date currentDate=new Date();
        Fact testFact9=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact9.setInitProperty("queryPropertyA_date", currentDate);
        testFact9.setInitProperty("queryPropertyC_str", "D");
        testFact9=ids.addFact(testFact9);
        Fact testFact10=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact10.setInitProperty("queryPropertyA_short", (short) 11);
        testFact10=ids.addFact(testFact10);
        Fact testFact11=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact11.setInitProperty("queryPropertyA_date", currentDate);
        testFact11.setInitProperty("queryPropertyC_str", "E");
        testFact11=ids.addFact(testFact11);
        Fact testFact12 = DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact12.setInitProperty("queryPropertyA_string", "iamstring");
        testFact12.setInitProperty("queryPropertyA_long", (long) 100);
        testFact12=ids.addFact(testFact12);

        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);
        ExploreParameters ep=new ExploreParameters();
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);

        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_int", 100));
        List<Fact> factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", false));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact7.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", true));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.get(0).getId(), testFact8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_string", "iamstring"));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact12.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_long", (long) 100));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact2.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_float", (float) 145666));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_short", (short) 11));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact10.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_double", (double) 2345.66));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact5.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_byte", (byte) 13));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact4.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_date", currentDate));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact9.getId());
        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyA_string", "iamstring");
        efi.reverseCondition();
        ep.setDefaultFilteringItem(efi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 100, 600));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact1.getId());
        Assert.assertEquals(factList.get(1).getId(), testFact2.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 600, 700));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact2.getId());
        Assert.assertEquals(factList.get(1).getId(), testFact3.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 800, 1000));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact4.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 1000, 5000));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 0, 50));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        BetweenFilteringItem bfi=new BetweenFilteringItem("queryPropertyB_int", 100, 600);
        bfi.reverseCondition();
        ep.setDefaultFilteringItem(bfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);

        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 4);
        Assert.assertEquals(factList.get(0).getId(), testFact1.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact3.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 900));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        GreaterThanEqualFilteringItem gtefi=new GreaterThanEqualFilteringItem("queryPropertyB_int", 500);
        gtefi.reverseCondition();
        ep.setDefaultFilteringItem(gtefi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 8);

        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        Assert.assertEquals(factList.get(0).getId(), testFact2.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact4.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 800));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        GreaterThanFilteringItem gtfi=new GreaterThanFilteringItem("queryPropertyB_int", 500);
        gtfi.reverseCondition();
        ep.setDefaultFilteringItem(gtfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 9);

        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testFact1.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        Assert.assertEquals(factList.get(0).getId(), testFact1.getId());
        Assert.assertEquals(factList.get(2).getId(), testFact3.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 400));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        LessThanEqualFilteringItem ltefi=new LessThanEqualFilteringItem("queryPropertyB_int", 500);
        ltefi.reverseCondition();
        ep.setDefaultFilteringItem(ltefi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testFact1.getId());
        Assert.assertEquals(factList.get(1).getId(), testFact2.getId());
        LessThanFilteringItem ltfi=new LessThanFilteringItem("queryPropertyB_int", 500);
        ltfi.reverseCondition();
        ep.setDefaultFilteringItem(ltfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 12);

        List<Object> objList= new ArrayList<Object>();
        objList.add("A");
        objList.add("B");
        objList.add("C");
        InValueFilteringItem ivfi=new InValueFilteringItem("queryPropertyC_str", objList);
        ep.setDefaultFilteringItem(ivfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        ivfi.reverseCondition();
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);

        NotEqualFilteringItem nefi=new NotEqualFilteringItem("queryPropertyC_str","A");
        ep.setDefaultFilteringItem(nefi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        nefi.reverseCondition();
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 9);

        NullValueFilteringItem nvfi=new NullValueFilteringItem("queryPropertyA_long");
        ep.setDefaultFilteringItem(nvfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);
        nvfi.reverseCondition();
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);

        SimilarFilteringItem sfi=new SimilarFilteringItem("queryPropertyA_string", "iamst",SimilarFilteringItem.MatchingType.BeginWith);
        ep.setDefaultFilteringItem(sfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        sfi.reverseCondition();
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.Contain));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.EndWith));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst1", SimilarFilteringItem.MatchingType.EndWith));
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        RegularMatchFilteringItem rmfi=new RegularMatchFilteringItem("queryPropertyA_string","iamstring");
        ep.setDefaultFilteringItem(rmfi);
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        rmfi.reverseCondition();
        factList = ie.discoverFacts(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ExploreParameters ep2=new ExploreParameters();
        ep2.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        ep2.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_int", 100));
        factList = ie.discoverFacts(ep2);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);

        ep2.addFilteringItem(new EqualFilteringItem("queryPropertyB_int", 800), ExploreParameters.FilteringLogic.OR);
        factList = ie.discoverFacts(ep2);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 4);

        ep2.addFilteringItem(new EqualFilteringItem("queryPropertyA_byte", (byte) 13), ExploreParameters.FilteringLogic.AND);
        factList = ie.discoverFacts(ep2);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);

        ep2.addFilteringItem(new EqualFilteringItem("queryPropertyC_str", "D"), ExploreParameters.FilteringLogic.AND);
        factList = ie.discoverFacts(ep2);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeFact(testFact4.getId());
        ids.removeFact(testFact5.getId());
        ids.removeFact(testFact6.getId());
        ids.removeFact(testFact7.getId());
        ids.removeFact(testFact8.getId());
        ids.removeFact(testFact9.getId());
        ids.removeFact(testFact10.getId());
        ids.removeFact(testFact11.getId());
        ids.removeFact(testFact12.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDiscoverFactsFunc" })
    public void testDiscoverDimensionsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException {
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);
        Dimension testDimension4=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension4.setInitProperty("queryPropertyA_byte", (byte)13);
        testDimension4.setInitProperty("queryPropertyB_int", 800);
        testDimension4=ids.addDimension(testDimension4);
        Dimension testDimension5=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension5.setInitProperty("queryPropertyA_double", (double)2345.66);
        testDimension5.setInitProperty("queryPropertyC_str", "A");
        testDimension5.setInitProperty("queryPropertyA_string", "Uiiicsiamst");
        testDimension5=ids.addDimension(testDimension5);
        Dimension testDimension6=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension6.setInitProperty("queryPropertyA_int", 100);
        testDimension6=ids.addDimension(testDimension6);
        Dimension testDimension7=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension7.setInitProperty("queryPropertyA_boolean",false);
        testDimension7.setInitProperty("queryPropertyC_str", "C");
        testDimension7=ids.addDimension(testDimension7);
        Dimension testDimension8=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension8.setInitProperty("queryPropertyA_boolean",true);
        testDimension8.setInitProperty("queryPropertyA_float",(float)145666);
        testDimension8.setInitProperty("queryPropertyA_string", "iamstringYuscss");
        testDimension8=ids.addDimension(testDimension8);
        Date currentDate=new Date();
        Dimension testDimension9=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension9.setInitProperty("queryPropertyA_date", currentDate);
        testDimension9.setInitProperty("queryPropertyC_str", "D");
        testDimension9=ids.addDimension(testDimension9);
        Dimension testDimension10=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension10.setInitProperty("queryPropertyA_short", (short) 11);
        testDimension10=ids.addDimension(testDimension10);
        Dimension testDimension11=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension11.setInitProperty("queryPropertyA_date", currentDate);
        testDimension11.setInitProperty("queryPropertyC_str", "E");
        testDimension11=ids.addDimension(testDimension11);
        Dimension testDimension12 = DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension12.setInitProperty("queryPropertyA_string", "iamstring");
        testDimension12.setInitProperty("queryPropertyA_long", (long) 100);
        testDimension12=ids.addDimension(testDimension12);

        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);
        ExploreParameters ep=new ExploreParameters();
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);

        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_int", 100));
        List<Dimension> DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 3);
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", false));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension7.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", true));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_string", "iamstring"));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension12.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_long", (long) 100));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension2.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_float", (float) 145666));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_short", (short) 11));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension10.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_double", (double) 2345.66));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension5.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_byte", (byte) 13));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension4.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_date", currentDate));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension9.getId());
        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyA_string", "iamstring");
        efi.reverseCondition();
        ep.setDefaultFilteringItem(efi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 11);

        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 100, 600));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension1.getId());
        Assert.assertEquals(DimensionList.get(1).getId(), testDimension2.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 600, 700));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension2.getId());
        Assert.assertEquals(DimensionList.get(1).getId(), testDimension3.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 800, 1000));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension4.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 1000, 5000));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 0, 50));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        BetweenFilteringItem bfi=new BetweenFilteringItem("queryPropertyB_int", 100, 600);
        bfi.reverseCondition();
        ep.setDefaultFilteringItem(bfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 10);

        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 500));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 4);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension1.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 700));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension3.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 900));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        GreaterThanEqualFilteringItem gtefi=new GreaterThanEqualFilteringItem("queryPropertyB_int", 500);
        gtefi.reverseCondition();
        ep.setDefaultFilteringItem(gtefi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 8);

        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 500));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 3);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension2.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 700));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension4.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 800));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        GreaterThanFilteringItem gtfi=new GreaterThanFilteringItem("queryPropertyB_int", 500);
        gtfi.reverseCondition();
        ep.setDefaultFilteringItem(gtfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 9);

        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 500));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension1.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 700));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 3);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension1.getId());
        Assert.assertEquals(DimensionList.get(2).getId(), testDimension3.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 400));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        LessThanEqualFilteringItem ltefi=new LessThanEqualFilteringItem("queryPropertyB_int", 500);
        ltefi.reverseCondition();
        ep.setDefaultFilteringItem(ltefi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 11);

        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 500));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);
        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 700));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        Assert.assertEquals(DimensionList.get(0).getId(), testDimension1.getId());
        Assert.assertEquals(DimensionList.get(1).getId(), testDimension2.getId());
        LessThanFilteringItem ltfi=new LessThanFilteringItem("queryPropertyB_int", 500);
        ltfi.reverseCondition();
        ep.setDefaultFilteringItem(ltfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 12);

        List<Object> objList= new ArrayList<Object>();
        objList.add("A");
        objList.add("B");
        objList.add("C");
        InValueFilteringItem ivfi=new InValueFilteringItem("queryPropertyC_str", objList);
        ep.setDefaultFilteringItem(ivfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        ivfi.reverseCondition();
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 10);

        NotEqualFilteringItem nefi=new NotEqualFilteringItem("queryPropertyC_str","A");
        ep.setDefaultFilteringItem(nefi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 3);
        nefi.reverseCondition();
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 9);

        NullValueFilteringItem nvfi=new NullValueFilteringItem("queryPropertyA_long");
        ep.setDefaultFilteringItem(nvfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 10);
        nvfi.reverseCondition();
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);

        SimilarFilteringItem sfi=new SimilarFilteringItem("queryPropertyA_string", "iamst",SimilarFilteringItem.MatchingType.BeginWith);
        ep.setDefaultFilteringItem(sfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 2);
        sfi.reverseCondition();
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 10);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.Contain));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 3);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.EndWith));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst1", SimilarFilteringItem.MatchingType.EndWith));
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 0);

        RegularMatchFilteringItem rmfi=new RegularMatchFilteringItem("queryPropertyA_string","iamstring");
        ep.setDefaultFilteringItem(rmfi);
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 1);
        rmfi.reverseCondition();
        DimensionList = ie.discoverDimensions(ep);
        Assert.assertNotNull(DimensionList);
        Assert.assertEquals(DimensionList.size(), 11);

        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());
        ids.removeDimension(testDimension4.getId());
        ids.removeDimension(testDimension5.getId());
        ids.removeDimension(testDimension6.getId());
        ids.removeDimension(testDimension7.getId());
        ids.removeDimension(testDimension8.getId());
        ids.removeDimension(testDimension9.getId());
        ids.removeDimension(testDimension10.getId());
        ids.removeDimension(testDimension11.getId());
        ids.removeDimension(testDimension12.getId());
        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDiscoverDimensionsFunc" })
    public void testDiscoverRelationsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);

        Fact containerFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        containerFact1=ids.addFact(containerFact1);
        Fact containerFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        containerFact2=ids.addFact(containerFact2);

        Relation testRelation1=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation1.addProperty("queryPropertyA_int", 100);
        testRelation1.addProperty("queryPropertyB_int", 500);
        Relation testRelation2=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation2.addProperty("queryPropertyA_long", (long) 100);
        testRelation2.addProperty("queryPropertyB_int", 600);
        Relation testRelation3=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation3.addProperty("queryPropertyA_int", 100);
        testRelation3.addProperty("queryPropertyB_int", 700);
        Relation testRelation4=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation4.addProperty("queryPropertyA_byte", (byte) 13);
        testRelation4.addProperty("queryPropertyB_int", 800);
        Relation testRelation5=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation5.addProperty("queryPropertyA_double", (double) 2345.66);
        testRelation5.addProperty("queryPropertyC_str", "A");
        testRelation5.addProperty("queryPropertyA_string", "Uiiicsiamst");
        Relation testRelation6=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation6.addProperty("queryPropertyA_int", 100);
        Relation testRelation7=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation7.addProperty("queryPropertyA_boolean", false);
        testRelation7.addProperty("queryPropertyC_str", "C");
        Relation testRelation8=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation8.addProperty("queryPropertyA_boolean", true);
        testRelation8.addProperty("queryPropertyA_float", (float) 145666);
        testRelation8.addProperty("queryPropertyA_string", "iamstringYuscss");
        Date currentDate=new Date();
        Relation testRelation9=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation9.addProperty("queryPropertyA_date", currentDate);
        testRelation9.addProperty("queryPropertyC_str", "D");
        Relation testRelation10=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation10.addProperty("queryPropertyA_short", (short) 11);
        Relation testRelation11=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation11.addProperty("queryPropertyA_date", currentDate);
        testRelation11.addProperty("queryPropertyC_str", "E");
        Relation testRelation12=ids.addDirectionalFactRelation(containerFact1, containerFact2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA, true);
        testRelation12.addProperty("queryPropertyA_string", "iamstring");
        testRelation12.addProperty("queryPropertyA_long", (long) 100);

        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);
        ExploreParameters ep=new ExploreParameters();
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_int", 100));
        List<Relation> factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", false));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation7.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_boolean", true));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.get(0).getId(), testRelation8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_string", "iamstring"));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation12.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_long", (long) 100));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation2.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_float", (float) 145666));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation8.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_short", (short) 11));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation10.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_double", (double) 2345.66));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation5.getId());
        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_byte", (byte) 13));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation4.getId());

        ep.setDefaultFilteringItem(new EqualFilteringItem("queryPropertyA_date", currentDate));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation9.getId());

        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyA_string", "iamstring");
        efi.reverseCondition();
        ep.setDefaultFilteringItem(efi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 100, 600));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation1.getId());
        Assert.assertEquals(factList.get(1).getId(), testRelation2.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 600, 700));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation2.getId());
        Assert.assertEquals(factList.get(1).getId(), testRelation3.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 800, 1000));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation4.getId());
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 1000, 5000));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        ep.setDefaultFilteringItem(new BetweenFilteringItem("queryPropertyB_int", 0, 50));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        BetweenFilteringItem bfi=new BetweenFilteringItem("queryPropertyB_int", 100, 600);
        bfi.reverseCondition();
        ep.setDefaultFilteringItem(bfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);

        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 4);
        Assert.assertEquals(factList.get(0).getId(), testRelation1.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation3.getId());
        ep.setDefaultFilteringItem(new GreaterThanEqualFilteringItem("queryPropertyB_int", 900));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        GreaterThanEqualFilteringItem gtefi=new GreaterThanEqualFilteringItem("queryPropertyB_int", 500);
        gtefi.reverseCondition();
        ep.setDefaultFilteringItem(gtefi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 8);

        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        Assert.assertEquals(factList.get(0).getId(), testRelation2.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation4.getId());
        ep.setDefaultFilteringItem(new GreaterThanFilteringItem("queryPropertyB_int", 800));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        GreaterThanFilteringItem gtfi=new GreaterThanFilteringItem("queryPropertyB_int", 500);
        gtfi.reverseCondition();
        ep.setDefaultFilteringItem(gtfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 9);

        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        Assert.assertEquals(factList.get(0).getId(), testRelation1.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        Assert.assertEquals(factList.get(0).getId(), testRelation1.getId());
        Assert.assertEquals(factList.get(2).getId(), testRelation3.getId());
        ep.setDefaultFilteringItem(new LessThanEqualFilteringItem("queryPropertyB_int", 400));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        LessThanEqualFilteringItem ltefi=new LessThanEqualFilteringItem("queryPropertyB_int", 500);
        ltefi.reverseCondition();
        ep.setDefaultFilteringItem(ltefi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 500));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);
        ep.setDefaultFilteringItem(new LessThanFilteringItem("queryPropertyB_int", 700));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        Assert.assertEquals(factList.get(0).getId(), testRelation1.getId());
        Assert.assertEquals(factList.get(1).getId(), testRelation2.getId());
        LessThanFilteringItem ltfi=new LessThanFilteringItem("queryPropertyB_int", 500);
        ltfi.reverseCondition();
        ep.setDefaultFilteringItem(ltfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 12);

        List<Object> objList= new ArrayList<Object>();
        objList.add("A");
        objList.add("B");
        objList.add("C");
        InValueFilteringItem ivfi=new InValueFilteringItem("queryPropertyC_str", objList);
        ep.setDefaultFilteringItem(ivfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        ivfi.reverseCondition();
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);

        NotEqualFilteringItem nefi=new NotEqualFilteringItem("queryPropertyC_str","A");
        ep.setDefaultFilteringItem(nefi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        nefi.reverseCondition();
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 9);

        NullValueFilteringItem nvfi=new NullValueFilteringItem("queryPropertyA_long");
        ep.setDefaultFilteringItem(nvfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);
        nvfi.reverseCondition();
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);

        SimilarFilteringItem sfi=new SimilarFilteringItem("queryPropertyA_string", "iamst",SimilarFilteringItem.MatchingType.BeginWith);
        ep.setDefaultFilteringItem(sfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 2);
        sfi.reverseCondition();
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 10);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.Contain));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 3);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst", SimilarFilteringItem.MatchingType.EndWith));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        ep.setDefaultFilteringItem(new SimilarFilteringItem("queryPropertyA_string", "iamst1", SimilarFilteringItem.MatchingType.EndWith));
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 0);

        RegularMatchFilteringItem rmfi=new RegularMatchFilteringItem("queryPropertyA_string","iamstring");
        ep.setDefaultFilteringItem(rmfi);
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 1);
        rmfi.reverseCondition();
        factList = ie.discoverRelations(ep);
        Assert.assertNotNull(factList);
        Assert.assertEquals(factList.size(), 11);

        ids.removeRelation(testRelation1.getId());
        ids.removeRelation(testRelation2.getId());
        ids.removeRelation(testRelation3.getId());
        ids.removeRelation(testRelation4.getId());
        ids.removeRelation(testRelation5.getId());
        ids.removeRelation(testRelation6.getId());
        ids.removeRelation(testRelation7.getId());
        ids.removeRelation(testRelation8.getId());
        ids.removeRelation(testRelation9.getId());
        ids.removeRelation(testRelation10.getId());
        ids.removeRelation(testRelation11.getId());
        ids.removeRelation(testRelation12.getId());
        ids.removeFact(containerFact1.getId());
        ids.removeFact(containerFact2.getId());
        ids.closeSpace();
    }


    @Test(dependsOnMethods = { "testDiscoverRelationsFunc" })
    public void testDiscoverRelationableFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);
        String sql="SELECT FROM ID_FACT_unitTestInfoExploreFactTypeA SKIP 0 LIMIT 100";
        List<Relationable> relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 0);

        Fact testFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact1.setInitProperty("queryPropertyA_int", 100);
        testFact1.setInitProperty("queryPropertyB_int", 500);
        testFact1=ids.addFact(testFact1);
        Fact testFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact2.setInitProperty("queryPropertyA_long", (long)100);
        testFact2.setInitProperty("queryPropertyB_int", 600);
        testFact2=ids.addFact(testFact2);
        Fact testFact3=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact3.setInitProperty("queryPropertyA_int", 100);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);
        Fact testFact4=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact4.setInitProperty("queryPropertyA_byte", (byte)13);
        testFact4.setInitProperty("queryPropertyB_int", 800);
        testFact4=ids.addFact(testFact4);
        Fact testFact5=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact5.setInitProperty("queryPropertyA_double", (double)2345.66);
        testFact5.setInitProperty("queryPropertyC_str", "A");
        testFact5.setInitProperty("queryPropertyA_string", "Uiiicsiamst");
        testFact5=ids.addFact(testFact5);

        relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 5);
        Assert.assertNotNull(relationableList.get(0).getId());

        sql="SELECT FROM ID_FACT_unitTestInfoExploreFactTypeA WHERE queryPropertyA_int =100 SKIP 0 LIMIT 100";
        relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 2);

        sql="SELECT FROM ID_DIMENSION_unitTestInfoExploreDimensionTypeA SKIP 0 LIMIT 100";
        relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 0);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);
        Dimension testDimension4=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension4.setInitProperty("queryPropertyA_byte", (byte)13);
        testDimension4.setInitProperty("queryPropertyB_int", 800);
        testDimension4=ids.addDimension(testDimension4);
        Dimension testDimension5=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension5.setInitProperty("queryPropertyA_double", (double)2345.66);
        testDimension5.setInitProperty("queryPropertyC_str", "A");
        testDimension5.setInitProperty("queryPropertyA_string", "Uiiicsiamst");
        testDimension5=ids.addDimension(testDimension5);

        relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 5);

        sql="SELECT FROM ID_DIMENSION_unitTestInfoExploreDimensionTypeA WHERE queryPropertyA_string like '%csia%' SKIP 0 LIMIT 100";
        relationableList= ie.discoverRelationables(sql);
        Assert.assertNotNull(relationableList);
        Assert.assertEquals(relationableList.size(), 1);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeFact(testFact4.getId());
        ids.removeFact(testFact5.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());
        ids.removeDimension(testDimension4.getId());
        ids.removeDimension(testDimension5.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testDiscoverRelationableFunc" })
    public void testExploreRelationsByRelatedRelationablesFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        InformationExplorer ie=ids.getInformationExplorer();
        Assert.assertNotNull(ie);

        Fact testFact1=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact1.setInitProperty("queryPropertyA_int", 100);
        testFact1.setInitProperty("queryPropertyB_int", 500);
        testFact1=ids.addFact(testFact1);
        Fact testFact2=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact2.setInitProperty("queryPropertyA_long", (long)100);
        testFact2.setInitProperty("queryPropertyB_int", 600);
        testFact2=ids.addFact(testFact2);
        Fact testFact3=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact3.setInitProperty("queryPropertyA_int", 100);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);

        Relation relation1=testFact1.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation2=testFact1.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact1.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        relation1.addProperty("relationProperty",100);
        relation2.addProperty("relationProperty",100);
        relation3.addProperty("relationProperty", 300);

        relation1.addProperty("relationProperty2",1);
        relation2.addProperty("relationProperty2",2);
        relation3.addProperty("relationProperty2",3);

        List<String> relationableIds=new ArrayList<>();
        ExploreParameters ep=new ExploreParameters();
        boolean exceptionShouldThrownForNotSettingRelationableIds=false;
        try {
            ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotSettingRelationableIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotSettingRelationableIds);

        exceptionShouldThrownForNotSettingRelationableIds=false;
        try {
            ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, null, ep);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotSettingRelationableIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotSettingRelationableIds);

        relationableIds.add(testFact1.getId());

        List<Relation> exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertNotNull(exploredRelationList);
        Assert.assertEquals(exploredRelationList.size(), 3);
        for(Relation currentRelation:exploredRelationList){
            Assert.assertEquals(currentRelation.getFromRelationable().getId(), testFact1.getId());
            boolean isMatchedRelation=currentRelation.getId().equals(relation1.getId())||
                    currentRelation.getId().equals(relation2.getId())||
                    currentRelation.getId().equals(relation3.getId());
            Assert.assertTrue(isMatchedRelation);

            String currentRelationType=currentRelation.getType();
            boolean isMatchRelationType=currentRelationType.equals(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA)||
                    currentRelationType.equals(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
            Assert.assertTrue(isMatchRelationType);
        }

        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TO, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 0);

        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TWO_WAY, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 3);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 1);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        EqualFilteringItem efi=new EqualFilteringItem("relationProperty", 100);
        ep.setDefaultFilteringItem(efi);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 1);
        ep.setType(null);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 2);

        EqualFilteringItem efi2=new EqualFilteringItem("relationProperty2", 2);
        ep.addFilteringItem(efi2, ExploreParameters.FilteringLogic.AND);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 1);

        boolean exceptionShouldThrownForNotExistedRelationableIds=false;
        relationableIds.add("NotExistId");
        try {
            ie.exploreRelationsByRelatedRelationables(RelationDirection.FROM, relationableIds, ep);
        }catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedRelationableIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedRelationableIds);

        relationableIds=new ArrayList<>();
        ep=new ExploreParameters();
        relationableIds.add(testFact1.getId());
        relationableIds.add(testDimension1.getId());
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TWO_WAY, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 3);

        ep.setDistinctMode(false);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TWO_WAY, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 4);

        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TO, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 1);

        testFact2.addFromRelation(testDimension2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        testFact3.addFromRelation(testDimension3, UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);

        relationableIds=new ArrayList<>();
        relationableIds.add(testFact1.getId());
        relationableIds.add(testFact2.getId());
        relationableIds.add(testFact3.getId());
        ep.setDistinctMode(true);
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredRelationList=ie.exploreRelationsByRelatedRelationables(RelationDirection.TWO_WAY, relationableIds, ep);
        Assert.assertEquals(exploredRelationList.size(), 3);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }


    @Test(dependsOnMethods = { "testExploreRelationsByRelatedRelationablesFunc" })
    public void testExploreFactsByRelatedRelationTypesFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        testFact3.setInitProperty("queryPropertyA_int", 100);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);

        Relation relation1=testFact1.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation2=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact3.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        List<RelationTypeFilter> rtf=new ArrayList<>();
        RelationTypeFilter rtf1=
                new RelationTypeFilter(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,RelationDirection.FROM,0,
                        RelationTypeFilter.FilteringLogic.AND,RelationTypeFilter.TypeExistenceLogic.HAS);
        rtf.add(rtf1);

        ExploreParameters ep=new ExploreParameters();
        ep.setType("NOT_EXIST_FACT_TYPE");
        boolean exceptionShouldThrownForNotExistedFactType=false;
        try {
            ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedFactType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedFactType);

        ep.setType(null);
        List<Fact> exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertNotNull(exploredFactList);
        Assert.assertEquals(exploredFactList.size(), 2);

        rtf1.setRelationDirection(RelationDirection.TO);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        rtf1.setRelationDirection(RelationDirection.TWO_WAY);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        rtf1.setRelationDirection(RelationDirection.FROM);
        rtf1.setRelationCount(1);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 2);
        rtf1.setRelationCount(2);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        rtf1.setRelationCount(0);

        RelationTypeFilter rtf2=new RelationTypeFilter();
        rtf2.setTypeName(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        rtf2.setRelationDirection(RelationDirection.FROM);
        rtf2.setFilteringLogic(RelationTypeFilter.FilteringLogic.OR);
        rtf2.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.HAS);
        rtf.add(rtf2);

        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        rtf2.setFilteringLogic(RelationTypeFilter.FilteringLogic.AND);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        rtf2.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.NOTHAS);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        rtf2.setTypeExistenceLogic(RelationTypeFilter.TypeExistenceLogic.HAS);
        rtf2.setFilteringLogic(RelationTypeFilter.FilteringLogic.OR);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 500);
        ep.setDefaultFilteringItem(efi);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        Relation relation4=testFact3.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        rtf.clear();
        rtf.add(rtf1);
        rtf1.setRelationCount(2);
        ep.setDefaultFilteringItem(null);
        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf,ep);
        Assert.assertEquals(exploredFactList.size(), 1);
        Assert.assertEquals(exploredFactList.get(0).getId(), testFact3.getId());

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        exploredFactList=ie.exploreFactsByRelatedRelationTypes(rtf, ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreFactsByRelatedRelationTypesFunc" })
    public void testExploreFactsByRelatedDimensionsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        testFact3.setInitProperty("queryPropertyA_int", 100);
        testFact3.setInitProperty("queryPropertyB_int", 700);
        testFact3=ids.addFact(testFact3);

        Dimension testDimension1=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension1.setInitProperty("queryPropertyA_int", 100);
        testDimension1.setInitProperty("queryPropertyB_int", 500);
        testDimension1=ids.addDimension(testDimension1);
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);

        Relation relation1=testFact1.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation2=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact3.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        List<DimensionsFilter> dfl=new ArrayList<>();
        ExploreParameters ep=new ExploreParameters();
        ep.setType("NOT_EXIST_FACT_TYPE");
        boolean exceptionShouldThrownForNotExistedFactType=false;
        try {
            ie.exploreFactsByRelatedDimensions(dfl, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedFactType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedFactType);

        ep.setType(null);

        DimensionsFilter df1=new DimensionsFilter(testDimension1.getId(),RelationDirection.FROM,
                UnitTestConfigInfo.unitTestInfoExploreRelationTypeA,DimensionsFilter.FilteringLogic.OR,DimensionsFilter.RelationExistenceLogic.HAS);
        dfl.add(df1);

        List<Fact> exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertNotNull(exploredFactList);
        Assert.assertEquals(exploredFactList.size(), 1);

        df1.setRelationDirection(RelationDirection.TO);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        df1.setRelationDirection(RelationDirection.TWO_WAY);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        df1.setRelationDirection(RelationDirection.TO);
        df1.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.NOTHAS);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        df1.setRelationDirection(RelationDirection.FROM);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        df1.setRelationDirection(RelationDirection.FROM);
        df1.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        df1.addDimensionId(testDimension2.getId());
        df1.addDimensionId(testDimension3.getId());

        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        df1.setRelationType(null);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 3);
        for(Fact currentFact:exploredFactList){
            String factId=currentFact.getId();
            boolean isMatchedId=factId.equals(testFact1.getId())||
                    factId.equals(testFact2.getId())||factId.equals(testFact3.getId());
            Assert.assertTrue(isMatchedId);
            String factType=currentFact.getType();
            boolean isMatchedType=factType.equals(UnitTestConfigInfo.unitTestInfoExploreFactTypeA)||
                    factType.equals(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
            Assert.assertTrue(isMatchedType);

        }

        dfl.clear();

        DimensionsFilter df2=new DimensionsFilter();
        df2.addDimensionId(testDimension1.getId());
        df2.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
        df2.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        df2.setRelationDirection(RelationDirection.FROM);
        dfl.add(df2);

        DimensionsFilter df3=new DimensionsFilter();
        df3.addDimensionId(testDimension2.getId());
        df3.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
        df3.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        df3.setRelationDirection(RelationDirection.FROM);
        dfl.add(df3);

        DimensionsFilter df4=new DimensionsFilter();
        df4.addDimensionId(testDimension3.getId());
        df4.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
        df4.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        df4.setRelationDirection(RelationDirection.FROM);
        dfl.add(df4);

        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        df4.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.NOTHAS);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        df4.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        df4.setRelationExistenceLogic(DimensionsFilter.RelationExistenceLogic.HAS);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        df3.setFilteringLogic(DimensionsFilter.FilteringLogic.AND);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        df2.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
        df3.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
        df4.setFilteringLogic(DimensionsFilter.FilteringLogic.OR);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep.setType(null);

        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 500);
        ep.setDefaultFilteringItem(efi);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep=new ExploreParameters();
        EqualFilteringItem efi2=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.AND);
        ep.addFilteringItem(efi2, ExploreParameters.FilteringLogic.AND);
        exploredFactList=ie.exploreFactsByRelatedDimensions(dfl, ep);
        Assert.assertEquals(exploredFactList.size(), 0);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreFactsByRelatedDimensionsFunc" })
    public void testExploreFactsByRelationsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        Dimension testDimension2=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension2.setInitProperty("queryPropertyA_long", (long)100);
        testDimension2.setInitProperty("queryPropertyB_int", 600);
        testDimension2=ids.addDimension(testDimension2);
        Dimension testDimension3=DiscoverEngineComponentFactory.createDimension(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        testDimension3.setInitProperty("queryPropertyA_int", 100);
        testDimension3.setInitProperty("queryPropertyB_int", 700);
        testDimension3=ids.addDimension(testDimension3);

        Relation relation1=testFact1.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        Relation relation2=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact3.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        List<String> relationIds=null;
        ExploreParameters ep=new ExploreParameters();

        boolean exceptionShouldThrownForNotExistedRelationIds=false;
        try {
            ie.exploreFactsByRelations(RelationDirection.TWO_WAY,relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedRelationIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedRelationIds);

        relationIds=new ArrayList<>();
        boolean exceptionShouldThrownForEmptyRelationIds=false;
        try {
            ie.exploreFactsByRelations(RelationDirection.TWO_WAY,relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForEmptyRelationIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForEmptyRelationIds);

        relationIds.add(relation1.getId());

        List<Fact> exploredFactList=ie.exploreFactsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertNotNull(exploredFactList);
        Assert.assertEquals(exploredFactList.size(), 0);

        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        exploredFactList=ie.exploreFactsByRelations(RelationDirection.TWO_WAY, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        relationIds.add(relation2.getId());
        relationIds.add(relation3.getId());

        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA+"NOT_EXIST");
        boolean exceptionShouldThrownForNotExistedFactType=false;
        try {
            ie.exploreFactsByRelations(RelationDirection.TWO_WAY,relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedFactType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedFactType);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep.setType(null);

        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        Relation relation4=testFact1.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        relationIds.add(relation4.getId());
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 3);

        ep.setDistinctMode(false);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 4);

        ep.setDistinctMode(true);
        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.setDefaultFilteringItem(efi);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 1);


        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        exploredFactList=ie.exploreFactsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredFactList.size(), 1);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreFactsByRelationsFunc" })
    public void testExploreDimensionsByRelationsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        Relation relation2=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact3.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        List<String> relationIds=null;
        ExploreParameters ep=new ExploreParameters();

        boolean exceptionShouldThrownForNotExistedRelationIds=false;
        try {
            ie.exploreDimensionsByRelations(RelationDirection.TWO_WAY, relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedRelationIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedRelationIds);

        relationIds=new ArrayList<>();
        boolean exceptionShouldThrownForEmptyRelationIds=false;
        try {
            ie.exploreDimensionsByRelations(RelationDirection.TWO_WAY, relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForEmptyRelationIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForEmptyRelationIds);

        relationIds.add(relation1.getId());

        List<Dimension> exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertNotNull(exploredDimensionList);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.FROM, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 0);

        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TWO_WAY, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        relationIds.add(relation2.getId());
        relationIds.add(relation3.getId());

        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA+"NOT_EXIST");
        boolean exceptionShouldThrownForNotExistedDimensionType=false;
        try {
            ie.exploreDimensionsByRelations(RelationDirection.TWO_WAY, relationIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedDimensionType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedDimensionType);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(null);

        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        Relation relation4=testFact1.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        relationIds.add(relation4.getId());
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        ep.setDistinctMode(false);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 4);

        ep.setDistinctMode(true);
        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.setDefaultFilteringItem(efi);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelations(RelationDirection.TO, relationIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreDimensionsByRelationsFunc" })
    public void testExploreDimensionsByRelatedFactsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        Relation relation2=testFact2.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        Relation relation3=testFact3.addToRelation(testDimension3,UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        List<String> factIds=null;
        ExploreParameters ep=new ExploreParameters();

        boolean exceptionShouldThrownForNotExistedFactIds=false;
        try {
            ie.exploreDimensionsByRelatedFacts(RelationDirection.TWO_WAY, factIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedFactIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedFactIds);

        factIds=new ArrayList<>();
        boolean exceptionShouldThrownForEmptyFactIds=false;
        try {
            ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForEmptyFactIds=true;
        }
        Assert.assertTrue(exceptionShouldThrownForEmptyFactIds);

        factIds.add("FactIdNotExist");
        boolean exceptionShouldThrownForNotExistedSingelFactId=false;
        try {
            ie.exploreDimensionsByRelatedFacts(RelationDirection.TO, factIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistedSingelFactId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistedSingelFactId);

        factIds.clear();
        factIds.add(testDimension1.getId());
        boolean exceptionShouldThrownForNotFactId=false;
        try {
            ie.exploreDimensionsByRelatedFacts(RelationDirection.TO, factIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotFactId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotFactId);
        factIds.clear();
        factIds.add(testFact1.getId());

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA + "NotExist");
        boolean exceptionShouldThrownForNotExistDimensionType=false;
        try {
            ie.exploreDimensionsByRelatedFacts(RelationDirection.TO, factIds, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistDimensionType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistDimensionType);

        ep.setType(null);

        List<Dimension> exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertNotNull(exploredDimensionList);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.TO, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 0);

        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.TWO_WAY, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        factIds.add(testFact2.getId());
        factIds.add(testFact3.getId());

        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(null);
        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        Relation relation4=testFact2.addToRelation(testDimension1,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);

        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 3);

        ep.setDistinctMode(false);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 4);

        ep.setDistinctMode(true);

        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.setDefaultFilteringItem(efi);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 2);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        exploredDimensionList=ie.exploreDimensionsByRelatedFacts(RelationDirection.FROM, factIds, ep);
        Assert.assertEquals(exploredDimensionList.size(), 1);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreDimensionsByRelatedFactsFunc" })
    public void testExploreSimilarFactsByRelatedDimensionsFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), null, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        ep.setDistinctMode(true);
        exploredFactList=ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), null, ep);
        Assert.assertEquals(exploredFactList.size(), 2);

        boolean exceptionShouldThrownForNotExistFactId=false;
        try {
            ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, null, null, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistFactId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistFactId);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB + "NotExist");
        boolean exceptionShouldThrownForNotExistFactType=false;
        try {
            ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), null, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistFactType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistFactType);

        ep.setType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        boolean exceptionShouldThrownForNotExistRelationTypeType=false;
        try {
            ie.exploreSimilarFactsByRelatedDimensions(RelationDirection.TWO_WAY, testFact3.getId(), UnitTestConfigInfo.unitTestInfoExploreRelationTypeA+"notExist", ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistRelationTypeType=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistRelationTypeType);

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @Test(dependsOnMethods = { "testExploreSimilarFactsByRelatedDimensionsFunc" })
    public void testExploreRelatedRelationablesByRelationDepthFunc() throws InfoDiscoveryEngineDataMartException,InfoDiscoveryEngineRuntimeException,InfoDiscoveryEngineInfoExploreException{
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
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
        Fact testFact4=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact4.setInitProperty("queryPropertyA_int", 200);
        testFact4.setInitProperty("queryPropertyB_int", 700);
        testFact4=ids.addFact(testFact4);
        Fact testFact5=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact5.setInitProperty("queryPropertyA_int", 200);
        testFact5.setInitProperty("queryPropertyB_int", 700);
        testFact5=ids.addFact(testFact5);

        Fact testFact6=DiscoverEngineComponentFactory.createFact(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        testFact6.setInitProperty("queryPropertyA_int", 200);
        testFact6.setInitProperty("queryPropertyB_int", 700);
        testFact6=ids.addFact(testFact6);

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

        testFact1.addToRelation(testDimension1, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        testFact1.addToRelation(testDimension2,UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        testFact1.addToRelation(testDimension3, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);

        ExploreParameters ep=new ExploreParameters();
        List<Relationable> exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertNotNull(exploredRelationableList);
        Assert.assertEquals(exploredRelationableList.size(), 3);

        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TO,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 0);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.FROM,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 3);

        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 1);

        ep=new ExploreParameters();
        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 2);
        ep.addRelatedRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 3);

        testFact1.addFromRelation(testDimension3, UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 3);

        ep.setDistinctMode(false);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 3);

        boolean exceptionShouldThrownForNotExistRelationableId=false;
        try {
            ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY, null, 1, ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForNotExistRelationableId=true;
        }
        Assert.assertTrue(exceptionShouldThrownForNotExistRelationableId);

        boolean exceptionShouldThrownForIncorrectDepth=false;
        try {
           ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),0,ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForIncorrectDepth=true;
        }
        Assert.assertTrue(exceptionShouldThrownForIncorrectDepth);

        exceptionShouldThrownForIncorrectDepth=false;
        try {
            ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),-1,ep);
        } catch (InfoDiscoveryEngineRuntimeException e){
            exceptionShouldThrownForIncorrectDepth=true;
        }
        Assert.assertTrue(exceptionShouldThrownForIncorrectDepth);

        ep.setDistinctMode(true);

        EqualFilteringItem efi=new EqualFilteringItem("queryPropertyB_int", 600);
        ep.setDefaultFilteringItem(efi);

        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 1);

        EqualFilteringItem efi1=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi1, ExploreParameters.FilteringLogic.OR);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 2);

        ep=new ExploreParameters();
        EqualFilteringItem efi2=new EqualFilteringItem("queryPropertyB_int", 700);
        ep.setDefaultFilteringItem(efi2);
        EqualFilteringItem efi3=new EqualFilteringItem("queryPropertyA_int", 100);
        ep.addFilteringItem(efi3, ExploreParameters.FilteringLogic.AND);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),1,ep);
        Assert.assertEquals(exploredRelationableList.size(), 0);

        testFact2.addToRelation(testDimension1, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        testFact3.addFromRelation(testDimension2, UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        testFact4.addToRelation(testDimension3, UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        testFact5.addFromRelation(testDimension3, UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);

        ep=new ExploreParameters();
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TWO_WAY,testFact1.getId(),2,ep);
        Assert.assertEquals(exploredRelationableList.size(), 4);

        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.FROM,testFact1.getId(),2,ep);
        Assert.assertEquals(exploredRelationableList.size(), 2);
        for(Relationable currentRelationable:exploredRelationableList){
            String relationableId=currentRelationable.getId();
            boolean isMatchedId= relationableId.equals(testFact3.getId())||relationableId.equals(testFact5.getId());
            Assert.assertTrue(isMatchedId);
        }

        ep.setDistinctMode(false);
        exploredRelationableList=ie.exploreRelatedRelationablesByRelationDepth(RelationDirection.TO,testFact1.getId(),2,ep);
        Assert.assertEquals(exploredRelationableList.size(), 1);
        Assert.assertEquals(exploredRelationableList.get(0).getId(),testFact4.getId());

        ids.removeFact(testFact1.getId());
        ids.removeFact(testFact2.getId());
        ids.removeFact(testFact3.getId());
        ids.removeFact(testFact4.getId());
        ids.removeFact(testFact5.getId());
        ids.removeFact(testFact6.getId());
        ids.removeDimension(testDimension1.getId());
        ids.removeDimension(testDimension2.getId());
        ids.removeDimension(testDimension3.getId());

        ids.closeSpace();
    }

    @AfterTest
    public void cleanTestData()throws InfoDiscoveryEngineDataMartException{
        System.out.println("--------------------------------------------------");
        System.out.println("Clean unit test data for InfoDiscoverSpaceInformationExplorerUnitTest");
        System.out.println("--------------------------------------------------");
        InfoDiscoverSpace ids= DiscoverEngineComponentFactory.connectInfoDiscoverSpace(UnitTestConfigInfo.unitTestSpaceName);
        if(ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA)){
            ids.getFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA).removeContainedFaces();
            ids.removeFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeA);
        }
        if(ids.hasFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB)){
            ids.getFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB).removeContainedFaces();
            ids.removeFactType(UnitTestConfigInfo.unitTestInfoExploreFactTypeB);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA).removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeA);
        }
        if(ids.hasDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB)){
            ids.getDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB).removeContainedDimensions();
            ids.removeDimensionType(UnitTestConfigInfo.unitTestInfoExploreDimensionTypeB);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA)){
            ids.getRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA).removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeA);
        }
        if(ids.hasRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB)){
            ids.getRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB).removeContainedRelations();
            ids.removeRelationType(UnitTestConfigInfo.unitTestInfoExploreRelationTypeB);
        }
        ids.closeSpace();
    }


}
