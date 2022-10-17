package com.example.coursesite_final.admin.model;

import lombok.Data;
@Data
public class CommonParam {

    long pageIndex;
    long pageSize;

    String searchType; // 현재 페이지 번호
    String searchValue; // 한 페이지에 나오는 개수

        /*
        getPageStart() > 0, 10, 20, 30
        getPageEnd() > 10, 10, 10, 10

        limit 0, 10 > pageIndex : 1
        limit 10, 10 > pageIndex : 2
        limit 20, 10 > pageIndex : 3
        limit 30, 10 > pageIndex : 4
    */


    public long getPageStart() {
        init();
        return (pageIndex - 1) * pageSize;
    }

    public long getPageEnd() {
        init();
        return pageSize;
    }

    public void init() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 10) {
            pageSize = 10;
        }
    }


    public String getQueryString() { // 페이지 이동시 전달되는 파라미터(쿼리스트링) > 검색 유지 위해
        init();

        StringBuilder sb = new StringBuilder();

        if (searchType != null && searchType.length() > 0) {
            sb.append(String.format("searchType=%s", searchType));
        }

        if (searchValue != null && searchValue.length() > 0) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s", searchValue));
        }

        return sb.toString();
    }
}
