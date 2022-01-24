package com.substation.entity.dto;

import com.substation.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName:  ElectricCapacityDto
 * @Author: zhengxin
 * @Description: 电容对象Dto
 * @Date: 2020-12-01 16:50:57
 * @Version: 1.0
 */
@ApiModel(value = "电容对象Dto", description = "从客户端，由电容传入的数据封装在此entity中")
public class ElectricCapacityDto extends BaseEntity {

    /**
     * 电容ID
     */
    @ApiModelProperty(value = "电容ID", name = "capacityId")
    private Integer capacityId;

    /**
     * AN相电压 单位：0.01V
     */
    @ApiModelProperty(value = "AN相电压 单位：0.01V", name = "uan")
    private Integer uan;

    /**
     * AB相线电压 单位：0.01V
     */
    @ApiModelProperty(value = "AB相线电压 单位：0.01V", name = "uab")
    private Integer uab;

    /**
     * A相电流 单位：0.0001A
     */
    @ApiModelProperty(value = "A相电流 单位：0.0001A", name = "ia")
    private Integer ia;

    /**
     * A相有功功率 单位：1W
     */
    @ApiModelProperty(value = "A相有功功率 单位：1W", name = "pa")
    private Integer pa;

    /**
     * A相功率因素 单位：0.001
     */
    @ApiModelProperty(value = "A相功率因素 单位：0.001", name = "pfa")
    private Integer pfa;

    /**
     * A相无功功率 单位：1Var
     */
    @ApiModelProperty(value = "A相无功功率 单位：1Var", name = "qa")
    private Integer qa;

    /**
     * A相视在功率 单位：1VA
     */
    @ApiModelProperty(value = "A相视在功率 单位：1VA", name = "sa")
    private Integer sa;

    /**
     * BN相电压 单位：0.01V
     */
    @ApiModelProperty(value = "BN相电压 单位：0.01V", name = "ubn")
    private Integer ubn;

    /**
     * BC线电压 单位：0.01V
     */
    @ApiModelProperty(value = "BC线电压 单位：0.01V", name = "ubc")
    private Integer ubc;

    /**
     * B相电流 单位：0.0001A
     */
    @ApiModelProperty(value = "B相电流 单位：0.0001A", name = "ib")
    private Integer ib;

    /**
     * B相有功功率 单位：1W
     */
    @ApiModelProperty(value = "B相有功功率 单位：1W", name = "pb")
    private Integer pb;

    /**
     * B相功率因素 单位：0.001
     */
    @ApiModelProperty(value = "B相功率因素 单位：0.001", name = "pfb")
    private Integer pfb;

    /**
     * B相无功功率 单位：1Var
     */
    @ApiModelProperty(value = "B相无功功率 单位：1Var", name = "qb")
    private Integer qb;

    /**
     * B相视在功率 单位：1VA
     */
    @ApiModelProperty(value = "B相视在功率 单位：1VA", name = "sb")
    private Integer sb;

    /**
     * CN相电压 单位：0.01V
     */
    @ApiModelProperty(value = "CN相电压 单位：0.01V", name = "ucn")
    private Integer ucn;

    /**
     * CA线电压 单位：0.01V
     */
    @ApiModelProperty(value = "CA线电压 单位：0.01V", name = "uca")
    private Integer uca;

    /**
     * C相电流 单位：0.0001A
     */
    @ApiModelProperty(value = "C相电流 单位：0.0001A", name = "ic")
    private Integer ic;

    /**
     * C相有功功率 单位：1W
     */
    @ApiModelProperty(value = "C相有功功率 单位：1W", name = "pc")
    private Integer pc;

    /**
     * C相功率因素 单位：0.001
     */
    @ApiModelProperty(value = "C相功率因素 单位：0.001", name = "pfc")
    private Integer pfc;

    /**
     * C相无功功率 单位：1Var
     */
    @ApiModelProperty(value = "C相无功功率 单位：1Var", name = "qc")
    private Integer qc;

    /**
     * C相视在功率 单位：1VA
     */
    @ApiModelProperty(value = "C相视在功率 单位：1VA", name = "sc")
    private Integer sc;

    /**
     * 电压均值 单位：0.01V
     */
    @ApiModelProperty(value = "电压均值 单位：0.01V", name = "uavg")
    private Integer uavg;

    /**
     * 电流均值 单位：0.0001A
     */
    @ApiModelProperty(value = "电流均值 单位：0.0001A", name = "lavg")
    private Integer lavg;

    /**
     * 频率 单位：0.01HZ
     */
    @ApiModelProperty(value = "频率 单位：0.01HZ", name = "frequency")
    private Integer frequency;

    /**
     * 总有功功率 单位：1W
     */
    @ApiModelProperty(value = "总有功功率 单位：1W", name = "totalActivePower")
    private Integer totalActivePower;

    /**
     * 总功率因素 单位：0.001
     */
    @ApiModelProperty(value = "总功率因素 单位：0.001", name = "pf")
    private Integer pf;

    /**
     * 总无功功率 单位：1Var
     */
    @ApiModelProperty(value = "总无功功率 单位：1Var", name = "totalReactivePower")
    private Integer totalReactivePower;

    /**
     * 总视在功率 单位：1VA
     */
    @ApiModelProperty(value = "总视在功率 单位：1VA", name = "totalApparentPower")
    private Integer totalApparentPower;

    /**
     * 电压不平衡度 单位：%
     */
    @ApiModelProperty(value = "电压不平衡度 单位：%", name = "voltageUnbalance")
    private String voltageUnbalance;

    /**
     * 电流不平衡度 单位：%
     */
    @ApiModelProperty(value = "电流不平衡度 单位：%", name = "currentUnbalance")
    private String currentUnbalance;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", name = "updateTime")
    private Date updateTime;

    public Integer getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Integer capacityId) {
        this.capacityId = capacityId;
    }

    public Integer getUan() {
        return uan;
    }

    public void setUan(Integer uan) {
        this.uan = uan;
    }

    public Integer getUab() {
        return uab;
    }

    public void setUab(Integer uab) {
        this.uab = uab;
    }

    public Integer getIa() {
        return ia;
    }

    public void setIa(Integer ia) {
        this.ia = ia;
    }

    public Integer getPa() {
        return pa;
    }

    public void setPa(Integer pa) {
        this.pa = pa;
    }

    public Integer getPfa() {
        return pfa;
    }

    public void setPfa(Integer pfa) {
        this.pfa = pfa;
    }

    public Integer getQa() {
        return qa;
    }

    public void setQa(Integer qa) {
        this.qa = qa;
    }

    public Integer getSa() {
        return sa;
    }

    public void setSa(Integer sa) {
        this.sa = sa;
    }

    public Integer getUbn() {
        return ubn;
    }

    public void setUbn(Integer ubn) {
        this.ubn = ubn;
    }

    public Integer getUbc() {
        return ubc;
    }

    public void setUbc(Integer ubc) {
        this.ubc = ubc;
    }

    public Integer getIb() {
        return ib;
    }

    public void setIb(Integer ib) {
        this.ib = ib;
    }

    public Integer getPb() {
        return pb;
    }

    public void setPb(Integer pb) {
        this.pb = pb;
    }

    public Integer getPfb() {
        return pfb;
    }

    public void setPfb(Integer pfb) {
        this.pfb = pfb;
    }

    public Integer getQb() {
        return qb;
    }

    public void setQb(Integer qb) {
        this.qb = qb;
    }

    public Integer getSb() {
        return sb;
    }

    public void setSb(Integer sb) {
        this.sb = sb;
    }

    public Integer getUcn() {
        return ucn;
    }

    public void setUcn(Integer ucn) {
        this.ucn = ucn;
    }

    public Integer getUca() {
        return uca;
    }

    public void setUca(Integer uca) {
        this.uca = uca;
    }

    public Integer getIc() {
        return ic;
    }

    public void setIc(Integer ic) {
        this.ic = ic;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public Integer getPfc() {
        return pfc;
    }

    public void setPfc(Integer pfc) {
        this.pfc = pfc;
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }

    public Integer getSc() {
        return sc;
    }

    public void setSc(Integer sc) {
        this.sc = sc;
    }

    public Integer getUavg() {
        return uavg;
    }

    public void setUavg(Integer uavg) {
        this.uavg = uavg;
    }

    public Integer getLavg() {
        return lavg;
    }

    public void setLavg(Integer lavg) {
        this.lavg = lavg;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getTotalActivePower() {
        return totalActivePower;
    }

    public void setTotalActivePower(Integer totalActivePower) {
        this.totalActivePower = totalActivePower;
    }

    public Integer getPf() {
        return pf;
    }

    public void setPf(Integer pf) {
        this.pf = pf;
    }

    public Integer getTotalReactivePower() {
        return totalReactivePower;
    }

    public void setTotalReactivePower(Integer totalReactivePower) {
        this.totalReactivePower = totalReactivePower;
    }

    public Integer getTotalApparentPower() {
        return totalApparentPower;
    }

    public void setTotalApparentPower(Integer totalApparentPower) {
        this.totalApparentPower = totalApparentPower;
    }

    public String getVoltageUnbalance() {
        return voltageUnbalance;
    }

    public void setVoltageUnbalance(String voltageUnbalance) {
        this.voltageUnbalance = voltageUnbalance;
    }

    public String getCurrentUnbalance() {
        return currentUnbalance;
    }

    public void setCurrentUnbalance(String currentUnbalance) {
        this.currentUnbalance = currentUnbalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", capacityId=").append(capacityId);
        sb.append(", uan=").append(uan);
        sb.append(", uab=").append(uab);
        sb.append(", ia=").append(ia);
        sb.append(", pa=").append(pa);
        sb.append(", pfa=").append(pfa);
        sb.append(", qa=").append(qa);
        sb.append(", sa=").append(sa);
        sb.append(", ubn=").append(ubn);
        sb.append(", ubc=").append(ubc);
        sb.append(", ib=").append(ib);
        sb.append(", pb=").append(pb);
        sb.append(", pfb=").append(pfb);
        sb.append(", qb=").append(qb);
        sb.append(", sb=").append(sb);
        sb.append(", ucn=").append(ucn);
        sb.append(", uca=").append(uca);
        sb.append(", ic=").append(ic);
        sb.append(", pc=").append(pc);
        sb.append(", pfc=").append(pfc);
        sb.append(", qc=").append(qc);
        sb.append(", sc=").append(sc);
        sb.append(", uavg=").append(uavg);
        sb.append(", lavg=").append(lavg);
        sb.append(", frequency=").append(frequency);
        sb.append(", totalActivePower=").append(totalActivePower);
        sb.append(", pf=").append(pf);
        sb.append(", totalReactivePower=").append(totalReactivePower);
        sb.append(", totalApparentPower=").append(totalApparentPower);
        sb.append(", voltageUnbalance=").append(voltageUnbalance);
        sb.append(", currentUnbalance=").append(currentUnbalance);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

}