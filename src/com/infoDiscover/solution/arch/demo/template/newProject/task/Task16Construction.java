package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task16Construction extends NewProjectBaseTask {
    // String departmentId = "Engineering_Department";
    String jishufuzren; // 施工单位技术负责人
    String jianlidanwei; // 监理单位
    Date kaigongriqi; // 开工日期
    long gongqi; // 工期
    String jianlidanweilianxiren; // 监理单位联系人

    public Task16Construction(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getJishufuzren() {
        return jishufuzren;
    }

    public void setJishufuzren(String jishufuzren) {
        this.jishufuzren = jishufuzren;
    }

    public String getJianlidanwei() {
        return jianlidanwei;
    }

    public void setJianlidanwei(String jianlidanwei) {
        this.jianlidanwei = jianlidanwei;
    }

    public Date getKaigongriqi() {
        return kaigongriqi;
    }

    public void setKaigongriqi(Date kaigongriqi) {
        this.kaigongriqi = kaigongriqi;
    }

    public long getGongqi() {
        return gongqi;
    }

    public void setGongqi(long gongqi) {
        this.gongqi = gongqi;
    }

    public String getJianlidanweilianxiren() {
        return jianlidanweilianxiren;
    }

    public void setJianlidanweilianxiren(String jianlidanweilianxiren) {
        this.jianlidanweilianxiren = jianlidanweilianxiren;
    }
}
