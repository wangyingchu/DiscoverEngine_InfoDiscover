package com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering;

import com.viewfnction.infoDiscoverEngine.dataWarehouse.SQLBuilder;

public class LessThanEqualFilteringItem implements FilteringItem{
    private boolean reverseCondition=false;
    private String attributeName;
    private Object attributeValue;
    public LessThanEqualFilteringItem(String attributeName, Object attributeValue){
        this.attributeName=attributeName;
        this.attributeValue=attributeValue;
    }

    @Override
    public String getFilteringLogic() {
        String filteringValueStr= SQLBuilder.formatFilteringValue(attributeValue);
        String filteringLogic=attributeName+" <= " + filteringValueStr;
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
