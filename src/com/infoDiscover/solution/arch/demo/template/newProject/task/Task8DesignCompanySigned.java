package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task8DesignCompanySigned extends NewProjectBaseTask {

    // String departmentId = "General_Manager";

    String shejidanweimingcheng; // 设计单位名称
    String shejidanweilianxiren; // 设计单位联系人
    Date shejiqiandingriqi; // 设计合同签订日期
    String shejihetongbianhao; // 设计合同编号
    double shejiheotongjine; // 设计合同金额
    Date shejihetongkaishiriqi; // 设计合同开始日期
    Date shejihetongjieshuriqi; // 设计合同结束日期

    public Task8DesignCompanySigned(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getShejidanweimingcheng() {
        return shejidanweimingcheng;
    }

    public void setShejidanweimingcheng(String shejidanweimingcheng) {
        this.shejidanweimingcheng = shejidanweimingcheng;
    }

    public String getShejidanweilianxiren() {
        return shejidanweilianxiren;
    }

    public void setShejidanweilianxiren(String shejidanweilianxiren) {
        this.shejidanweilianxiren = shejidanweilianxiren;
    }

    public Date getShejiqiandingriqi() {
        return shejiqiandingriqi;
    }

    public void setShejiqiandingriqi(Date shejiqiandingriqi) {
        this.shejiqiandingriqi = shejiqiandingriqi;
    }

    public String getShejihetongbianhao() {
        return shejihetongbianhao;
    }

    public void setShejihetongbianhao(String shejihetongbianhao) {
        this.shejihetongbianhao = shejihetongbianhao;
    }

    public double getShejiheotongjine() {
        return shejiheotongjine;
    }

    public void setShejiheotongjine(double shejiheotongjine) {
        this.shejiheotongjine = shejiheotongjine;
    }

    public Date getShejihetongkaishiriqi() {
        return shejihetongkaishiriqi;
    }

    public void setShejihetongkaishiriqi(Date shejihetongkaishiriqi) {
        this.shejihetongkaishiriqi = shejihetongkaishiriqi;
    }

    public Date getShejihetongjieshuriqi() {
        return shejihetongjieshuriqi;
    }

    public void setShejihetongjieshuriqi(Date shejihetongjieshuriqi) {
        this.shejihetongjieshuriqi = shejihetongjieshuriqi;
    }
}
