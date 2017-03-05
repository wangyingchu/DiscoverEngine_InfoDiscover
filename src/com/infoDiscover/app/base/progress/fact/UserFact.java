package com.infoDiscover.app.base.progress.fact;

/**
 * Created by sun.
 */
public class UserFact {
    String userId;
    String userName;

    public UserFact(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
