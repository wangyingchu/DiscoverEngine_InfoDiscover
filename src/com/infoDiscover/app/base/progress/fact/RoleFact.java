package com.infoDiscover.app.base.progress.fact;

/**
 * Created by sun.
 */
public class RoleFact {
    String roleId;
    String roleName;

    public RoleFact(String roleId, String roleName) {
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
