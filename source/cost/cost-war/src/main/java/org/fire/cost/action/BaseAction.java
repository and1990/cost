package org.fire.cost.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Controller;

/**
 * 分页基类 .
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
public abstract class BaseAction<T> extends ActionSupport {
    //分页对象
    protected PageData<T> pageData;
    //当前页
    protected int page;
    //每页显示条数
    protected int rows;

    public PageData<T> getPageData() {
        return pageData;
    }

    public void setPageData(PageData<T> pageData) {
        this.pageData = pageData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}