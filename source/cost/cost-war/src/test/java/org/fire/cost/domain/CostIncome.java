package org.fire.cost.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 注释：
 * 时间：2014年05月06日 下午2:16
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_income", schema = "", catalog = "cost_shasha")
public class CostIncome {
    private Long incomeId;
    private Integer incomeMoney;
    private Timestamp incomeTime;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private String incomeRemark;
    private CostUser user;

    @Id
    @Column(name = "income_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    @Basic
    @Column(name = "income_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Integer incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    @Basic
    @Column(name = "income_time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Timestamp incomeTime) {
        this.incomeTime = incomeTime;
    }

    @Basic
    @Column(name = "create_user", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_user", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "income_remark", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    public String getIncomeRemark() {
        return incomeRemark;
    }

    public void setIncomeRemark(String incomeRemark) {
        this.incomeRemark = incomeRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CostIncome that = (CostIncome) o;

        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (incomeId != null ? !incomeId.equals(that.incomeId) : that.incomeId != null) return false;
        if (incomeMoney != null ? !incomeMoney.equals(that.incomeMoney) : that.incomeMoney != null) return false;
        if (incomeRemark != null ? !incomeRemark.equals(that.incomeRemark) : that.incomeRemark != null) return false;
        if (incomeTime != null ? !incomeTime.equals(that.incomeTime) : that.incomeTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (modifyUser != null ? !modifyUser.equals(that.modifyUser) : that.modifyUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = incomeId != null ? incomeId.hashCode() : 0;
        result = 31 * result + (incomeMoney != null ? incomeMoney.hashCode() : 0);
        result = 31 * result + (incomeTime != null ? incomeTime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyUser != null ? modifyUser.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (incomeRemark != null ? incomeRemark.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public CostUser getUser() {
        return user;
    }

    public void setUser(CostUser user) {
        this.user = user;
    }
}
