package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task12CostConsultingCompanySigned extends NewProjectBaseTask {

    // String departmentId = "General_Manager";
    String zaojiadanwei; // 造价咨询单位名称
    String zaojiazixunlianxiren; // 造价咨询单位联系人
    double zaojiazixunhetongjine; // 造价咨询合同金额
    String zaojiazixunhetongbianhao; // 造价咨询合同编号
    Date zaojiazixunhetongqiandingriqi; // 造价咨询合同签订日期
    Date zaojiazixunhetongkaishiriqi; // 造价咨询合同开始日期
    Date zaojiazixunhetongjieshuriqi; // 造价咨询合同结束日期

    public Task12CostConsultingCompanySigned(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getZaojiadanwei() {
        return zaojiadanwei;
    }

    public void setZaojiadanwei(String zaojiadanwei) {
        this.zaojiadanwei = zaojiadanwei;
    }

    public String getZaojiazixunlianxiren() {
        return zaojiazixunlianxiren;
    }

    public void setZaojiazixunlianxiren(String zaojiazixunlianxiren) {
        this.zaojiazixunlianxiren = zaojiazixunlianxiren;
    }

    public double getZaojiazixunhetongjine() {
        return zaojiazixunhetongjine;
    }

    public void setZaojiazixunhetongjine(double zaojiazixunhetongjine) {
        this.zaojiazixunhetongjine = zaojiazixunhetongjine;
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

    public Date getZaojiazixunhetongkaishiriqi() {
        return zaojiazixunhetongkaishiriqi;
    }

    public void setZaojiazixunhetongkaishiriqi(Date zaojiazixunhetongkaishiriqi) {
        this.zaojiazixunhetongkaishiriqi = zaojiazixunhetongkaishiriqi;
    }

    public Date getZaojiazixunhetongjieshuriqi() {
        return zaojiazixunhetongjieshuriqi;
    }

    public void setZaojiazixunhetongjieshuriqi(Date zaojiazixunhetongjieshuriqi) {
        this.zaojiazixunhetongjieshuriqi = zaojiazixunhetongjieshuriqi;
    }
}
