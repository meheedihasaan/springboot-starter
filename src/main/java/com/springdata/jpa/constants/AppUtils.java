package com.springdata.jpa.constants;

import com.springdata.jpa.enums.AscOrDescType;
import com.springdata.jpa.models.PaginationArgs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public final class AppUtils {

    /*public static Pageable getPageable(PaginationArgs paginationArgs) {
        Pageable pageable;
        int pageNumber = paginationArgs.getPageNumber();
        int pageSize = paginationArgs.getPageSize();
        String sortBy = paginationArgs.getSortBy();
        AscOrDescType ascOrDescType = paginationArgs.getAscOrDescType();
        if(paginationArgs.getSortBy() != null && paginationArgs.getSortBy().length() > 0) {
            Sort sort;
            if(ascOrDescType.equals(AscOrDescType.asc)) {
                sort = Sort.by(sortBy).ascending();
            }
            else {
                sort = Sort.by(sortBy).descending();
            }
            pageable = PageRequest.of(pageNumber, pageSize, sort);
        }
        else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return pageable;
    }*/


    public static Pageable getPageable(PaginationArgs paginationArgs) {
        Pageable pageable;
        String sortBy = paginationArgs.getSortBy();
        int pageNo = paginationArgs.getPageNumber();
        int pageSize = paginationArgs.getPageSize();

        if(sortBy != null && sortBy.length() > 0) {
            if (paginationArgs.getAscOrDescType().equals(AscOrDescType.asc)) {
                pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
            } else {
                pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
            }
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        return pageable;
    }








    public static Map<String, Object> getParameters(Map<String, Object> specParameters) {
        specParameters.remove(AppConstant.PAGE_NUMBER);
        specParameters.remove(AppConstant.PAGE_SIZE);
        specParameters.remove(AppConstant.SORT_BY);
        specParameters.remove(AppConstant.ASC_OR_DESC_TYPE);
        specParameters.remove(AppConstant.PARAMETERS);
        return specParameters;
    }

}
