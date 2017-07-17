package com.infoDiscover.solution.template.POJO;

/**
 * Created by sun on 7/17/17.
 */
public class FactTypeDefinitionPOJO extends TypeDefinitionPOJO {
    private String factTypeName;
    private String factTypeAliasName;

    public FactTypeDefinitionPOJO(String solutionName, String factTypeName, String factTypeAliasName) {
        super(solutionName);
        this.factTypeName = factTypeName;
        this.factTypeAliasName = factTypeAliasName;
    }

    public String getFactTypeName() {
        return factTypeName;
    }

    public void setFactTypeName(String factTypeName) {
        this.factTypeName = factTypeName;
    }

    public String getFactTypeAliasName() {
        return factTypeAliasName;
    }

    public void setFactTypeAliasName(String factTypeAliasName) {
        this.factTypeAliasName = factTypeAliasName;
    }
}
