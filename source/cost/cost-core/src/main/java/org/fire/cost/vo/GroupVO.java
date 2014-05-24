package org.fire.cost.vo;

import java.io.Serializable;

/**
 * @author liutengfei
 */
public class GroupVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long groupId;

    private String groupName;

    private String userIds;

    private String userNames;

    private Integer groupType;

    private String groupTypeName;

    private Integer groupStatus;

    private String groupStatusName;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    private String groupRemark;

    //是否分页
    private boolean isPage;

    public GroupVO() {

    }

    public GroupVO(Long groupId, String groupName, String userIds, int groupStatus, String groupRemark) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userIds = userIds;
        this.groupStatus = groupStatus;
        this.groupRemark = groupRemark;
    }

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

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getGroupTypeName() {
        return groupTypeName;
    }

    public void setGroupTypeName(String groupTypeName) {
        this.groupTypeName = groupTypeName;
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

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean isPage) {
        this.isPage = isPage;
    }
}
