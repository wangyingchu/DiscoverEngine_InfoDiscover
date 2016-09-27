package com.infoDiscover.infoDiscoverEngine.dataWarehouse;

import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.FilteringItem;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineException;
import com.infoDiscover.infoDiscoverEngine.dataMart.RelationDirection;
import com.infoDiscover.infoDiscoverEngine.util.InfoDiscoverEngineConstant;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;

import java.util.Date;
import java.util.List;

public class SQLBuilder {

    public static String buildQuerySQL(InformationType informationType,ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException {
        String type=exploreParameters.getType();
        String orientTypeClass=null;
        switch(informationType){
            case FACT:
                if(type!=null){
                    orientTypeClass= InfoDiscoverEngineConstant.CLASSPERFIX_FACT+type;
                }else{
                    orientTypeClass= InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;
                }
                break;
            case DIMENSION:
                if(type!=null){
                    orientTypeClass= InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+type;
                }else{
                    orientTypeClass= InfoDiscoverEngineConstant.DIMENSION_ROOTCLASSNAME;
                }
                break;
            case RELATION:
                if(type!=null){
                    orientTypeClass= InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+type;
                }else{
                    orientTypeClass= InfoDiscoverEngineConstant.RELATION_ROOTCLASSNAME;
                }
                break;
        }

        StringBuffer querySQLStringBuffer=new StringBuffer();
        querySQLStringBuffer.append("SELECT FROM ");
        querySQLStringBuffer.append(orientTypeClass);

        if(exploreParameters.getDefaultFilteringItem()!=null){
            querySQLStringBuffer.append(" WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());

            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }

        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        System.out.println(querySQLStringBuffer.toString());
        return querySQLStringBuffer.toString();
    }

    public static String buildExploreDimensionsByRelatedFactSQL(List<String> factIds,RelationDirection relationDirection,ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException {
        String directionMethod="both";
        switch(relationDirection){
            case FROM:directionMethod="out";
                break;
            case TO:directionMethod="in";
                break;
            case TWO_WAY:directionMethod="both";
                break;
        }
        List<String> relatedRelationTypes=exploreParameters.getRelatedRelationTypesList();
        StringBuffer relationSelectSQL=new StringBuffer();
        relationSelectSQL.append("SELECT "+directionMethod+"(");
        for(int i=0;i<relatedRelationTypes.size();i++){
            relationSelectSQL.append("\"");
            relationSelectSQL.append(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+relatedRelationTypes.get(i));
            relationSelectSQL.append("\"");
            if(i<relatedRelationTypes.size()-1){
                relationSelectSQL.append(",");
            }
        }
        relationSelectSQL.append(")");

        String factIdsArray=factIds.toString();
        relationSelectSQL.append(" FROM "+factIdsArray);

        String expandSelectSQL="SELECT EXPAND("+directionMethod+") FROM("+relationSelectSQL.toString()+")";

        String relationableTypeFilter=null;
        if(exploreParameters.getType()!=null){
            relationableTypeFilter=") WHERE @class ="+"\""+InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION+exploreParameters.getType()+"\"";
        }else{
            relationableTypeFilter=") WHERE @class LIKE " + "\"" + InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION + "%\"";
        }

        StringBuffer querySQLStringBuffer=new StringBuffer();
        if(exploreParameters.isDistinctMode()){
            querySQLStringBuffer.append("SELECT DISTINCT(@rid) FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }else{
            querySQLStringBuffer.append("SELECT FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }

        if(exploreParameters.getDefaultFilteringItem()!=null){
            querySQLStringBuffer.append(" WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());

            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }

        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        String resultSQL;
        if(exploreParameters.isDistinctMode()){

            resultSQL="SELECT EXPAND(DISTINCT) FROM("+querySQLStringBuffer.toString()+")";
        }else{
            resultSQL=querySQLStringBuffer.toString();
        }
        System.out.println(resultSQL);
        return resultSQL;
    }

    public static String buildExploreFactsByRelationTypesSQL(List<RelationTypeFilter> relationTypeFilters, ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException{
        String factTypeStr=exploreParameters.getType();
        String factType;
        if(factTypeStr!=null){
            factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
        }else{
            factType=InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;
        }
        StringBuffer querySQLStringBuffer=new StringBuffer();
        querySQLStringBuffer.append("SELECT FROM " + factType);
        if(relationTypeFilters.size()>0){
            querySQLStringBuffer.append(" WHERE ");

            RelationTypeFilter firstRelationTypeFilter = relationTypeFilters.get(0);
            String directionMethod="both";
            RelationDirection relationDirection= firstRelationTypeFilter.getRelationDirection();

            switch(relationDirection){
                case FROM:directionMethod="out";
                    break;
                case TO:directionMethod="in";
                    break;
                case TWO_WAY:directionMethod="both";
                    break;
            }

            String currentFilter= directionMethod+"(\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+ firstRelationTypeFilter.getTypeName()+"\")";

            long relationCount= firstRelationTypeFilter.getRelationCount();
            if(relationCount!=0){
                currentFilter= currentFilter+".size() = "+relationCount;
            }else{
                RelationTypeFilter.TypeExistenceLogic typeExistenceLogic = firstRelationTypeFilter.getTypeExistenceLogic();
                switch(typeExistenceLogic){
                    case HAS: currentFilter= currentFilter+".size() <> 0";
                        break;
                    case NOTHAS:currentFilter=currentFilter+".size() = 0";
                        break;
                }
            }

            querySQLStringBuffer.append(currentFilter);

            if(relationTypeFilters.size()>1){
                for(int i=1;i< relationTypeFilters.size();i++){
                    String currentQuerySQLPath="";
                    RelationTypeFilter currentRelationTypeFilter = relationTypeFilters.get(i);
                    RelationTypeFilter.FilteringLogic filteringLogic= currentRelationTypeFilter.getFilteringLogic();
                    switch(filteringLogic){
                        case AND: currentQuerySQLPath=" AND ";
                            break;
                        case OR: currentQuerySQLPath=" OR ";
                            break;
                    }
                    relationDirection= currentRelationTypeFilter.getRelationDirection();
                    switch(relationDirection){
                        case FROM:directionMethod="out";
                            break;
                        case TO:directionMethod="in";
                            break;
                        case TWO_WAY:directionMethod="both";
                            break;
                    }
                    currentQuerySQLPath= currentQuerySQLPath+directionMethod+"(\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+ currentRelationTypeFilter.getTypeName()+"\")";

                    relationCount= currentRelationTypeFilter.getRelationCount();
                    if(relationCount!=0){
                        currentQuerySQLPath= currentQuerySQLPath+".size() = "+relationCount;
                    }else{
                        RelationTypeFilter.TypeExistenceLogic typeExistenceLogic = currentRelationTypeFilter.getTypeExistenceLogic();
                        switch(typeExistenceLogic){
                            case HAS: currentQuerySQLPath= currentQuerySQLPath+".size() <> 0";
                                break;
                            case NOTHAS:currentQuerySQLPath=currentQuerySQLPath+".size() = 0";
                                break;
                        }
                    }
                    querySQLStringBuffer.append(currentQuerySQLPath);
                }
            }
        }
        if(exploreParameters.getDefaultFilteringItem()!=null){
            StringBuffer outerStringBuffer=new StringBuffer();
            outerStringBuffer.append("SELECT FROM(");
            outerStringBuffer.append(querySQLStringBuffer.toString());
            outerStringBuffer.append(") WHERE ");
            outerStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());
            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                outerStringBuffer.append(" AND ");
                outerStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                outerStringBuffer.append(" OR ");
                outerStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
            setCommonPagingParameters(outerStringBuffer, exploreParameters);
            System.out.println(outerStringBuffer.toString());
            return outerStringBuffer.toString();
        }else{
            setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
            System.out.println(querySQLStringBuffer.toString());
            return querySQLStringBuffer.toString();
        }
    }

    public static String buildExploreFactsByRelatedDimensionsSQL(List<DimensionsFilter> dimensionsFilters,ExploreParameters exploreParameters) throws InfoDiscoveryEngineInfoExploreException{
        String factTypeStr=exploreParameters.getType();
        String factType;
        if(factTypeStr!=null){
            factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
        }else{
            factType=InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;
        }
        StringBuffer querySQLStringBuffer=new StringBuffer();
        querySQLStringBuffer.append("SELECT FROM " + factType);
        if(dimensionsFilters.size()>0) {
            querySQLStringBuffer.append(" WHERE ");

            DimensionsFilter firstDimensionsFilter = dimensionsFilters.get(0);
            String directionMethod="both";
            RelationDirection relationDirection= firstDimensionsFilter.getRelationDirection();

            switch(relationDirection){
                case FROM:directionMethod="out";
                    break;
                case TO:directionMethod="in";
                    break;
                case TWO_WAY:directionMethod="both";
                    break;
            }

            String currentFilter;
            if(firstDimensionsFilter.getRelationType()!=null){
                currentFilter= directionMethod+"(\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+ firstDimensionsFilter.getRelationType()+"\")";
            }else{
                currentFilter= directionMethod+"()";
            }
            String dimensionIdsArray=firstDimensionsFilter.getDimensionIdsList().toString();
            currentFilter=currentFilter+" in "+dimensionIdsArray;

            DimensionsFilter.RelationExistenceLogic relationExistenceLogic=firstDimensionsFilter.getRelationExistenceLogic();
            switch(relationExistenceLogic){
                case HAS:break;
                case NOTHAS:currentFilter="NOT("+currentFilter+")";
                    break;
            }
            querySQLStringBuffer.append(currentFilter);

            if(dimensionsFilters.size()>1){
                for(int i=1;i< dimensionsFilters.size();i++){
                    DimensionsFilter currentDimensionsFilter = dimensionsFilters.get(i);
                    relationDirection= currentDimensionsFilter.getRelationDirection();
                    switch(relationDirection){
                        case FROM:directionMethod="out";
                            break;
                        case TO:directionMethod="in";
                            break;
                        case TWO_WAY:directionMethod="both";
                            break;
                    }
                    if(currentDimensionsFilter.getRelationType()!=null){
                        currentFilter= directionMethod+"(\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+ currentDimensionsFilter.getRelationType()+"\")";
                    }else{
                        currentFilter= directionMethod+"()";
                    }

                    dimensionIdsArray=currentDimensionsFilter.getDimensionIdsList().toString();
                    currentFilter=currentFilter+" in "+dimensionIdsArray;

                    relationExistenceLogic=currentDimensionsFilter.getRelationExistenceLogic();
                    switch(relationExistenceLogic){
                        case HAS:break;
                        case NOTHAS:currentFilter="NOT("+currentFilter+")";
                            break;
                    }

                    DimensionsFilter.FilteringLogic filteringLogic=currentDimensionsFilter.getFilteringLogic();

                    switch (filteringLogic){
                        case OR: querySQLStringBuffer.append(" OR ");
                            break;
                        case AND:querySQLStringBuffer.append(" AND ");
                            break;
                    }
                    querySQLStringBuffer.append(currentFilter);
                }
            }
        }
        if(exploreParameters.getDefaultFilteringItem()!=null){
            StringBuffer outerStringBuffer=new StringBuffer();
            outerStringBuffer.append("SELECT FROM(");
            outerStringBuffer.append(querySQLStringBuffer.toString());
            outerStringBuffer.append(") WHERE ");
            outerStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());
            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                outerStringBuffer.append(" AND ");
                outerStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                outerStringBuffer.append(" OR ");
                outerStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
            setCommonPagingParameters(outerStringBuffer, exploreParameters);
            System.out.println(outerStringBuffer.toString());
            return outerStringBuffer.toString();
        }else{
            setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
            System.out.println(querySQLStringBuffer.toString());
            return querySQLStringBuffer.toString();
        }
    }

    public static String buildExploreRelationsByRelatedRelationableSQL(List<String> relationableIds, RelationDirection relationDirection, ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException {
        String directionMethod="bothE";
        switch(relationDirection){
            case FROM:directionMethod="outE";
                break;
            case TO:directionMethod="inE";
                break;
            case TWO_WAY:directionMethod="bothE";
                break;
        }
        List<String> relatedDimensionTypes=exploreParameters.getRelatedRelationTypesList();
        StringBuffer relationSelectSQL=new StringBuffer();
        relationSelectSQL.append("SELECT "+directionMethod+"(");
        for(int i=0;i<relatedDimensionTypes.size();i++){
            relationSelectSQL.append("\"");
            relationSelectSQL.append(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+relatedDimensionTypes.get(i));
            relationSelectSQL.append("\"");
            if(i<relatedDimensionTypes.size()-1){
                relationSelectSQL.append(",");
            }
        }
        relationSelectSQL.append(")");

        String relationableIdArray=relationableIds.toString();
        relationSelectSQL.append(" FROM "+relationableIdArray);

        String expandSelectSQL="SELECT EXPAND("+directionMethod+") FROM("+relationSelectSQL.toString()+")";

        String relationableTypeFilter=null;
        if(exploreParameters.getType()!=null){
            relationableTypeFilter=") WHERE @class ="+"\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+exploreParameters.getType()+"\"";
        }else{
            relationableTypeFilter=") WHERE @class LIKE " + "\"" + InfoDiscoverEngineConstant.CLASSPERFIX_RELATION + "%\"";
        }

        StringBuffer querySQLStringBuffer=new StringBuffer();
        if(exploreParameters.isDistinctMode()){
            querySQLStringBuffer.append("SELECT DISTINCT(@rid) FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }else{
            querySQLStringBuffer.append("SELECT FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }

        if(exploreParameters.getDefaultFilteringItem()!=null){
            querySQLStringBuffer.append(" WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());

            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }

        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        String resultSQL;
        if(exploreParameters.isDistinctMode()){
            resultSQL="SELECT EXPAND(DISTINCT) FROM("+querySQLStringBuffer.toString()+")";
        }else{
            resultSQL=querySQLStringBuffer.toString();
        }
        System.out.println(resultSQL);
        return resultSQL;
    }

    public static String buildExploreRelationablesByRelationSQL(List<String> relationIds, RelationDirection relationDirection,
                                                                ExploreParameters exploreParameters, InformationType informationType)throws InfoDiscoveryEngineInfoExploreException {
        String directionMethod="bothV";
        switch(relationDirection){
            case FROM:directionMethod="outV";
                break;
            case TO:directionMethod="inV";
                break;
            case TWO_WAY:directionMethod="bothV";
                break;
        }
        List<String> relatedRelationTypes=exploreParameters.getRelatedRelationTypesList();
        StringBuffer relationSelectSQL=new StringBuffer();
        relationSelectSQL.append("SELECT "+directionMethod+"(");
        for(int i=0;i<relatedRelationTypes.size();i++){
            relationSelectSQL.append("\"");
            relationSelectSQL.append(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+relatedRelationTypes.get(i));
            relationSelectSQL.append("\"");
            if(i<relatedRelationTypes.size()-1){
                relationSelectSQL.append(",");
            }
        }
        relationSelectSQL.append(")");
        String relationIdsArray=relationIds.toString();
        relationSelectSQL.append(" FROM "+relationIdsArray);

        String expandSelectSQL="SELECT EXPAND("+directionMethod+") FROM("+relationSelectSQL.toString()+")";

        StringBuffer querySQLStringBuffer=new StringBuffer();

        String classPerFix="";
        switch(informationType){
            case FACT:classPerFix=InfoDiscoverEngineConstant.CLASSPERFIX_FACT;break;
            case DIMENSION:classPerFix=InfoDiscoverEngineConstant.CLASSPERFIX_DIMENSION;break;
        }

        String relationableTypeFilter=null;
        if(exploreParameters.getType()!=null){
            relationableTypeFilter=") WHERE @class ="+"\""+classPerFix+exploreParameters.getType()+"\"";

        }else{
            relationableTypeFilter=") WHERE @class LIKE " + "\"" + classPerFix + "%\"";
        }

        if(exploreParameters.isDistinctMode()){
            querySQLStringBuffer.append("SELECT DISTINCT(@rid) FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }else{
            querySQLStringBuffer.append("SELECT FROM("+ "SELECT FROM("+expandSelectSQL+relationableTypeFilter+")");
        }

        if(exploreParameters.getDefaultFilteringItem()!=null){
            querySQLStringBuffer.append(" WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());

            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }

        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        String resultSQL;
        if(exploreParameters.isDistinctMode()){
            resultSQL="SELECT EXPAND(DISTINCT) FROM("+querySQLStringBuffer.toString()+")";
        }else{
            resultSQL=querySQLStringBuffer.toString();
        }
        System.out.println(resultSQL);
        return resultSQL;
    }


    public static String buildExploreSimilarFactsByRelatedDimensionsSQL(String factId, String relationType, RelationDirection relationDirection, ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException{
        String factTypeStr=exploreParameters.getType();
        String factType;
        if(factTypeStr!=null){
            factType=InfoDiscoverEngineConstant.CLASSPERFIX_FACT+factTypeStr;
        }else{
            factType=InfoDiscoverEngineConstant.FACT_ROOTCLASSNAME;
        }
        String directionMethod="both";
        switch(relationDirection){
            case FROM:directionMethod="out";
                break;
            case TO:directionMethod="in";
                break;
            case TWO_WAY:directionMethod="both";
                break;
        }
        String relationFilter;
        if(relationType!=null){
            relationFilter= directionMethod+"(\""+InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+ relationType+"\")";
        }else{
            relationFilter= directionMethod+"()";
        }
        String internalSQL=factType+" LET $connections =(SELECT @rid FROM (TRAVERSE "+relationFilter+" FROM "+
                factId+" WHILE $depth <= 1  STRATEGY BREADTH_FIRST) WHERE $depth = 1), $currentRelations="+relationFilter+" WHERE $connections.size() > 0 AND $connections CONTAINSALL (rid in $currentRelations) AND "+
                "@rid<>"+factId;
        String fullSelectSQL;
        if(exploreParameters.isDistinctMode()){
            fullSelectSQL="SELECT DISTINCT(@rid) FROM "+internalSQL;
        }else{
            fullSelectSQL="SELECT FROM "+internalSQL;
        }
        StringBuffer querySQLStringBuffer=new StringBuffer();
        if(exploreParameters.isDistinctMode()){
            querySQLStringBuffer.append("SELECT EXPAND(DISTINCT) FROM(");
            querySQLStringBuffer.append(fullSelectSQL);
            querySQLStringBuffer.append(")");
        }else{
            querySQLStringBuffer.append(fullSelectSQL);
        }
        if(exploreParameters.getDefaultFilteringItem()!=null){
            String currentSQLStringResult=querySQLStringBuffer.toString();
            querySQLStringBuffer=new StringBuffer();
            querySQLStringBuffer.append("SELECT FROM(");
            querySQLStringBuffer.append(currentSQLStringResult);
            querySQLStringBuffer.append(")WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());
            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }
        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        String resultSQL=querySQLStringBuffer.toString();
        System.out.println(resultSQL);
        return resultSQL;
    }

    public static String buildExploreRelatedRelationablesByRelationDepthSQL(RelationDirection relationDirection, String sourceRelationableId, int depth, ExploreParameters exploreParameters)throws InfoDiscoveryEngineInfoExploreException{
        String directionMethod="both";
        switch(relationDirection){
            case FROM:directionMethod="out";
                break;
            case TO:directionMethod="in";
                break;
            case TWO_WAY:directionMethod="both";
                break;
        }
        String relationFilter;
        List<String> relatedRelationTypes=exploreParameters.getRelatedRelationTypesList();
        if(relatedRelationTypes!=null&&relatedRelationTypes.size()>0){
            StringBuffer relationSelectSQL=new StringBuffer();
            for(int i=0;i<relatedRelationTypes.size();i++){
                relationSelectSQL.append("\"");
                relationSelectSQL.append(InfoDiscoverEngineConstant.CLASSPERFIX_RELATION+relatedRelationTypes.get(i));
                relationSelectSQL.append("\"");
                if(i<relatedRelationTypes.size()-1){
                    relationSelectSQL.append(",");
                }
            }
            relationFilter= directionMethod+"("+relationSelectSQL.toString()+")";
        }else{
            relationFilter= directionMethod+"()";
        }
        String traverseSQL="FROM (TRAVERSE "+relationFilter+" FROM "+sourceRelationableId+" WHILE $depth <= "+depth+" STRATEGY BREADTH_FIRST) WHERE $depth >= "+depth;

        StringBuffer querySQLStringBuffer=new StringBuffer();
        if(exploreParameters.isDistinctMode()){
            querySQLStringBuffer.append("SELECT EXPAND(DISTINCT) FROM( SELECT DISTINCT(@rid) ");
            querySQLStringBuffer.append(traverseSQL);
            querySQLStringBuffer.append(")");

        }else{
            querySQLStringBuffer.append("SELECT EXPAND(rid) FROM(SELECT @rid ");
            querySQLStringBuffer.append(traverseSQL);
            querySQLStringBuffer.append(")");
        }

        if(exploreParameters.getDefaultFilteringItem()!=null){
            String currentSQLStringResult=querySQLStringBuffer.toString();
            querySQLStringBuffer=new StringBuffer();
            querySQLStringBuffer.append("SELECT FROM(");
            querySQLStringBuffer.append(currentSQLStringResult);
            querySQLStringBuffer.append(")WHERE ");
            querySQLStringBuffer.append(exploreParameters.getDefaultFilteringItem().getFilteringLogic());
            List<FilteringItem> andFilteringItemsList= exploreParameters.getAndFilteringItemsList();
            for(FilteringItem andFilteringItem:andFilteringItemsList){
                querySQLStringBuffer.append(" AND ");
                querySQLStringBuffer.append(andFilteringItem.getFilteringLogic());
            }
            List<FilteringItem> orFilteringItemsList= exploreParameters.getOrFilteringItemsList();
            for(FilteringItem orFilteringItem:orFilteringItemsList){
                querySQLStringBuffer.append(" OR ");
                querySQLStringBuffer.append(orFilteringItem.getFilteringLogic());
            }
        }
        setCommonPagingParameters(querySQLStringBuffer, exploreParameters);
        String resultSQL=querySQLStringBuffer.toString();
        System.out.println(resultSQL);
        return resultSQL;
    }

    private static void setCommonPagingParameters(StringBuffer querySQLStringBuffer, ExploreParameters exploreParameters) throws InfoDiscoveryEngineInfoExploreException{
        if(exploreParameters.getStartPage()!=0){
            if(exploreParameters.getStartPage()<0){
                String exceptionMessage = "start page must great then zero";
                throw InfoDiscoveryEngineException.getInfoExploreException(exceptionMessage);
            }
            if(exploreParameters.getPageSize()<0){
                String exceptionMessage = "page size must great then zero";
                throw InfoDiscoveryEngineException.getInfoExploreException(exceptionMessage);
            }

            int runtimePageSize=exploreParameters.getPageSize()!=0?exploreParameters.getPageSize():50;
            int runtimeStartPage=exploreParameters.getStartPage()-1;
            if(exploreParameters.getEndPage()!=0){
                //get data from start page to end page, each page has runtimePageSize number of record
                if(exploreParameters.getEndPage()<0||exploreParameters.getEndPage()<=exploreParameters.getStartPage()){
                    String exceptionMessage = "end page must great than start page";
                    throw InfoDiscoveryEngineException.getInfoExploreException(exceptionMessage);
                }
                int runtimeEndPage=exploreParameters.getEndPage()-1;
                querySQLStringBuffer.append(" ");
                querySQLStringBuffer.append("SKIP ");
                querySQLStringBuffer.append(runtimePageSize*runtimeStartPage);
                querySQLStringBuffer.append(" ");
                querySQLStringBuffer.append("LIMIT ");
                querySQLStringBuffer.append((runtimeEndPage-runtimeStartPage)*runtimePageSize);
            }else{
                //filter the data before the start page
                querySQLStringBuffer.append(" ");
                querySQLStringBuffer.append("SKIP ");
                querySQLStringBuffer.append(runtimePageSize*runtimeStartPage);
            }
        }else{
            //if there is no page parameters,use resultNumber to control result information number
            if(exploreParameters.getResultNumber()!=0){
                if(exploreParameters.getResultNumber()<0){
                    String exceptionMessage = "result number must great then zero";
                    throw InfoDiscoveryEngineException.getInfoExploreException(exceptionMessage);
                }
                querySQLStringBuffer.append(" ");
                querySQLStringBuffer.append("LIMIT ");
                querySQLStringBuffer.append(exploreParameters.getResultNumber());
            }
        }
    }

    public static String formatFilteringValue(Object filteringValue){
        String formattedValue;
        if(filteringValue instanceof Boolean){
            formattedValue=""+((Boolean) filteringValue).booleanValue();
        }else if(filteringValue instanceof Integer){
            formattedValue=""+((Integer) filteringValue).intValue();
        }else if(filteringValue instanceof Short){
            formattedValue=""+((Short) filteringValue).shortValue();
        }else if(filteringValue instanceof Long){
            formattedValue=""+((Long) filteringValue).longValue();
        }else if(filteringValue instanceof Float){
            formattedValue=""+((Float) filteringValue).floatValue();
        }else if(filteringValue instanceof Double){
            formattedValue=""+((Double) filteringValue).doubleValue();
        }else if(filteringValue instanceof Date){
            Date filterDateValue = (Date) filteringValue;
            formattedValue=""+filterDateValue.getTime();
        }else if(filteringValue instanceof String){
            formattedValue="'"+filteringValue+"'";
        }else if(filteringValue instanceof Byte){
            formattedValue=""+((Byte) filteringValue).byteValue();
        }else{
            formattedValue=filteringValue.toString();
        }
        return formattedValue;
    }
}
