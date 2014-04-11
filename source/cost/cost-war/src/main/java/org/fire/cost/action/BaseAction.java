package org.fire.cost.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Controller;

/**
 * 分页基类 .
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
public abstract class BaseAction extends ActionSupport {}