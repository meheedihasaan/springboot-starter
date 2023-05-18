package com.springboot.starter.constants;

import com.springboot.starter.entities.User;
import com.springboot.starter.enums.AscOrDesc;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.responses.PasswordValidationResponse;
import com.springboot.starter.security.CustomUserDetails;
import com.springboot.starter.services.UserService;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AppUtils {

    public static Boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static PasswordValidationResponse getPasswordValidationResponse(String password) {
        if (password == null || password.isEmpty()) {
            return new PasswordValidationResponse(false, "Password can't be empty.");
        }

        if (password.contains(" ")) {
            return new PasswordValidationResponse(false, "Password shouldn't contain white spaces.");
        }

        if (password.length() <= 7) {
            return new PasswordValidationResponse(false, "Password must have 8 or more characters.");
        }

        Pattern specialAndDigitPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_]).{3,}$");
        boolean isMatch = specialAndDigitPattern.matcher(password).matches();
        if (!isMatch) {
            return new PasswordValidationResponse(
                    false, "Password must have a digit, a special character and an alphabet.");
        }

        return new PasswordValidationResponse(true, "Password is validated.");
    }

    public static Pageable getPageable(PaginationArgs paginationArgs) {
        Pageable pageable;
        int pageNumber = paginationArgs.getPageNumber();
        int pageSize = paginationArgs.getPageSize();
        String sortBy = paginationArgs.getSortBy();
        AscOrDesc ascOrDesc = paginationArgs.getAscOrDesc();
        if (paginationArgs.getSortBy() != null && paginationArgs.getSortBy().length() > 0) {
            Sort sort;
            if (ascOrDesc.equals(AscOrDesc.asc)) {
                sort = Sort.by(sortBy).ascending();
            } else {
                sort = Sort.by(sortBy).descending();
            }
            pageable = PageRequest.of(pageNumber, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return pageable;
    }

    //    public static Pageable getPageable(PaginationArgs paginationArgs) {
    //        Pageable pageable;
    //        String sortBy = paginationArgs.getSortBy();
    //        int pageNo = paginationArgs.getPageNumber();
    //        int pageSize = paginationArgs.getPageSize();
    //
    //        if (sortBy != null && sortBy.length() > 0) {
    //            if (paginationArgs.getAscOrDescType().equals(AscOrDesc.asc)) {
    //                pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
    //            } else {
    //                pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    //            }
    //        } else {
    //            pageable = PageRequest.of(pageNo, pageSize);
    //        }
    //
    //        return pageable;
    //    }

    public static Map<String, Object> getSpecParameters(Map<String, Object> specParameters) {
        specParameters.remove(AppConstant.PAGE_NUMBER);
        specParameters.remove(AppConstant.PAGE_SIZE);
        specParameters.remove(AppConstant.SORT_BY);
        specParameters.remove(AppConstant.ASC_OR_DESC);
        specParameters.remove(AppConstant.PARAMETERS);
        return specParameters;
    }

    public static User getLoggedInUser(UserService userService) {
        User user;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) principal;

                user = new User();
                user.setId(customUserDetails.getId());
                user.setName(customUserDetails.getName());
                user.setEmail(customUserDetails.getPassword());
                user.setRoles(customUserDetails.getRoles());
                user.setVerified(customUserDetails.getVerified());
                user.setPassword(customUserDetails.getPassword());
            } else {
                user = userService.findByEmail(authentication.getName());
            }
        } catch (Exception ex) {
            user = userService.findByEmail(authentication.getName());
        }

        return user;
    }
}
