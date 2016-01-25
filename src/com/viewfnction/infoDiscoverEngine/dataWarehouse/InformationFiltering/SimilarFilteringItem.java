package com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering;

public class SimilarFilteringItem implements FilteringItem{

    public enum MatchingType{
        BeginWith,EndWith,Contain
    }
    private boolean reverseCondition=false;
    private String attributeName;
    private String attributeValue;
    private MatchingType matchingType;

    public SimilarFilteringItem(String attributeName, String attributeValue,MatchingType matchingType){
        this.attributeName=attributeName;
        this.attributeValue=attributeValue;
        this.matchingType=matchingType;
    }

    @Override
    public String getFilteringLogic() {
        String filteringValueStr="";
        switch(matchingType){
            case BeginWith:
                filteringValueStr="'"+attributeValue+"%'";
                break;
            case EndWith:
                filteringValueStr="'%"+attributeValue+"'";
                break;
            case Contain:
                filteringValueStr="'%"+attributeValue+"%'";
                break;
        }
        String filteringLogic=attributeName+" LIKE " + filteringValueStr;
        if(!reverseCondition){
            return filteringLogic;
        }else{
            return "NOT("+filteringLogic+")";
        }
    }

    @Override
    public void reverseCondition() {
        this.reverseCondition=true;
    }
}
