package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task6TenderAgentSigned extends NewProjectBaseTask {
    // String departmentId = "General_Manager";
    double zhaobiaodailihetongjine; // 招标代理合同金额
    Date zhaobiaodailihetongqiandingriqi; // 招标代理合同签订时间
    String zhaobiaodailihetongbianhao; // 招标代理合同编号
    String zhaobiaodaililianxiren; // 招标代理单位联系人
    String zhaobiaodaili; // 招标代理单位名称
    Date zhaobiaodailihetongkaishiriqi; // 招标代理合同开始日期
    Date zhaobiaodailihetongjieshuriqi; // 招标代理合同结束日期

    public Task6TenderAgentSigned(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getZhaobiaodailihetongjine() {
        return zhaobiaodailihetongjine;
    }

    public void setZhaobiaodailihetongjine(double zhaobiaodailihetongjine) {
        this.zhaobiaodailihetongjine = zhaobiaodailihetongjine;
    }

    public Date getZhaobiaodailihetongqiandingriqi() {
        return zhaobiaodailihetongqiandingriqi;
    }

    public void setZhaobiaodailihetongqiandingriqi(Date zhaobiaodailihetongqiandingriqi) {
        this.zhaobiaodailihetongqiandingriqi = zhaobiaodailihetongqiandingriqi;
    }

    public String getZhaobiaodailihetongbianhao() {
        return zhaobiaodailihetongbianhao;
    }

    public void setZhaobiaodailihetongbianhao(String zhaobiaodailihetongbianhao) {
        this.zhaobiaodailihetongbianhao = zhaobiaodailihetongbianhao;
    }

    public String getZhaobiaodaililianxiren() {
        return zhaobiaodaililianxiren;
    }

    public void setZhaobiaodaililianxiren(String zhaobiaodaililianxiren) {
        this.zhaobiaodaililianxiren = zhaobiaodaililianxiren;
    }

    public String getZhaobiaodaili() {
        return zhaobiaodaili;
    }

    public void setZhaobiaodaili(String zhaobiaodaili) {
        this.zhaobiaodaili = zhaobiaodaili;
    }

    public Date getZhaobiaodailihetongkaishiriqi() {
        return zhaobiaodailihetongkaishiriqi;
    }

    public void setZhaobiaodailihetongkaishiriqi(Date zhaobiaodailihetongkaishiriqi) {
        this.zhaobiaodailihetongkaishiriqi = zhaobiaodailihetongkaishiriqi;
    }

    public Date getZhaobiaodailihetongjieshuriqi() {
        return zhaobiaodailihetongjieshuriqi;
    }

    public void setZhaobiaodailihetongjieshuriqi(Date zhaobiaodailihetongjieshuriqi) {
        this.zhaobiaodailihetongjieshuriqi = zhaobiaodailihetongjieshuriqi;
    }
}
