package com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering;

import com.infoDiscover.infoDiscoverEngine.dataWarehouse.SQLBuilder;

public class BetweenFilteringItem implements FilteringItem{
    private boolean reverseCondition=false;
    private String attributeName;
    private Object attributeFromValue;
    private Object attributeToValue;
    public BetweenFilteringItem(String attributeName, Object attributeFromValue,Object attributeToValue){
        this.attributeName=attributeName;
        this.attributeFromValue=attributeFromValue;
        this.attributeToValue=attributeToValue;
    }

    @Override
    public String getFilteringLogic() {
        String filteringFromValueStr= SQLBuilder.formatFilteringValue(attributeFromValue);
        String filteringToValueStr= SQLBuilder.formatFilteringValue(attributeToValue);
        String filteringLogic=attributeName+" BETWEEN " + filteringFromValueStr +" AND "+filteringToValueStr;
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
