package org.fire.cost.vo;

import java.io.Serializable;

/**
 * @author liutengfei
 */
public class GroupVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long groupId;

    private String groupName;

    private Integer groupStatus;

    private String groupStatusName;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    private String groupRemark;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getGroupStatusName() {
        return groupStatusName;
    }

    public void setGroupStatusName(String groupStatusName) {
        this.groupStatusName = groupStatusName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }
}
