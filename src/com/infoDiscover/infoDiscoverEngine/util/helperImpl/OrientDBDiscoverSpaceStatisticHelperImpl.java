package com.infoDiscover.infoDiscoverEngine.util.helperImpl;

import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.helper.DataTypeStatisticMetrics;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticHelper;
import com.infoDiscover.infoDiscoverEngine.util.helper.DiscoverSpaceStatisticMetrics;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.OMetadataDefault;
import com.orientechnologies.orient.core.metadata.schema.OClass;

import java.util.Collection;
import java.util.Iterator;

public class OrientDBDiscoverSpaceStatisticHelperImpl implements DiscoverSpaceStatisticHelper {

    private static String serviceLocation;
    private static String userAccount;
    private static String userPWD;

    public OrientDBDiscoverSpaceStatisticHelperImpl(String serviceLocation, String userAccount, String userPWD){
        this.serviceLocation=serviceLocation;
        this.userAccount=userAccount;
        this.userPWD=userPWD;
    }

    @Override
    public DiscoverSpaceStatisticMetrics getDiscoverSpaceStatisticMetrics(String spaceName) {
        DiscoverSpaceStatisticMetrics discoverSpaceStatisticMetrics=new DiscoverSpaceStatisticMetrics();
        ODatabaseDocumentTx db = new ODatabaseDocumentTx(serviceLocation+spaceName).open(userAccount, userPWD);
        discoverSpaceStatisticMetrics.setSpaceDiskSize(db.getSize());
        discoverSpaceStatisticMetrics.setSpaceFullDataCount(db.getStorage().countRecords());
        discoverSpaceStatisticMetrics.setSpaceLocation(db.getURL());
        discoverSpaceStatisticMetrics.setSpaceOwner(db.getUser().getName());
        OMetadataDefault dbMetadata=db.getMetadata();
        Collection<OClass> classesListCollection=dbMetadata.getSchema().getClasses();
        Iterator<OClass> classIterator=classesListCollection.iterator();

        while(classIterator.hasNext()){
            OClass currentClass=classIterator.next();
            String className=currentClass.getName();
            if(className.equals(InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME)){
                discoverSpaceStatisticMetrics.setSpaceFactDataCount(currentClass.count());
            }
            if(className.equals(InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME)){
                discoverSpaceStatisticMetrics.setSpaceDimensionDataCount(currentClass.count());
            }
            if(className.equals(InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME)){
                discoverSpaceStatisticMetrics.setSpaceRelationDataCount(currentClass.count());
            }
            if(className.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_FACT)){
                DataTypeStatisticMetrics dataTypeStatisticMetrics=new DataTypeStatisticMetrics();
                dataTypeStatisticMetrics.setDataTypeName(className);
                dataTypeStatisticMetrics.setTypeDataCount(currentClass.count());
                discoverSpaceStatisticMetrics.addFactStatisticMetrics(dataTypeStatisticMetrics);
            }
            if(className.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION)){
                DataTypeStatisticMetrics dataTypeStatisticMetrics=new DataTypeStatisticMetrics();
                dataTypeStatisticMetrics.setDataTypeName(className);
                dataTypeStatisticMetrics.setTypeDataCount(currentClass.count());
                discoverSpaceStatisticMetrics.addDimensionStatisticMetrics(dataTypeStatisticMetrics);
            }
            if(className.startsWith(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION)){
                DataTypeStatisticMetrics dataTypeStatisticMetrics=new DataTypeStatisticMetrics();
                dataTypeStatisticMetrics.setDataTypeName(className);
                dataTypeStatisticMetrics.setTypeDataCount(currentClass.count());
                discoverSpaceStatisticMetrics.addRelationStatisticMetrics(dataTypeStatisticMetrics);
            }
        }
        db.close();
        return discoverSpaceStatisticMetrics;
    }
}
