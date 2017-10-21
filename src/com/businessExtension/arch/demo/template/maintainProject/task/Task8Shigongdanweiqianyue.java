package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task8Shigongdanweiqianyue extends MaintainProjectBaseTask {
    //    String departmentId = "Engineering_Department";
    private String shigongdanwei; // 施工单位名称
    private Date shigonghetongqizhiriqi_start; // 合同启始日期
    private Date shigonghetongjiezhiriqi_end; // 合同截止日期
    private String shigongdanweijishurenyuan; // 施工单位技术负责人
    private double shigonghetongjine; // 合同金额
    private String shigonghetongbianhao; // 合同编号
    private String shigongdanweilianxiren; // 施工单位联系人
    private Date shigonghetongqiandingriqi; // 合同签订日期

    public Task8Shigongdanweiqianyue(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getShigongdanwei() {
        return shigongdanwei;
    }

    public void setShigongdanwei(String shigongdanwei) {
        this.shigongdanwei = shigongdanwei;
    }

    public Date getShigonghetongqizhiriqi_start() {
        return shigonghetongqizhiriqi_start;
    }

    public void setShigonghetongqizhiriqi_start(Date shigonghetongqizhiriqi_start) {
        this.shigonghetongqizhiriqi_start = shigonghetongqizhiriqi_start;
    }

    public Date getShigonghetongjiezhiriqi_end() {
        return shigonghetongjiezhiriqi_end;
    }

    public void setShigonghetongjiezhiriqi_end(Date shigonghetongjiezhiriqi_end) {
        this.shigonghetongjiezhiriqi_end = shigonghetongjiezhiriqi_end;
    }

    public String getShigongdanweijishurenyuan() {
        return shigongdanweijishurenyuan;
    }

    public void setShigongdanweijishurenyuan(String shigongdanweijishurenyuan) {
        this.shigongdanweijishurenyuan = shigongdanweijishurenyuan;
    }

    public double getShigonghetongjine() {
        return shigonghetongjine;
    }

    public void setShigonghetongjine(double shigonghetongjine) {
        this.shigonghetongjine = shigonghetongjine;
    }

    public String getShigonghetongbianhao() {
        return shigonghetongbianhao;
    }

    public void setShigonghetongbianhao(String shigonghetongbianhao) {
        this.shigonghetongbianhao = shigonghetongbianhao;
    }

    public String getShigongdanweilianxiren() {
        return shigongdanweilianxiren;
    }

    public void setShigongdanweilianxiren(String shigongdanweilianxiren) {
        this.shigongdanweilianxiren = shigongdanweilianxiren;
    }

    public Date getShigonghetongqiandingriqi() {
        return shigonghetongqiandingriqi;
    }

    public void setShigonghetongqiandingriqi(Date shigonghetongqiandingriqi) {
        this.shigonghetongqiandingriqi = shigonghetongqiandingriqi;
    }
}
