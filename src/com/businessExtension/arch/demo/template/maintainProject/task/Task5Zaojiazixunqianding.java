package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task5Zaojiazixunqianding extends MaintainProjectBaseTask {
//    String departmentId = "General_Manager";

    private  double zaojiazixunhetongjine; //合同金额　DOUBLE
    private String zaojiazixundanweimingcheng; //造价咨询单位名称　STRING
    private String zaojiazixunhetongbianhao; //合同编号　STRING
    private Date zaojiazixunhetongqiandingriqi; //合同签订日期　DATE

    public Task5Zaojiazixunqianding(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getZaojiazixunhetongjine() {
        return zaojiazixunhetongjine;
    }

    public void setZaojiazixunhetongjine(double zaojiazixunhetongjine) {
        this.zaojiazixunhetongjine = zaojiazixunhetongjine;
    }

    public String getZaojiazixundanweimingcheng() {
        return zaojiazixundanweimingcheng;
    }

    public void setZaojiazixundanweimingcheng(String zaojiazixundanweimingcheng) {
        this.zaojiazixundanweimingcheng = zaojiazixundanweimingcheng;
    }

    public String getZaojiazixunhetongbianhao() {
        return zaojiazixunhetongbianhao;
    }

    public void setZaojiazixunhetongbianhao(String zaojiazixunhetongbianhao) {
        this.zaojiazixunhetongbianhao = zaojiazixunhetongbianhao;
    }

    public Date getZaojiazixunhetongqiandingriqi() {
        return zaojiazixunhetongqiandingriqi;
    }

    public void setZaojiazixunhetongqiandingriqi(Date zaojiazixunhetongqiandingriqi) {
        this.zaojiazixunhetongqiandingriqi = zaojiazixunhetongqiandingriqi;
    }
}
