package org.fire.cost.vo;

import java.util.UUID;

public class Message {
    /**
     * 返回结果，1：成功，2：失败
     */
    private int result;
    //状态码
    private int status;
    //返回数据
    private Object data;
    //异常信息
    private String descMsg;
    //uuid返回数据的唯一标识符
    private String uuid = UUID.randomUUID().toString();

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDescMsg() {
        return descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
