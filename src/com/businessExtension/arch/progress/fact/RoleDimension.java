package com.businessExtension.arch.progress.fact;

/**
 * Created by sun.
 */
public class RoleDimension {
    String roleId;
    String roleName;

    public RoleDimension(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
