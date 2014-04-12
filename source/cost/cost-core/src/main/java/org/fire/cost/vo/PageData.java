package org.fire.cost.vo;

import java.util.List;

/**
 * 注释：分页对象
 * 时间：2014-04-12 上午11:37
 * 作者：liutengfei【刘腾飞】
 */
public class PageData<T> {
    //当前页
    private int pageIndex = PageData.DEFAULT_PAGE_INDEX;
    //每页显示条数
    private int pageSize = PageData.DEFAULT_PAGE_SIZE;
    //总页数
    private int pageTotal;
    //总记录数
    private int dataTotal;
    //当前页数据
    private List<T> dataList;

    //默认显示第一页
    private final static int DEFAULT_PAGE_INDEX = 1;
    //默认显示条数
    private final static int DEFAULT_PAGE_SIZE = 10;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal() {
        if (dataTotal != 0) {
            int page = dataTotal / pageSize;
            this.pageTotal = dataTotal % pageSize == 0 ? page : page + 1;
        } else {
            this.pageTotal = 1;
        }
    }

    public int getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(int dataTotal) {
        this.dataTotal = dataTotal;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
