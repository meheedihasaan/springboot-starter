package com.springboot.starter.models;

import com.springboot.starter.enums.AscOrDesc;

import java.util.Map;

public class PaginationArgs {

    private int pageNumber;

    private int pageSize;

    private String sortBy;

    private AscOrDesc ascOrDesc;

    private Map<String, Object> parameters;

    public PaginationArgs() {}
    ;

    public PaginationArgs(int pageNumber, int pageSize, String sortBy, AscOrDesc ascOrDesc) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.ascOrDesc = ascOrDesc;
    }

    public PaginationArgs(
            int pageNumber, int pageSize, String sortBy, AscOrDesc ascOrDesc, Map<String, Object> parameters) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.ascOrDesc = ascOrDesc;
        this.parameters = parameters;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public AscOrDesc getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(AscOrDesc ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
