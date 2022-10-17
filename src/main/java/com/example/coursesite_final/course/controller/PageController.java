package com.example.coursesite_final.course.controller;

import com.example.coursesite_final.util.PageUtil;

public class PageController {
    public String getPaperHtml(long totalCount, long pageSize, long pageIndex, String queryString) {
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);
        return pageUtil.pager();
    }
}
