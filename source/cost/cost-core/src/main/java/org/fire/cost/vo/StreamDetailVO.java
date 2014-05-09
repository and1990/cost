package org.fire.cost.vo;

import java.math.BigDecimal;

/**
 * 注释：流水明细VO
 * 时间：2014年05月09日 上午11:52
 * 作者：刘腾飞[liutengfei]
 */
public class StreamDetailVO {
    private String date;
    private int type;
    private String typeName;
    private BigDecimal money;
    private String remark;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
