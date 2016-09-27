package com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering;

import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;

public class EqualFilteringItem implements FilteringItem{
    private boolean reverseCondition=false;
    private String attributeName;
    private Object attributeValue;
    public EqualFilteringItem(String attributeName, Object attributeValue){
        this.attributeName=attributeName;
        this.attributeValue=attributeValue;
    }

    @Override
    public String getFilteringLogic() {
        String filteringValueStr= SQLBuilder.formatFilteringValue(attributeValue);
        String filteringLogic=attributeName+" = " + filteringValueStr;
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
