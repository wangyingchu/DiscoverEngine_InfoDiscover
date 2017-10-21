package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task9Shigong extends MaintainProjectBaseTask {
    //    String departmentId = "Engineering_Department";
    private Date shigongxunshishijian; // 巡视时间
    private String shigongjianlidanwei; // 监理单位
    private String shigongjianlilianxiren; // 监理单位联系人
    private long shigonggongqi; // 工期 (天）
    private String shigongxunshirenyuan; // 巡视人员
    private Date kaigongriqi; // 开工日期

    public Task9Shigong(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getShigongxunshishijian() {
        return shigongxunshishijian;
    }

    public void setShigongxunshishijian(Date shigongxunshishijian) {
        this.shigongxunshishijian = shigongxunshishijian;
    }

    public String getShigongjianlidanwei() {
        return shigongjianlidanwei;
    }

    public void setShigongjianlidanwei(String shigongjianlidanwei) {
        this.shigongjianlidanwei = shigongjianlidanwei;
    }

    public String getShigongjianlilianxiren() {
        return shigongjianlilianxiren;
    }

    public void setShigongjianlilianxiren(String shigongjianlilianxiren) {
        this.shigongjianlilianxiren = shigongjianlilianxiren;
    }

    public long getShigonggongqi() {
        return shigonggongqi;
    }

    public void setShigonggongqi(long shigonggongqi) {
        this.shigonggongqi = shigonggongqi;
    }

    public String getShigongxunshirenyuan() {
        return shigongxunshirenyuan;
    }

    public void setShigongxunshirenyuan(String shigongxunshirenyuan) {
        this.shigongxunshirenyuan = shigongxunshirenyuan;
    }

    public Date getKaigongriqi() {
        return kaigongriqi;
    }

    public void setKaigongriqi(Date kaigongriqi) {
        this.kaigongriqi = kaigongriqi;
    }
}
