package com.springboot.starter.models;

import com.springboot.starter.enums.AscOrDescType;
import java.util.Map;

public class PaginationArgs {

    private int pageNumber;

    private int pageSize;

    private String sortBy;

    private AscOrDescType ascOrDescType;

    private Map<String, Object> parameters;

    public PaginationArgs() {}
    ;

    public PaginationArgs(int pageNumber, int pageSize, String sortBy, AscOrDescType ascOrDescType) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.ascOrDescType = ascOrDescType;
    }

    public PaginationArgs(
            int pageNumber, int pageSize, String sortBy, AscOrDescType ascOrDescType, Map<String, Object> parameters) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.ascOrDescType = ascOrDescType;
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

    public AscOrDescType getAscOrDescType() {
        return ascOrDescType;
    }

    public void setAscOrDescType(AscOrDescType ascOrDescType) {
        this.ascOrDescType = ascOrDescType;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
