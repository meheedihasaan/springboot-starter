package com.springdata.jpa.constants;

import com.springdata.jpa.enums.AscOrDescType;
import com.springdata.jpa.models.PaginationArgs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AppUtils {

    public static Pageable getPageable(PaginationArgs paginationArgs) {
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
    }

}
