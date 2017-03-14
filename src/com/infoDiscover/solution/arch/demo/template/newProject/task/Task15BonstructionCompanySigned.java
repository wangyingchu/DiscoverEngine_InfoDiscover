package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task15BonstructionCompanySigned extends NewProjectBaseTask {
    //String departmentId = "General_Manager";
    String shigongdanweijishufuzeren; // 施工单位技术负责人
    String shigongdanwei; // 施工单位名称
    Date shigonghetongqiandingriqi; // 施工合同签订日期
    double shigonghetongjiner; // 施工合同金额
    String shigonglianxiren; // 施工单位联系人
    String shigonghetongbianhao; // 施工合同编号
    Date shigonghetongkaishiriqi; // 施工合同开始日期
    Date shigonghetongjieshuriqi; // 施工合同结束日期

    public Task15BonstructionCompanySigned(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getShigongdanweijishufuzeren() {
        return shigongdanweijishufuzeren;
    }

    public void setShigongdanweijishufuzeren(String shigongdanweijishufuzeren) {
        this.shigongdanweijishufuzeren = shigongdanweijishufuzeren;
    }

    public String getShigongdanwei() {
        return shigongdanwei;
    }

    public void setShigongdanwei(String shigongdanwei) {
        this.shigongdanwei = shigongdanwei;
    }

    public Date getShigonghetongqiandingriqi() {
        return shigonghetongqiandingriqi;
    }

    public void setShigonghetongqiandingriqi(Date shigonghetongqiandingriqi) {
        this.shigonghetongqiandingriqi = shigonghetongqiandingriqi;
    }

    public double getShigonghetongjiner() {
        return shigonghetongjiner;
    }

    public void setShigonghetongjiner(double shigonghetongjiner) {
        this.shigonghetongjiner = shigonghetongjiner;
    }

    public String getShigonglianxiren() {
        return shigonglianxiren;
    }

    public void setShigonglianxiren(String shigonglianxiren) {
        this.shigonglianxiren = shigonglianxiren;
    }

    public String getShigonghetongbianhao() {
        return shigonghetongbianhao;
    }

    public void setShigonghetongbianhao(String shigonghetongbianhao) {
        this.shigonghetongbianhao = shigonghetongbianhao;
    }

    public Date getShigonghetongkaishiriqi() {
        return shigonghetongkaishiriqi;
    }

    public void setShigonghetongkaishiriqi(Date shigonghetongkaishiriqi) {
        this.shigonghetongkaishiriqi = shigonghetongkaishiriqi;
    }

    public Date getShigonghetongjieshuriqi() {
        return shigonghetongjieshuriqi;
    }

    public void setShigonghetongjieshuriqi(Date shigonghetongjieshuriqi) {
        this.shigonghetongjieshuriqi = shigonghetongjieshuriqi;
    }
}
