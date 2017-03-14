package com.infoDiscover.solution.arch.demo.template.maintainProject.task;

import com.infoDiscover.solution.arch.demo.template.maintainProject.MaintainProjectProgress;

/**
 * Created by sun.
 */
public class Task7Yusuanzaojiashenpi extends MaintainProjectBaseTask {

    //String departmentId = "Engineering_Department";

    double yusuanzaojia; //　预算造价(审定价)　DOUBLE
    double yusuanhezenge; //预算核增额 DOUBLE
    String yusuanshenhedanwei; //工程预算审核单位 STRING

    public Task7Yusuanzaojiashenpi(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getYusuanzaojia() {
        return yusuanzaojia;
    }

    public void setYusuanzaojia(double yusuanzaojia) {
        this.yusuanzaojia = yusuanzaojia;
    }

    public double getYusuanhezenge() {
        return yusuanhezenge;
    }

    public void setYusuanhezenge(double yusuanhezenge) {
        this.yusuanhezenge = yusuanhezenge;
    }

    public String getYusuanshenhedanwei() {
        return yusuanshenhedanwei;
    }

    public void setYusuanshenhedanwei(String yusuanshenhedanwei) {
        this.yusuanshenhedanwei = yusuanshenhedanwei;
    }
}
