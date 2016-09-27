package com.infoDiscover.infoDiscoverEngine.dataWarehouse;

import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.FilteringItem;

import java.util.ArrayList;
import java.util.List;

public class ExploreParameters {
    private int pageSize;
    private int startPage;
    private int endPage;
    private int resultNumber;
    private FilteringItem defaultFilteringItem;
    private String type;
    private List<String> relatedRelationTypesList;
    private boolean distinctMode=true;

    public enum FilteringLogic{
        AND,OR
    }

    private List<FilteringItem> andFilteringItemList;
    private List<FilteringItem> orFilteringItemList;

    public ExploreParameters(){
        andFilteringItemList=new ArrayList<>();
        orFilteringItemList=new ArrayList<>();
        this.relatedRelationTypesList=new ArrayList<>();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    public void setDefaultFilteringItem(FilteringItem filteringItem){
        this.defaultFilteringItem=filteringItem;
    }

    public void addFilteringItem(FilteringItem filteringItem,FilteringLogic filteringLogic){
        if(defaultFilteringItem==null){
            this.defaultFilteringItem=filteringItem;
        }else{
            switch(filteringLogic){
                case AND:
                    this.andFilteringItemList.add(filteringItem);
                    break;
                case OR:
                    this.orFilteringItemList.add(filteringItem);
                    break;
            }
        }
    }

    public FilteringItem getDefaultFilteringItem() {
        return defaultFilteringItem;
    }

    public List<FilteringItem> getAndFilteringItemsList(){
        return andFilteringItemList;
    }

    public List<FilteringItem> getOrFilteringItemsList(){
        return orFilteringItemList;
    }

    public void addRelatedRelationType(String typeName){
        this.relatedRelationTypesList.add(typeName);
    }

    public List<String> getRelatedRelationTypesList(){
        return relatedRelationTypesList;
    }

    public boolean isDistinctMode() {
        return distinctMode;
    }

    public void setDistinctMode(boolean distinctMode) {
        this.distinctMode = distinctMode;
    }
}
