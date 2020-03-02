package com.iot.model.resultAndPage;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResultBean<T> implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6477868212171605239L;

    /**
     * 总记录数
     */
    private Long totalelements;

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer allPages;

    public PageResultBean(List<T> rows) {
        init(rows);
    }

    /**
     * 初始化
     *
     * @param rows
     */
    private void init(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.totalelements = page.getTotal();
            this.rows = page.getResult();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.allPages = page.getPages();
        }
    }
}
