package com.businessExtension.arch.demo.template.maintainProject.task;

/**
 * Created by sun.
 */
public class Task11ProjectEnd extends MaintainProjectBaseTask {
    //String departmentId = "Engineering_Department";
    private String wuyebushenpi; // 物业部审批意见
    private String caiwuzongjian; // 区财务总监审批意见
    private String zongjinglishenpi; // 总经理审批意见
    private String gongchengbushenpi; // 工程部审批意见
    private String renwuxiadajushi; // 任务下达局师审批意见

    public Task11ProjectEnd(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getWuyebushenpi() {
        return wuyebushenpi;
    }

    public void setWuyebushenpi(String wuyebushenpi) {
        this.wuyebushenpi = wuyebushenpi;
    }

    public String getCaiwuzongjian() {
        return caiwuzongjian;
    }

    public void setCaiwuzongjian(String caiwuzongjian) {
        this.caiwuzongjian = caiwuzongjian;
    }

    public String getZongjinglishenpi() {
        return zongjinglishenpi;
    }

    public void setZongjinglishenpi(String zongjinglishenpi) {
        this.zongjinglishenpi = zongjinglishenpi;
    }

    public String getGongchengbushenpi() {
        return gongchengbushenpi;
    }

    public void setGongchengbushenpi(String gongchengbushenpi) {
        this.gongchengbushenpi = gongchengbushenpi;
    }

    public String getRenwuxiadajushi() {
        return renwuxiadajushi;
    }

    public void setRenwuxiadajushi(String renwuxiadajushi) {
        this.renwuxiadajushi = renwuxiadajushi;
    }
}
