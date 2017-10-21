package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task7InvestigationCompanySigned extends NewProjectBaseTask {
    // String departmentId = "General_Manager";
    double kanchahetonghetongjine; //合同金额 DOUBLE
    String kanchahetonghetongbianhao;// 合同编号 STRING
    Date kanchahetongqiandingriqi;// 合同签订日期 DATE
    String kanchadanwei;// 勘察单位名称 STRING
    String kanchadanweilianxiren;// 勘察单位联系人 STRING
    Date kanchahetongkaishiriqi;// 勘察合同开始日期 DATE
    Date kanchahetongjieshuriqi;// 勘察合同结束日期 DATE

    public Task7InvestigationCompanySigned(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getKanchahetonghetongjine() {
        return kanchahetonghetongjine;
    }

    public void setKanchahetonghetongjine(double kanchahetonghetongjine) {
        this.kanchahetonghetongjine = kanchahetonghetongjine;
    }

    public String getKanchahetonghetongbianhao() {
        return kanchahetonghetongbianhao;
    }

    public void setKanchahetonghetongbianhao(String kanchahetonghetongbianhao) {
        this.kanchahetonghetongbianhao = kanchahetonghetongbianhao;
    }

    public Date getKanchahetongqiandingriqi() {
        return kanchahetongqiandingriqi;
    }

    public void setKanchahetongqiandingriqi(Date kanchahetongqiandingriqi) {
        this.kanchahetongqiandingriqi = kanchahetongqiandingriqi;
    }

    public String getKanchadanwei() {
        return kanchadanwei;
    }

    public void setKanchadanwei(String kanchadanwei) {
        this.kanchadanwei = kanchadanwei;
    }

    public String getKanchadanweilianxiren() {
        return kanchadanweilianxiren;
    }

    public void setKanchadanweilianxiren(String kanchadanweilianxiren) {
        this.kanchadanweilianxiren = kanchadanweilianxiren;
    }

    public Date getKanchahetongkaishiriqi() {
        return kanchahetongkaishiriqi;
    }

    public void setKanchahetongkaishiriqi(Date kanchahetongkaishiriqi) {
        this.kanchahetongkaishiriqi = kanchahetongkaishiriqi;
    }

    public Date getKanchahetongjieshuriqi() {
        return kanchahetongjieshuriqi;
    }

    public void setKanchahetongjieshuriqi(Date kanchahetongjieshuriqi) {
        this.kanchahetongjieshuriqi = kanchahetongjieshuriqi;
    }
}
