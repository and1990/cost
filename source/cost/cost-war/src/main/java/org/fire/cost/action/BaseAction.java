package org.fire.cost.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

/**
 * 分页基类 .
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
public abstract class BaseAction extends ActionSupport {
    //返回客户端信息
    protected Message message = new Message();

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}