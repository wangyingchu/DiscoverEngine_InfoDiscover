package com.viewfnction.infoDiscoverEngine.dataWarehouse.InformationFiltering;

public class NullValueFilteringItem implements FilteringItem{
    private boolean reverseCondition=false;
    private String attributeName;
    public NullValueFilteringItem(String attributeName){
        this.attributeName=attributeName;
    }

    @Override
    public String getFilteringLogic() {
        String filteringLogic=attributeName+" IS null";
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
