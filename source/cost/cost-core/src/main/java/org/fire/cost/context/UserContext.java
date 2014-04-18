package org.fire.cost.context;

/**
 * 用户信息上下文
 *
 * @author liutengfei
 */
public class UserContext {

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 全局uuid
     */
    private String uuid;
    /**
     * 用户类型
     */
    private short userType;
    /**
     * 用户名称
     */
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public short getUserType() {
        return userType;
    }

    public void setUserType(short userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
