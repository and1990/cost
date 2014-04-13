package org.fire.cost.vo;

import java.util.List;

/**
 * 注释：分页对象
 * 时间：2014-04-12 上午11:37
 * 作者：liutengfei【刘腾飞】
 */
public class PageData<T> {
    //当前页
    private int page = PageData.DEFAULT_PAGE;
    //每页显示条数
    private int pageSize = PageData.DEFAULT_PAGE_SIZE;
    //总页数
    private int pages;
    //总记录数
    private int total;
    //当前页数据
    private List<T> rows;

    //默认显示第一页
    private final static int DEFAULT_PAGE = 1;
    //默认显示条数
    private final static int DEFAULT_PAGE_SIZE = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
