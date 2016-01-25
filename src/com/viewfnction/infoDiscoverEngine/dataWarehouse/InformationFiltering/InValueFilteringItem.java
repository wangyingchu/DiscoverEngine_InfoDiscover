package com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering;

import com.viewfnction.infoDiscoverEngine.dataWarehouse.SQLBuilder;

import java.util.List;

public class InValueFilteringItem implements FilteringItem{
    private boolean reverseCondition=false;
    private String attributeName;
    private List<Object> attributeValue;

    public InValueFilteringItem(String attributeName,List<Object> attributeValue){
        this.attributeName=attributeName;
        this.attributeValue=attributeValue;
    }

    @Override
    public String getFilteringLogic() {
        StringBuffer inPartBuffer=new StringBuffer();
        inPartBuffer.append("[");

        for(int i=0;i<attributeValue.size();i++){
            Object currentValue=attributeValue.get(i);
            String filteringValueStr= SQLBuilder.formatFilteringValue(currentValue);
            inPartBuffer.append(filteringValueStr);
            if(i<attributeValue.size()-1){
                inPartBuffer.append(",");
            }
        }
        inPartBuffer.append("]");

        String filteringLogic=attributeName+" IN "+inPartBuffer.toString();
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
