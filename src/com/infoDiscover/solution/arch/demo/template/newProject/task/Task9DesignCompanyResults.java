package com.infoDiscover.solution.arch.demo.template.newProject.task;

/**
 * Created by sun.
 */
public class Task9DesignCompanyResults extends  NewProjectBaseTask {
    //String departmentId = "Engineering_Department";
    double dixiajianzhumianji; // 地下建筑面积
    String jishurenyuanxinxi; // 设计单位技术负责人
    double lvhualv; // 绿化率
    double jiansheyongdi; // 建设用地面积
    String yongdixingzhi; // 用地性质
    String zhuyaogongneng; // 主要功能或建筑类型
    int tichengshu; // 机动车停车数
    String shejirenyuan; // 勘察设计人员
    double zhandilv; // 占地率
    double rongjiliang; // 容积率
    int dishangjianzhuceng; // 地上建筑层数
    int dixiajianzhuceng; // 地下建筑层数
    double jianzhugaodu; // 建筑高度

    public Task9DesignCompanyResults(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getDixiajianzhumianji() {
        return dixiajianzhumianji;
    }

    public void setDixiajianzhumianji(double dixiajianzhumianji) {
        this.dixiajianzhumianji = dixiajianzhumianji;
    }

    public String getJishurenyuanxinxi() {
        return jishurenyuanxinxi;
    }

    public void setJishurenyuanxinxi(String jishurenyuanxinxi) {
        this.jishurenyuanxinxi = jishurenyuanxinxi;
    }

    public double getLvhualv() {
        return lvhualv;
    }

    public void setLvhualv(double lvhualv) {
        this.lvhualv = lvhualv;
    }

    public double getJiansheyongdi() {
        return jiansheyongdi;
    }

    public void setJiansheyongdi(double jiansheyongdi) {
        this.jiansheyongdi = jiansheyongdi;
    }

    public String getYongdixingzhi() {
        return yongdixingzhi;
    }

    public void setYongdixingzhi(String yongdixingzhi) {
        this.yongdixingzhi = yongdixingzhi;
    }

    public String getZhuyaogongneng() {
        return zhuyaogongneng;
    }

    public void setZhuyaogongneng(String zhuyaogongneng) {
        this.zhuyaogongneng = zhuyaogongneng;
    }

    public int getTichengshu() {
        return tichengshu;
    }

    public void setTichengshu(int tichengshu) {
        this.tichengshu = tichengshu;
    }

    public String getShejirenyuan() {
        return shejirenyuan;
    }

    public void setShejirenyuan(String shejirenyuan) {
        this.shejirenyuan = shejirenyuan;
    }

    public double getZhandilv() {
        return zhandilv;
    }

    public void setZhandilv(double zhandilv) {
        this.zhandilv = zhandilv;
    }

    public double getRongjiliang() {
        return rongjiliang;
    }

    public void setRongjiliang(double rongjiliang) {
        this.rongjiliang = rongjiliang;
    }

    public int getDishangjianzhuceng() {
        return dishangjianzhuceng;
    }

    public void setDishangjianzhuceng(int dishangjianzhuceng) {
        this.dishangjianzhuceng = dishangjianzhuceng;
    }

    public int getDixiajianzhuceng() {
        return dixiajianzhuceng;
    }

    public void setDixiajianzhuceng(int dixiajianzhuceng) {
        this.dixiajianzhuceng = dixiajianzhuceng;
    }

    public double getJianzhugaodu() {
        return jianzhugaodu;
    }

    public void setJianzhugaodu(double jianzhugaodu) {
        this.jianzhugaodu = jianzhugaodu;
    }
}
