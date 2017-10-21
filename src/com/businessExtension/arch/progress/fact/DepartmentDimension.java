package com.businessExtension.arch.progress.fact;

/**
 * Created by sun.
 */
public class DepartmentDimension {
    private String departmentID;
    private String departmentName;
    private String description;

    public DepartmentDimension(String departmentID, String departmentName, String description) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.description = description;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
